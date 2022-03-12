package com.POS.POS.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TastaRapidaRequestDTO {
    private String denumire;
    private String codExtern;
    private short numar;
}
