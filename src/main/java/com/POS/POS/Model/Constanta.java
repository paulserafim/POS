package com.POS.POS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Constanta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private short id;

    private String denumire;
    private String valoareText;
    private long valoareNumericaIntreaga;
    private double valoareNumerica;

    public Constanta(String denumire, String valoareText, long valoareNumericaIntreaga, double valoareNumerica) {
        this.denumire = denumire;
        this.valoareText = valoareText;
        this.valoareNumericaIntreaga = valoareNumericaIntreaga;
        this.valoareNumerica = valoareNumerica;
    }
}
