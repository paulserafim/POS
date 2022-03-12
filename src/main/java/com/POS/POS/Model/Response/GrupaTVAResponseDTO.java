package com.POS.POS.Model.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupaTVAResponseDTO {

    private short id;
    private short numarGrupa;
    private double valoare;

}
