/*
 * SPDX-FileCopyrightText: 2026 Antonio Seoane De Ois
 *
 * SPDX-License-Identifier: EPL-2.0
 */


package com.cortex.dto;

import com.cortex.model.Note;
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
