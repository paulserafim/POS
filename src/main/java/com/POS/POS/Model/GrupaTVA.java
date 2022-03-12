package com.POS.POS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import org.hibernate.annotations.Parameter;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GrupaTVA {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private short id;
    private short numarGrupa;
    private double valoare;

    @OneToMany(mappedBy = "grupaTVA", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Produs> produse;

    public GrupaTVA(short id, double valoare) {
        this.id = id;
        this.valoare = valoare;
    }

    public GrupaTVA(double valoare) {
        this.valoare = valoare;
    }
}
