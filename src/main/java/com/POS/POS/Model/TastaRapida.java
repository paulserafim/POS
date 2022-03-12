package com.POS.POS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TastaRapida {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private short id;

    private String denumire;
    private String codExtern;
    private short numar;

    @OneToOne(mappedBy = "tastaRapida", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Produs produs;

    public TastaRapida(String denumire, String codExtern, short numar) {
        this.denumire = denumire;
        this.codExtern = codExtern;
        this.numar = numar;
    }
}
