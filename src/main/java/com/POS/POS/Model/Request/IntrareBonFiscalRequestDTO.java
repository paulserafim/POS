package com.POS.POS.Model.Request;

import com.POS.POS.Model.Produs;
import com.POS.POS.Model.Response.IncasareResponseDTO;
import com.POS.POS.Model.Response.ProdusResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntrareBonFiscalRequestDTO {

    private double cantitate;
    private ProdusRequestDTO produs;
    private Double discountPercentage;
    private Double discountValue;
    private Double increasePercentage;
    private Double increaseValue;
    private boolean cancelled;
}
