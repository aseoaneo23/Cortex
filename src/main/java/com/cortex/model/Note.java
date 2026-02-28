package com.cortex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private String id;
    private String inboxItemId;
    private String title;
    private String summary;
    private String content;
    private String category;
    private String tags;
    private String createdAt;
}
