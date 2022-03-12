package com.POS.POS.Model.Request;

import com.POS.POS.Model.Response.IncasareResponseDTO;
import com.POS.POS.Model.StatusBonFiscal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BonFiscalRequestDTO {
    private String cui;
    private List<IncasareRequestDTO> incasareList;
    private List<IntrareBonFiscalRequestDTO> intrareBonFiscalList;
    private boolean fiscal;
    private boolean cancelled;
    private String text;
    private Double discountPercentage;
    private Double discountValue;
    private Double increasePercentage;
    private Double increaseValue;
    private StatusBonFiscal status;
}
