package com.POS.POS.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static com.POS.POS.Model.Instrument.NUMERAR;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BonFiscalFiscalNetFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private BonFiscal bonFiscal;

    private String content = "";
    private String path;

    public BonFiscalFiscalNetFile(BonFiscal bonFiscal, String path) {
        this.bonFiscal = bonFiscal;
        this.path = path;
    }

    public void convertToFiscalNetText() {
        if (bonFiscal.hasCui()) {
            content += "CF^" + bonFiscal.getCui() + "\n";
        }

        for (int index = 0; index < bonFiscal.getIntrareBonFiscalList().size(); index++) {
            if (bonFiscal.getIntrareBonFiscalList().get(index).isCancelled() == true) {
                content += "VS^" + bonFiscal.getIntrareBonFiscalList().get(index).getProdus().getDenumire() + "^"
                        + bonFiscal.getIntrareBonFiscalList().get(index).getProdus().getPret() + "^"
                        + bonFiscal.getIntrareBonFiscalList().get(index).getCantitate() + "^"
                        + bonFiscal.getIntrareBonFiscalList().get(index).getProdus().getUm().getDenumire() + "^"
                        + bonFiscal.getIntrareBonFiscalList().get(index).getProdus().getGrupaTVA().getId() + "^1\n";
            } else {
                content += "S^" + bonFiscal.getIntrareBonFiscalList().get(index).getProdus().getDenumire() + "^"
                        + bonFiscal.getIntrareBonFiscalList().get(index).getProdus().getPret() + "^"
                        + bonFiscal.getIntrareBonFiscalList().get(index).getCantitate() + "^"
                        + bonFiscal.getIntrareBonFiscalList().get(index).getProdus().getUm().getDenumire() + "^"
                        + bonFiscal.getIntrareBonFiscalList().get(index).getProdus().getGrupaTVA().getId() + "^1\n";
            }

            if (bonFiscal.getIntrareBonFiscalList().get(index).hasDiscountPercentage()) {
                content += "DP^" + bonFiscal.getIntrareBonFiscalList().get(index).getDiscountPercentage() * 100 + "\n";
            }

            if (bonFiscal.getIntrareBonFiscalList().get(index).hasDiscountValue()) {
                content += "DV^" + bonFiscal.getIntrareBonFiscalList().get(index).getDiscountValue() * 100 + "\n";
            }

            if (bonFiscal.getIntrareBonFiscalList().get(index).hasIncreasePercentage()) {
                content += "MP^" + bonFiscal.getIntrareBonFiscalList().get(index).getIncreasePercentage() * 100 + "\n";
            }

            if (bonFiscal.getIntrareBonFiscalList().get(index).hasIncreaseValue()) {
                content += "MV^" + bonFiscal.getIntrareBonFiscalList().get(index).getIncreaseValue() * 100 + "\n";
            }

        }

        content += "ST^\n";

        if (bonFiscal.hasDiscountPercentage()) {
            content += "DP^" + bonFiscal.getDiscountPercentage() * 100 + "\n";
        }

        if (bonFiscal.hasDiscountValue()) {
            content += "DV^" + bonFiscal.getDiscountValue() * 100 + "\n";
        }

        if (bonFiscal.hasIncreasePercentage()) {
            content += "MP^" + bonFiscal.getIncreasePercentage() * 100 + "\n";
        }

        if (bonFiscal.hasIncreaseValue()) {
            content += "MV^" + bonFiscal.getIncreaseValue() * 100 + "\n";
        }

        for (int index = 0; index < bonFiscal.getIncasareList().size(); index++) {
            content += "P^" + getIdIncasareDependingOnInstrument(bonFiscal.getIncasareList().get(index).getInstrument()) + "^"
                    + bonFiscal.getIncasareList().get(index).getValue() * 100 + "\n";
        }
    }

    private static short getIdIncasareDependingOnInstrument(Instrument instrument) {
        switch (instrument) {
            case NUMERAR:
                return 1;
            case CARD:
                return 2;
            case CREDIT:
                return 3;
            case TICHET_MASA:
                return 4;
            case TICHET_VALORIC:
                return 5;
            case VOUCHER:
                return 6;
            case PLATA_MODERNA:
                return 7;
            case ALTE_MODALITATI:
                return 8;
        }
        return 1;
    }

    public void createFile(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "Bon.txt"));
        writer.write(this.content);
        writer.close();
    }

}
