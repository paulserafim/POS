package com.POS.POS.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TastaRapidaResponseDTO {
    private short id;
    private String denumire;
    private String codExtern;
    private short numar;
    private String mesaj;

    public TastaRapidaResponseDTO(short id, String denumire, String codExtern, short numar) {
        this.id = id;
        this.denumire = denumire;
        this.codExtern = codExtern;
        this.numar = numar;
    }
}
