package com.digitalbrain.controller;

import com.digitalbrain.dto.InboxItemRequest;
import com.digitalbrain.model.InboxItem;
import com.digitalbrain.service.InboxService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inbox")
public class InboxController {

    private final InboxService service;

    public InboxController(InboxService service) {
        this.service = service;
    }

    /** POST /inbox → create InboxItem (status: pending) */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InboxItem create(@Valid @RequestBody InboxItemRequest req) {
        return service.create(req);
    }

    /** GET /inbox/pending → list all pending items */
    @GetMapping("/pending")
    public List<InboxItem> getPending() {
        return service.getPending();
    }

    /** DELETE /inbox/{id} → set status to discarded */
    @DeleteMapping("/{id}")
    public Map<String, String> discard(@PathVariable String id) {
        service.discard(id);
        return Map.of("message", "Item discarded", "id", id);
    }
}
