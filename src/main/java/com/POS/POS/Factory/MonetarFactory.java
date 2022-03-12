package com.POS.POS.Factory;

import com.POS.POS.Model.*;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MonetarFactory {


    private static MonetarFactory monetarFactoryInstance = null;

    private MonetarFactory() {}

    public static MonetarFactory getInstance() {
        if(monetarFactoryInstance == null) {
            monetarFactoryInstance = new MonetarFactory();
        }
        return monetarFactoryInstance;
    }

    public Monetar create(List<BonFiscal> bonFiscalList, LocalDate date, long nrDoc, String simbolCarnet,
                          String observatii, String gestiuneLivrare) {
        Monetar monetar = new Monetar();
        monetar.setDate(date);
        monetar.setOperat(false);
        monetar.setNrDoc(nrDoc);
        monetar.setSimbolCarnet(simbolCarnet);
        monetar.setOperatie(OperatieType.A);
        monetar.setCasaDeMarcat(CasaDeMarcatValueType.D);
        monetar.setTotalArticole(calculateTotalArticole(bonFiscalList));
        monetar.setObservatii(observatii);
        //monetar.setDiscount(calculateDiscount(bonFiscalList));
        //monetar.setTvaDiscount(calculateTVADiscount(bonFiscalList));
        monetar.setBonFiscalList(bonFiscalList);
        monetar.setGestiuneLivrare(gestiuneLivrare);
        return monetar;
    }

    /*
    private double calculateDiscount(List<BonFiscal> bonFiscalList) {
        double totalDiscount = 0;
        for(int index = 0; index < bonFiscalList.size(); index ++) {
            total
        }
    }

     */

    private long calculateTotalArticole(List<BonFiscal> bonFiscalList) {
        long totalArticole = 0;
        for(int index = 0; index < bonFiscalList.size(); index ++) {
            totalArticole += bonFiscalList.get(index).getIntrareBonFiscalList().size();
        }
        return totalArticole;
    }

    private void createFile(Monetar monetar, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "Monetar" + monetar.getDate() + ".txt"));
        writer.write("[InfoPachet]\n");
        writer.write("AnLucru=" + monetar.getDate().getYear() + "\n");
        writer.write("LunaLucru=" + monetar.getDate().getMonth() + "\n");
        writer.write("TipDocument=" + monetar.getTIP_DOCUMENT() + "\n");
        writer.write("TotalMonetare=1\n");
        writer.write("\n");
        writer.write("[Monetar_1]");
        writer.write("Operat=" + ((monetar.isOperat() == true) ? "D\n" : "N\n"));
        writer.write("NrDoc=" + monetar.getNrDoc() + "\n");
        writer.write("SimbolCarnet=" + monetar.getSimbolCarnet() + "\n");
        writer.write("Operatie=" + monetar.getOperatie().toString() + "\n");
        writer.write("CasaDeMarcat=" + monetar.getCasaDeMarcat().toString() + "\n");
        writer.write("NumarBonuri=" + monetar.getBonFiscalList().size() + "\n");
        writer.write("Data=" + monetar.getDate().getDayOfMonth() + "." + monetar.getDate().getMonth() + "." + monetar.getDate().getYear() + "\n");
        writer.write("Casa=CasaLei" + "\n");
        writer.write("TotalArticole=" + monetar.getTotalArticole() + "\n");
        writer.write("Observatii=" + monetar.getObservatii() + "\n");
        writer.write("\n");
        writer.write("[Items_1]" + "\n");
        int indexItem = 1;
        for(int indexBonFiscal = 0; indexBonFiscal < monetar.getBonFiscalList().size(); indexBonFiscal ++) {
            for (int indexIntrareBonFiscal = 0;
                 indexIntrareBonFiscal < monetar.getBonFiscalList().get(indexBonFiscal).getIntrareBonFiscalList().size();
                 indexIntrareBonFiscal ++) {
                IntrareBonFiscal intrareBonFiscal = monetar.getBonFiscalList().
                        get(indexBonFiscal).getIntrareBonFiscalList().get(indexIntrareBonFiscal);
                writer.write("Item_" + indexItem + "=" + intrareBonFiscal.getProdus().getCodExtern() + ";" +
                        intrareBonFiscal.getProdus().getUm().getDenumire() + ";" + intrareBonFiscal.getCantitate() +
                        ";" + intrareBonFiscal.getPretVanzareDiscountIncrease() + ";" + monetar.getGestiuneLivrare());
            }
        }
        writer.close();
    }
}
