package com.cortex.dto;

import lombok.*;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PromptDto {
    private long id;

    private String prompt;

}
