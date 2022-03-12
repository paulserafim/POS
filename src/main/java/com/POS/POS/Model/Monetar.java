package com.POS.POS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Monetar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDate date;
    private final String TIP_DOCUMENT = "MONETAR";
    private boolean operat;
    private long nrDoc;
    private String simbolCarnet;

    @Enumerated(EnumType.STRING)
    private OperatieType operatie;
    @Enumerated(EnumType.STRING)
    private CasaDeMarcatValueType casaDeMarcat;
    private long totalArticole;
    private String observatii;
    private double discount;
    private double tvaDiscount;
    private String gestiuneLivrare;

    @OneToMany(mappedBy = "monetar", cascade = CascadeType.ALL)
    private List<BonFiscal> bonFiscalList;
}
