package com.POS.POS.Controller;

import com.POS.POS.Model.GrupaTVA;
import com.POS.POS.Model.Produs;
import com.POS.POS.Model.Request.GrupaTVARequestDTO;
import com.POS.POS.Model.Request.ProdusRequestDTO;
import com.POS.POS.Model.Request.UnitateDeMasuraRequestDTO;
import com.POS.POS.Model.Response.UnitateDeMasuraResponseDTO;
import com.POS.POS.Model.UnitateDeMasura;
import com.POS.POS.Service.ConstantaService;
import com.POS.POS.Service.GrupaTVAService;
import com.POS.POS.Service.ProdusService;
import com.POS.POS.Service.UnitateDeMasuraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Controller
@CrossOrigin(origins = {"http://localhost:8081"})
public class UpdateController {

    private ProdusService produsService;
    private ConstantaService constantaService;
    private UnitateDeMasuraService unitateDeMasuraService;
    private GrupaTVAService grupaTVAService;


    public UpdateController(ProdusService produsService, ConstantaService constantaService, UnitateDeMasuraService unitateDeMasuraService, GrupaTVAService grupaTVAService) {
        this.produsService = produsService;
        this.constantaService = constantaService;
        this.unitateDeMasuraService = unitateDeMasuraService;
        this.grupaTVAService = grupaTVAService;
    }

    @GetMapping("/update")
    public String index() {
        return "update";
    }

    @PostMapping("/update/produse") // //new annotation since 4.3
    public ResponseEntity updateProduse() throws SQLException, ClassNotFoundException {

        produsService.deleteAll();
        unitateDeMasuraService.deleteAll();
        grupaTVAService.deleteAll();

        String driver = "com.googlecode.paradox.Driver";

        String jdbcPrefixurl = "jdbc:paradox:";
        String dataPath = constantaService.getValoareConstantaTextByDenumire("directorImportWinMentor");
        String url = jdbcPrefixurl + dataPath;

        Class.forName(driver);

        java.sql.Connection con = DriverManager.getConnection(url);

        Statement statement = con.createStatement();
        Statement statementrsUM = con.createStatement();
        Statement statementrsTVA = con.createStatement();
        Statement statementrsNART1 = con.createStatement();

        ResultSet rs = statement.executeQuery("select Cod, Denumire, CodExtern, CodIntern, PretVanzare, UM from NART");

        ResultSet rsUM = statementrsUM.executeQuery("select Cod, Denumire from NUM");

        ResultSet rsTVA = statementrsTVA.executeQuery("select Cod, Procent from NTVA");

        ResultSet rsNART1 = statementrsNART1.executeQuery("select Art, TVA from NART1");

        HashMap<Short, Short> unitateDeMasuraOldToNewIdMap = new HashMap<>();
        HashMap<Short, Short> grupaTVAOldToNewIdMap = new HashMap<>();

        while(rsTVA.next()) {
            GrupaTVA grupaTVA = new GrupaTVA(rsTVA.getDouble(2));
            grupaTVA.setNumarGrupa(grupaTVAService.getLastAvailableNumarGrupaTVA());
            GrupaTVA grupaTVASaved = grupaTVAService.saveDAO(grupaTVA);
            grupaTVAOldToNewIdMap.put(rsTVA.getShort(1), grupaTVASaved.getId());
            log.info("GRUPA TVA NOUA:" + grupaTVA + " ID Mentor: " + rsTVA.getShort(1));
        }


        while(rsUM.next()) {

            String denumire;

            if(rsUM.getString(2) == null || rsUM.getString(2).compareTo("") == 0) {
                denumire = "NA";
            } else {
                denumire = rsUM.getString(2);
            }

            UnitateDeMasura unitateDeMasura = new UnitateDeMasura(denumire);
            UnitateDeMasura unitateDeMasuraSaved = unitateDeMasuraService.saveDAO(unitateDeMasura);
            unitateDeMasuraOldToNewIdMap.put(rsUM.getShort(1), unitateDeMasuraSaved.getId());
            log.info("UNITATE DE MASURA NOUA:" + unitateDeMasura + " ID Mentor:" + rsUM.getShort(1));

            }
        HashMap<Long, GrupaTVA> oldIdProdusNewGrupaTVA = new HashMap<>();

        while(rsNART1.next()) {
            oldIdProdusNewGrupaTVA.put(rsNART1.getLong(1), grupaTVAService.getGrupaTVAByIdDAO(grupaTVAOldToNewIdMap.get(rsNART1.getShort(2))));
        }

        while(rs.next()) {
            String denumire;
            String codExtern;
            String codIntern;
            Double pret;
            GrupaTVA grupaTVA = null;
            if(oldIdProdusNewGrupaTVA.containsKey(rs.getLong(1))) {
                grupaTVA = oldIdProdusNewGrupaTVA.get(rs.getLong(1));
            }
            UnitateDeMasura unitateDeMasura = null;
            if(unitateDeMasuraOldToNewIdMap.containsKey(rs.getShort(6))) {
                unitateDeMasura = unitateDeMasuraService.getUnitateDeMasuraByIdDAO(unitateDeMasuraOldToNewIdMap.get(rs.getShort(6)));
            }

            if(grupaTVA == null) {
                grupaTVA = grupaTVAService.getGrupaTVAByValoareDAO(0.0).get(0);
            }

            if(unitateDeMasura == null) {
                unitateDeMasura = unitateDeMasuraService.getAllUnitateDeMasuraeDAO().iterator().next();
            }

            if(rs.getString(2) == null || rs.getString(2).compareTo("") == 0) {
                denumire = "NA";
            } else {
                denumire = rs.getString(2);
            }

            if(rs.getString(3) == null || rs.getString(3).compareTo("") == 0) {
                codExtern = "NA";
            } else {
                codExtern = rs.getString(3);
            }

            if(rs.getString(4) == null || rs.getString(4).compareTo("") == 0) {
                codIntern = "0";
            } else {
                codIntern = rs.getString(4);
            }

            if(rs.getString(5) == null || rs.getString(5).compareTo("") == 0) {
                pret = 0.0;
            } else {
                pret = rs.getDouble(5);
            }

            if(rs.getString(6) == null || rs.getString(6).compareTo("") == 0 || ! unitateDeMasuraOldToNewIdMap.containsKey(rs.getShort(6))) {
                unitateDeMasura = unitateDeMasuraService.getUnitateDeMasuraByIdDAO((short) 1);
            } else {
                unitateDeMasura = unitateDeMasuraService.getUnitateDeMasuraByIdDAO(unitateDeMasuraOldToNewIdMap.get(rs.getShort(6)));
            }

            try {
                Produs produs = new Produs(
                        Long.parseLong(codIntern),
                        denumire,
                        codExtern,
                        pret,
                        unitateDeMasura,
                        grupaTVA
                );
                produsService.saveDAO(produs);
                log.info("PRODUS NOU:" + produs);
            } catch (NumberFormatException e) {
                Produs produs = new Produs(
                        0,
                        denumire,
                        codExtern,
                        pret,
                        unitateDeMasura,
                        grupaTVA
                );
                produsService.saveDAO(produs);
                log.info("PRODUS NOU:" + produs);
            }
        }

        con.close();

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();

    }

}
