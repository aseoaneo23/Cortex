package com.digitalbrain.model;

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
    private String content; // markdown
    private String category; // study | tech | idea | task | reference
    private String tags; // JSON array as string e.g. ["java","ai"]
    private String createdAt;
}
