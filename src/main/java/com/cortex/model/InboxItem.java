package com.cortex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboxItem {
    private String id;
    private String type;
    private String rawContent;
    private String source;
    private String status;
    private String createdAt;
}
