package com.POS.POS.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitateDeMasuraRequestDTO {

    private short id;
    private String denumire;
}
