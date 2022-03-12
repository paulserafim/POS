package com.POS.POS.Model;

import com.POS.POS.Model.Request.IncasareRequestDTO;
import com.POS.POS.Model.Request.IntrareBonFiscalRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BonFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String cui;

    @OneToMany(mappedBy = "bonFiscal", cascade = CascadeType.ALL)
    private List<Incasare> incasareList;

    @OneToMany(mappedBy = "bonFiscal", cascade = CascadeType.ALL)
    private List<IntrareBonFiscal> intrareBonFiscalList;

    @OneToOne(mappedBy = "bonFiscal", cascade = CascadeType.ALL)
    @JoinColumn
    private BonFiscalFiscalNetFile bonFiscalFiscalNetFile;

    private boolean fiscal;
    private boolean cancelled;
    private String text;
    private LocalDate date;
    private Double discountPercentage;
    private Double discountValue;
    private Double increasePercentage;
    private Double increaseValue;
    private Double total;
    private Double totalDupaDiscount;
    private Double totalTVA;

    @Enumerated(EnumType.STRING)
    private StatusBonFiscal status;

    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private Monetar monetar;

    public BonFiscal(String cui, List<Incasare> incasareList, List<IntrareBonFiscal> intrareBonFiscalList,
                     boolean fiscal, boolean cancelled, String text, Double discountPercentage, Double discountValue,
                     Double increasePercentage, Double increaseValue, StatusBonFiscal status) {
        this.cui = cui;
        this.incasareList = incasareList;
        this.intrareBonFiscalList = intrareBonFiscalList;
        this.fiscal = fiscal;
        this.cancelled = cancelled;
        this.text = text;
        this.date = LocalDate.now();
        this.discountPercentage = discountPercentage;
        this.discountValue = discountValue;
        this.increasePercentage = increasePercentage;
        this.increaseValue = increaseValue;
        this.status = status;
        this.total = calculateTotal(intrareBonFiscalList);
        this.totalDupaDiscount = calculateTotalDupaDiscount(intrareBonFiscalList, discountPercentage, discountValue,
                                                            increasePercentage, increaseValue);
        this.totalTVA = calculateTotalTVA(intrareBonFiscalList);
    }

    private Double calculateTotalTVA(List<IntrareBonFiscal> intrareBonFiscalList) {
        Double totalTVA = 0.0;
        for (int index = 0; index < intrareBonFiscalList.size(); index ++) {
            totalTVA += intrareBonFiscalList.get(index).getPretVanzareDiscountIncrease() *
                    intrareBonFiscalList.get(index).getCantitate() *
                    intrareBonFiscalList.get(index).getProdus().getGrupaTVA().getValoare() / 100;
        }

        return totalTVA;
    }

    private Double calculateTotalDupaDiscount(List<IntrareBonFiscal> intrareBonFiscalList, Double discountPercentage,
                                              Double discountValue, Double increasePercentage, Double increaseValue) {
        Double totalDupaDiscountIntrari = 0.0;
        for (int index = 0; index < intrareBonFiscalList.size(); index ++) {
            totalDupaDiscountIntrari += intrareBonFiscalList.get(index).getPretVanzareDiscountIncrease() *
                                                                    intrareBonFiscalList.get(index).getCantitate();
        }
        if(discountPercentage != 0 && discountPercentage != null) {
            return totalDupaDiscountIntrari - totalDupaDiscountIntrari * discountPercentage / 100;
        }
        if (discountValue != 0 && discountValue != null) {
            return totalDupaDiscountIntrari - discountValue;
        }
        if (increasePercentage != 0 && increasePercentage != null) {
            return  totalDupaDiscountIntrari + totalDupaDiscountIntrari * increasePercentage / 100;
        }
        if (increaseValue != 0 && increaseValue != null) {
            return totalDupaDiscountIntrari + increaseValue;
        }
        return totalDupaDiscountIntrari;
    }

    private Double calculateTotal(List<IntrareBonFiscal> intrareBonFiscalList) {
        Double total = 0.0;
        for (int index = 0; index < intrareBonFiscalList.size(); index ++) {
            total += intrareBonFiscalList.get(index).getPretVanzare() * intrareBonFiscalList.get(index).getCantitate();
        }
        return total;
    }

    public boolean hasDifferentIntrareBonFiscalList(List<IntrareBonFiscalRequestDTO> intrareBonFiscalRequestDTOList) {
        if(intrareBonFiscalRequestDTOList.size() != this.getIntrareBonFiscalList().size()) {
            return true;
        } else {
            for(int index = 0; index < this.getIntrareBonFiscalList().size(); index ++) {
                if(
                        this.getIntrareBonFiscalList().get(index).getDiscountValue().compareTo(intrareBonFiscalRequestDTOList.get(index).getDiscountValue()) != 0
                || this.getIntrareBonFiscalList().get(index).getDiscountPercentage().compareTo(intrareBonFiscalRequestDTOList.get(index).getDiscountPercentage()) != 0
                || this.getIntrareBonFiscalList().get(index).getIncreaseValue().compareTo(intrareBonFiscalRequestDTOList.get(index).getIncreaseValue()) != 0
                || this.getIntrareBonFiscalList().get(index).getIncreasePercentage().compareTo(intrareBonFiscalRequestDTOList.get(index).getIncreasePercentage()) != 0
                || this.getIntrareBonFiscalList().get(index).getProdus().getId() != intrareBonFiscalRequestDTOList.get(index).getProdus().getId()
                || this.getIntrareBonFiscalList().get(index).getCantitate() != intrareBonFiscalRequestDTOList.get(index).getCantitate()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasDifferentIncasareList(List<IncasareRequestDTO> incasareList) {
        if(incasareList.size() != this.getIncasareList().size()) {
            return true;
        } else {
            for(int index = 0; index < this.getIncasareList().size(); index ++) {
                if(
                        this.getIncasareList().get(index).getInstrument() != incasareList.get(index).getInstrument()
                        || this.getIncasareList().get(index).getValue() != incasareList.get(index).getValue())
                    return true;
                }
            }
        return false;
    }

    public boolean hasCui() {
        return ((cui != null) && (cui != ""));
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
