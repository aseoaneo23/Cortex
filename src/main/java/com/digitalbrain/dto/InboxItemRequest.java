package com.digitalbrain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InboxItemRequest {

    @NotBlank(message = "type is required")
    private String type; // text | url | audio | code | image

    @NotBlank(message = "rawContent is required")
    private String rawContent;

    private String source = "manual"; // manual | share_extension
}
