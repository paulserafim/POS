package com.POS.POS.Model.Response;

import com.POS.POS.Model.Incasare;
import com.POS.POS.Model.StatusBonFiscal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonFiscalResponseDTO {

    private long id;

    private String cui;

    private List<IncasareResponseDTO> incasareList;
    private List<IntrareBonFiscalResponseDTO> intrareBonFiscalList;

    private boolean fiscal;
    private boolean cancelled;
    private String text;
    private LocalDate date;
    private Double discountPercentage;
    private Double discountValue;
    private Double increasePercentage;
    private Double increaseValue;
    private StatusBonFiscal status;
}
