package com.POS.POS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UnitateDeMasura {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "unitateDeMasura_sequence"
    )
    @GenericGenerator(
            name = "unitateDeMasura_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "unitateDeMasura_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private short id;
    private String denumire;

    @OneToMany(mappedBy = "um", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Produs> produse;

    public UnitateDeMasura(short id, String denumire) {
        this.id = id;
        this.denumire = denumire;
    }

    public UnitateDeMasura(String denumire) {
        this.denumire = denumire;
    }
}
