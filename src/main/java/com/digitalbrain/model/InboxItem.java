package com.digitalbrain.model;

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
    private String type; // text | url | audio | code | image
    private String rawContent;
    private String source; // manual | share_extension
    private String status; // pending | processed | discarded
    private String createdAt;
}
