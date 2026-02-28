package com.cortex.service;

import com.cortex.model.Knowledge;
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

@Service
public class ProcessService {

    private final InboxRepository inboxRepo;
    private final NoteRepository noteRepo;
    private final AiService aiService;

    public ProcessService(InboxRepository inboxRepo, NoteRepository noteRepo, AiService aiService) {
        this.inboxRepo = inboxRepo;
        this.noteRepo = noteRepo;
        this.aiService = aiService;
    }

    public Note processSingle(String itemId) {
        Knowledge item = inboxRepo.findById(itemId)
                .filter(i -> "pending".equals(i.getStatus()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pending item not found: " + itemId));

        return processAndSave(item);
    }

    public List<Note> processAll() {
        List<Knowledge> pending = inboxRepo.findAllPending();
        List<Note> results = new ArrayList<>();
        for (Knowledge item : pending) {
            try {
                results.add(processAndSave(item));
            } catch (Exception e) {
                // Log and continue with next item
            }
        }
        return results;
    }

    private Note processAndSave(Knowledge item) {
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
