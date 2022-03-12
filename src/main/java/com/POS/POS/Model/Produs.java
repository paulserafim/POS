package com.POS.POS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Produs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long codIntern;
    private String denumire;
    private String codExtern;
    private double pret;

    @ManyToOne
    @JoinColumn
    private UnitateDeMasura um;

    @ManyToOne
    @JoinColumn
    private GrupaTVA grupaTVA;

    @OneToOne
    @ToString.Exclude
    @JoinColumn
    private TastaRapida tastaRapida;

    @OneToMany(mappedBy = "produs", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<IntrareBonFiscal> intrareBonFiscalList;

    public Produs(String denumire, String codExtern) {
        this.denumire = denumire;
        this.codExtern = codExtern;
    }

    public Produs(String denumire, String codExtern, double pret) {
        this.denumire = denumire;
        this.codExtern = codExtern;
        this.pret = pret;
    }

    public Produs(String denumire) {
        this.denumire = denumire;
    }

    public Produs(String denumire, double pret) {
        this.denumire = denumire;
        this.pret = pret;
    }

    public Produs(long codIntern, String denumire, String codExtern, Double pret, UnitateDeMasura um, GrupaTVA grupaTVA) {
        this.codIntern = codIntern;
        this.denumire = denumire;
        this.codExtern = codExtern;
        this.pret = pret;
        this.um = um;
        this.grupaTVA = grupaTVA;
    }

    public Produs(String denumire, String codExtern, double pret, UnitateDeMasura um) {
        this.denumire = denumire;
        this.codExtern = codExtern;
        this.pret= pret;
        this.um = um;
    }

    public Produs(long id, long codIntern, String denumire, String codExtern, Double pret, UnitateDeMasura um, GrupaTVA grupaTVA) {
        this.id = id;
        this.codIntern = codIntern;
        this.denumire = denumire;
        this.codExtern = codExtern;
        this.pret = pret;
        this.um = um;
        this.grupaTVA = grupaTVA;
    }
}
