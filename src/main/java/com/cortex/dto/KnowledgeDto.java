package com.cortex.dto;

import com.cortex.enums.KnowledgeStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeDto {

    private long id;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "content is required")
    private String content;

    private String createdAt;

    @NotBlank(message = "status is required")
    private int status;

    private String topic;


}
