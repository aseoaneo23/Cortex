package com.cortex.controller;

import com.cortex.dto.KnowledgeDto;
import com.cortex.model.Knowledge;
import com.cortex.service.KnowledgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
//arregar
@RestController
@RequestMapping("/brain/knowledge")
public class BrainController {

    private final KnowledgeService knowledgeService;

    public BrainController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Knowledge>> getAllKnowledge() {
        return ResponseEntity.ok(knowledgeService.findAllKnowledge());
    }

    @PostMapping("/")
    public ResponseEntity<Knowledge> createKnowledge(@RequestBody KnowledgeDto knowledgeDto) {
        return ResponseEntity.ok(knowledgeService.create(knowledgeDto));
    }

    /** GET /brain/search?q= → LIKE search on title, tags, content, summary */
    @GetMapping("/{id}")
    public ResponseEntity<Knowledge> getKnowledgeById(@PathVariable BigInteger id) {
        try{
            Knowledge item = knowledgeService.findById(id);
            return ResponseEntity.ok(item);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
