package com.cortex.service;

import com.cortex.dto.KnowledgDto;
import com.cortex.model.Knowledge;
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

    public Knowledge create(KnowledgDto req) {
        Knowledge item = Knowledge.builder()
                .id(UUID.randomUUID().toString())
                .type(req.getType())
                .rawContent(req.getRawContent())
                .source(req.getSource() != null ? req.getSource() : "manual")
                .status("pending")
                .createdAt(Instant.now().toString())
                .build();
        return repo.save(item);
    }

    public List<Knowledge> getPending() {
        return repo.findAllPending();
    }

    public void discard(String id) {
        repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found: " + id));
        repo.updateStatus(id, "discarded");
    }
}
