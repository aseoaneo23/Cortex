package com.cortex.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigInteger;

//Toquetear
@Data
public class KnowledgDto {

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "content is required")
    private String content;

    @Size(min = 3, max = 3, message = "La fecha debe tener 3 componentes: día, mes, año")
    private int date;

    private boolean state; // 0 unknown 1 known

    private String topic; // a note might not have a specific topic


}
