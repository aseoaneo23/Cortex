package com.cortex.service;

import com.cortex.dto.KnowledgeDto;
import com.cortex.enums.KnowledgeStatus;
import com.cortex.model.Knowledge;
import com.cortex.repository.KnowledgeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class KnowledgeService {

    private final KnowledgeRepository repo;

    public KnowledgeService(KnowledgeRepository repo) {
        this.repo = repo;
    }

    private Long generarIdUnico() {
        return Math.abs(UUID.randomUUID().getMostSignificantBits());
    }

    public Knowledge create(KnowledgeDto knowObj) {
        Knowledge item = Knowledge.builder()
                .id(generarIdUnico())
                .title(knowObj.getTitle())
                .content(knowObj.getContent())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .status(knowObj.getStatus())
                .topic(knowObj.getTopic())
                .build();
        return repo.save(item);
    }
    public List<Knowledge> findAllKnowledge() {
        return repo.findAll();
    }

    public List<Knowledge> getPending() {
        return repo.findAll();
    }

    public Knowledge findById(BigInteger id) {
        return repo.findById(id.toString()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found: " + id));
    }

    public void discard(String id) {
        repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found: " + id));
        repo.updateStatus(id, "discarded");
    }
}
