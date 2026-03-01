/*
 * SPDX-FileCopyrightText: 2026 Antonio Seoane De Ois
 *
 * SPDX-License-Identifier: EPL-2.0
 */


package com.cortex.service;

import com.cortex.model.InboxItem;
import com.cortex.model.Note;
import com.cortex.repository.InboxRepository;
import com.cortex.repository.NoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ProcessService {

    private final InboxRepository inboxRepo;
    private final NoteRepository noteRepo;
    private final AiService aiService;
    private final AtomicBoolean isPosting = new AtomicBoolean(false);


    public ProcessService(InboxRepository inboxRepo, NoteRepository noteRepo, AiService aiService) {
        this.inboxRepo = inboxRepo;
        this.noteRepo = noteRepo;
        this.aiService = aiService;
    }

    public Note processSingle(String itemId) {
        if (!isPosting.compareAndSet(false, true)) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Already processing an item, try again later.");
        }
        Note note;
        try {
            InboxItem item = inboxRepo.findById(itemId)
                    .filter(i -> "pending".equals(i.getStatus()))
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Pending item not found: " + itemId));
            note = processAndSave(item);
        } finally {
            isPosting.set(false);
        }
        return note;
    }
    public List<Note> processAll() {
        List<InboxItem> pending = inboxRepo.findAllPending();
        List<Note> results = new ArrayList<>();
        for (InboxItem item : pending) {
            try {
                results.add(processAndSave(item));
            } catch (Exception e) {
                // Log and continue with next item
            }
        }
        return results;
    }

    private Note processAndSave(InboxItem item) {
        Map<String, Object> aiResult = aiService.processItem(item.getRawContent(), item.getType());

        Note note = Note.builder()
                .id(UUID.randomUUID().toString())
                .inboxItemId(item.getId())
                .title((String) aiResult.getOrDefault("title",
                        item.getRawContent().substring(0, Math.min(50, item.getRawContent().length()))))
                .summary((String) aiResult.getOrDefault("summary", ""))
                .content((String) aiResult.getOrDefault("content", ""))
                .category((String) aiResult.getOrDefault("category", "reference"))
                .tags((String) aiResult.getOrDefault("tags", "[]"))
                .createdAt(Instant.now().toString())
                .build();

        noteRepo.save(note);
        inboxRepo.updateStatus(item.getId(), "processed");
        return note;
    }
}
