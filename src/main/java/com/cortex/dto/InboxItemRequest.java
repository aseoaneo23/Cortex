/*
 * SPDX-FileCopyrightText: 2026 Antonio Seoane De Ois
 *
 * SPDX-License-Identifier: EPL-2.0
 */


package com.cortex.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InboxItemRequest {

    @NotBlank(message = "type is required")
    private String type;

    @NotBlank(message = "rawContent is required")
    private String rawContent;

    private String source = "manual";
}
