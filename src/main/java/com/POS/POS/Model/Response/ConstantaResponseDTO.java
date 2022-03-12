package com.POS.POS.Model.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstantaResponseDTO {

    private short id;
    private String denumire;
    private String valoareText;
    private long valoareNumericaIntreaga;
    private double valoareNumerica;
    private String mesaj;

    public ConstantaResponseDTO(short id, String denumire, String valoareText, long valoareNumericaIntreaga, double valoareNumerica) {
        this.id = id;
        this.denumire = denumire;
        this.valoareText = valoareText;
        this.valoareNumericaIntreaga = valoareNumericaIntreaga;
        this.valoareNumerica = valoareNumerica;
    }
}
