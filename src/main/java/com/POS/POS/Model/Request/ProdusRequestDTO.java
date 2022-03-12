package com.POS.POS.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdusRequestDTO {

    private long id;
    private long codIntern;
    private String denumire;
    private String codExtern;
    private double pret;
    private short idGrupaTVA;
    private short idUM;
}
