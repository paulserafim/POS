package com.POS.POS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IntrareBonFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double cantitate;

    @ManyToOne
    @JoinColumn
    private Produs produs;

    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private BonFiscal bonFiscal;
    private Double pretVanzare;
    private Double pretVanzareDiscountIncrease;
    private Double discountPercentage;
    private Double discountValue;
    private Double increasePercentage;
    private Double increaseValue;
    private boolean cancelled;

    public IntrareBonFiscal(double cantitate, Produs produs, Double discountPercentage, Double discountValue, Double increasePercentage, Double increaseValue) {
        this.cantitate = cantitate;
        this.pretVanzare = produs.getPret();
        this.pretVanzareDiscountIncrease = calculatePretVanzareDiscountIncrease(produs.getPret(), discountPercentage, discountValue, increasePercentage, increaseValue);
        this.produs = produs;
        this.discountPercentage = discountPercentage;
        this.discountValue = discountValue;
        this.increasePercentage = increasePercentage;
        this.increaseValue = increaseValue;
    }

    public IntrareBonFiscal(double cantitate, Produs produs, Double discountPercentage, Double discountValue, Double increasePercentage, Double increaseValue, boolean cancelled) {
        this.cantitate = cantitate;
        this.pretVanzare = produs.getPret();
        this.pretVanzareDiscountIncrease = calculatePretVanzareDiscountIncrease(produs.getPret(), discountPercentage, discountValue, increasePercentage, increaseValue);
        this.produs = produs;
        this.discountPercentage = discountPercentage;
        this.discountValue = discountValue;
        this.increasePercentage = increasePercentage;
        this.increaseValue = increaseValue;
        this.cancelled = cancelled;
    }

    private Double calculatePretVanzareDiscountIncrease(Double pret, Double discountPercentage, Double discountValue, Double increasePercentage, Double increaseValue) {
        if(discountPercentage != 0 && discountPercentage != null) {
            return pret - pret * discountPercentage / 100;
        }
        if (discountValue != 0 && discountValue != null) {
            return pret - discountValue;
        }
        if (increasePercentage != 0 && increasePercentage != null) {
            return  pret + pret * increasePercentage / 100;
        }
        if (increaseValue != 0 && increaseValue != null) {
            return pret + increaseValue;
        }
        return pret;
    }

    public boolean hasDiscountPercentage() {
        return discountPercentage != 0 && discountPercentage != null;
    }

    public boolean hasDiscountValue() {
        return discountValue != 0 && discountValue != null;
    }

    public boolean hasIncreasePercentage() {
        return increasePercentage != 0 && increasePercentage != null;
    }

    public boolean hasIncreaseValue() {
        return increaseValue != 0 && increaseValue != null;
    }

}
