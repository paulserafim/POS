package com.POS.POS.Model.Response;

import com.POS.POS.Model.BonFiscal;
import com.POS.POS.Model.Instrument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IncasareResponseDTO {

    private long id;
    private Instrument instrument;
    private double value;
}
