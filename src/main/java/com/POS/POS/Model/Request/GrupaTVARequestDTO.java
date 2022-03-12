package com.POS.POS.Model.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupaTVARequestDTO {

    private short id;
    private short numarGrupa;
    private double valoare;

}
