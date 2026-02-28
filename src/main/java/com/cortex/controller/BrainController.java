package com.cortex.controller;

import com.cortex.model.Note;
import com.cortex.repository.NoteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brain")
public class BrainController {

    private final NoteRepository repo;

    public BrainController(NoteRepository repo) {
        this.repo = repo;
    }

    /** GET /brain → all notes ordered by created_at desc */
    @GetMapping
    public List<Note> getAllNotes() {
        return repo.findAll();
    }

    /** GET /brain/search?q= → LIKE search on title, tags, content, summary */
    @GetMapping("/search")
    public List<Note> searchNotes(@RequestParam String q) {
        return repo.search(q);
    }

    /** GET / → health check */
    @GetMapping("/")
    public Map<String, String> root() {
        return Map.of("message", "Cortex API is running 🧠");
    }

    /** DELETE /brain/{id} → delete note */
    @DeleteMapping("/{id}")
    public Map<String, String> deleteNote(@PathVariable String id) {
        repo.delete(id);
        return Map.of("message", "Note deleted successfully");
    }
}