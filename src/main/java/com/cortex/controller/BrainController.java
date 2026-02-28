package com.cortex.controller;

import com.cortex.model.Knowledge;
import com.cortex.repository.NoteRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
//arregar
@RestController
@RequestMapping("/brain/knowledge")
public class BrainController {

    private final Knowledge knowledge;

    public BrainController(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    /** Quitar */
    @GetMapping
    public List<Note> getAllNotes() {
        return knowledge.findAll();
    }

    /** GET /brain/search?q= → LIKE search on title, tags, content, summary */
    @GetMapping("/{id}")
    public Knowledge getKnowlegeById(@RequestParam BigInteger id) {
        return knowledge.search(q );
    }

    /** GET / → health check */
    @GetMapping("/")
    public Map<String, String> root() {
        return Map.of("message", "Cortex Brain API is running 🧠");
    }
}
