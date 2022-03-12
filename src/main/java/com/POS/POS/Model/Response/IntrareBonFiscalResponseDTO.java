package com.POS.POS.Model.Response;

import com.POS.POS.Model.Produs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IntrareBonFiscalResponseDTO {

    private long id;
    private double cantitate;
    private ProdusResponseDTO produs;
    private Double discountPercentage;
    private Double discountValue;
    private Double increasePercentage;
    private Double increaseValue;
    private boolean cancelled;
}
