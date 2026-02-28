package com.digitalbrain.dto;

import com.digitalbrain.model.Note;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BatchProcessResponse {
    private String message;
    private int processed;
    private List<String> errors;
    private List<Note> notes;
}
