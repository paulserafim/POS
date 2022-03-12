package com.POS.POS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Incasare {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private Instrument instrument;

    private double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @ToString.Exclude
    private BonFiscal bonFiscal;

    public Incasare(Instrument instrument, double value) {
        this.instrument = instrument;
        this.value = value;
    }
}
