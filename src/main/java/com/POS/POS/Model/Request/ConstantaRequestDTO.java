package com.POS.POS.Model.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstantaRequestDTO {

    private String denumire;
    private String valoareText;
    private long valoareNumericaIntreaga;
    private double valoareNumerica;
}
