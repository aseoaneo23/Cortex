package com.digitalbrain.controller;

import com.digitalbrain.model.Note;
import com.digitalbrain.service.ProcessService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/process")
public class ProcessController {

    private final ProcessService service;

    public ProcessController(ProcessService service) {
        this.service = service;
    }

    /** POST /process/{id} → process single item */
    @PostMapping("/{id}")
    public Map<String, Object> processSingle(@PathVariable String id) {
        Note note = service.processSingle(id);
        return Map.of("message", "Item processed successfully", "note", note);
    }

    /** POST /process/batch/all → process all pending items */
    @PostMapping("/batch/all")
    public Map<String, Object> processBatch() {
        List<Note> notes = service.processAll();
        return Map.of(
                "message", "Processed " + notes.size() + " items",
                "processed", notes.size(),
                "notes", notes);
    }
}
