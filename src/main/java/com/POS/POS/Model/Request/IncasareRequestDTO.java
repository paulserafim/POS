package com.POS.POS.Model.Request;

import com.POS.POS.Model.Instrument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncasareRequestDTO {
    private Instrument instrument;
    private double value;
}
