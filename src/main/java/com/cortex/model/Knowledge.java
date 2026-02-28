package com.cortex.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

// Model=Como tratas la tabla desde java y ademas de model es el objeto
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Knowledge {

    private BigInteger id; // item's primary key

    private String title;

    private String content;

    @Size(min = 3, max = 3, message = "La fecha debe tener 3 componentes: día, mes, año")
    private int date;

    private boolean state; // 0 unknown 1 known

    private String topic; // a note might not have a specific topic

}
