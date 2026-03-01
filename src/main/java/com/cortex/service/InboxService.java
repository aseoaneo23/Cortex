/*
 * SPDX-FileCopyrightText: 2026 Antonio Seoane De Ois
 *
 * SPDX-License-Identifier: EPL-2.0
 */


package com.cortex.service;

import com.cortex.dto.InboxItemRequest;
import com.cortex.model.InboxItem;
import com.cortex.repository.InboxRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class InboxService {

    private final InboxRepository repo;

    public InboxService(InboxRepository repo) {
        this.repo = repo;
    }

    public InboxItem create(InboxItemRequest req) {
        InboxItem item = InboxItem.builder()
                .id(UUID.randomUUID().toString())
                .type(req.getType())
                .rawContent(req.getRawContent())
                .source(req.getSource() != null ? req.getSource() : "manual")
                .status("pending")
                .createdAt(Instant.now().toString())
                .build();
        return repo.save(item);
    }

    public List<InboxItem> getPending() {
        return repo.findAllPending();
    }

    public void discard(String id) {
        repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found: " + id));
        repo.delete(id);
    }
}
