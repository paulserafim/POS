package com.POS.POS.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdusResponseDTO {

    private long id;
    private long codIntern;
    private String denumire;
    private String codExtern;
    private double pret;
    private double valoareTVA;
    private String unitateDeMasura;
    private String mesaj;

    public ProdusResponseDTO(long id, long codIntern, String denumire, String codExtern, double pret, double valoareTVA, String unitateDeMasura) {
        this.id = id;
        this.codIntern = codIntern;
        this.codExtern = codExtern;
        this.denumire = denumire;
        this.pret = pret;
        this.valoareTVA = valoareTVA;
        this.unitateDeMasura = unitateDeMasura;
    }
}
