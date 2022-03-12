package com.POS.POS.Controller;

import com.POS.POS.Model.Request.ProdusRequestDTO;
import com.POS.POS.Model.Response.DatabaseInfoResponseDTO;
import com.POS.POS.Model.Response.ProdusResponseDTO;
import com.POS.POS.Service.ProdusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@Slf4j
@RestController
@RequestMapping("/produs")
@CrossOrigin(origins = {"http://localhost:8081"})
public class ProdusController {

    private ProdusService produsService;

    private ProdusController(ProdusService produsService) {
        this.produsService = produsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getProdusById(@PathVariable long id) {

        ProdusResponseDTO produsResponseDTO = produsService.getProdusByCodIntern(id);
        return ResponseEntity.status(HttpStatus.OK).body(produsResponseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity getAllProduse() {
        Set<ProdusResponseDTO> produsResponseDTOS = produsService.getAllProduse();
        return ResponseEntity.status(HttpStatus.OK).body(produsResponseDTOS);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ProdusRequestDTO produsRequestDTO) {
        ProdusResponseDTO produsResponseDTO = produsService.save(produsRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produsResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProdus(@RequestBody ProdusRequestDTO produsRequestDTO, @PathVariable long id) {
        ProdusResponseDTO produsResponseDTOModificat = produsService.update(produsRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(produsResponseDTOModificat);
    }

    @GetMapping("/codExtern")
    public ResponseEntity getProdusByCodExtern(@RequestParam(value = "codExtern") String codExtern) {
        List<ProdusResponseDTO> produsResponseDTOS = produsService.getProduseByCodExtern(codExtern);
        return ResponseEntity.status(HttpStatus.OK).body(produsResponseDTOS);
    }

    @GetMapping("/codIntern")
    public ResponseEntity getProdusByCodExtern(@RequestParam(value = "codIntern") long codIntern) {
        ProdusResponseDTO produsResponseDTOS = produsService.getProduseByCodIntern(codIntern);
        return ResponseEntity.status(HttpStatus.OK).body(produsResponseDTOS);
    }

    @GetMapping("/alphabet/{character}")
    public ResponseEntity getProdusByAlphabet(@PathVariable(value = "character") String character) {
        List<ProdusResponseDTO> produsResponseDTOS = produsService.getProduseByCharacter(character);
        return ResponseEntity.status(HttpStatus.OK).body(produsResponseDTOS);
    }

    @GetMapping("/denumire")
    public ResponseEntity getProdusByDenumire(@RequestParam(value = "denumire") String denumire) {
        List<ProdusResponseDTO> produsResponseDTOS = produsService.getProdusByDenumireProdus(denumire);
        return ResponseEntity.status(HttpStatus.OK).body(produsResponseDTOS);
    }

    @GetMapping("/pret")
    public ResponseEntity getProdusByPret(@RequestParam(value = "pret") double pret) {
        List<ProdusResponseDTO> produsResponseDTOS = produsService.getProduseByPret(pret);
        return ResponseEntity.status(HttpStatus.OK).body(produsResponseDTOS);
    }

    @GetMapping("/denumirePret")
    public ResponseEntity getProdusByDenumirePret(@RequestParam(value="denumire") String denumire,
                                                  @RequestParam(value="pret") double pret
    )
    {
        List<ProdusResponseDTO> produsResponseDTOS = produsService.getProdusByDenumirePret(denumire, pret);
        return ResponseEntity.status(HttpStatus.OK).body(produsResponseDTOS);
    }

    @GetMapping("/size")
    public ResponseEntity getSize() {
        DatabaseInfoResponseDTO databaseInfoResponseDTO = new DatabaseInfoResponseDTO("Produs", produsService.getSize());
        return ResponseEntity.status(HttpStatus.OK).body(databaseInfoResponseDTO);
    }

    @GetMapping("/existsByCodExtern/{codExtern}")
    public ResponseEntity existsByCodExtern(@PathVariable String codExtern) {
        DatabaseInfoResponseDTO databaseInfoResponseDTO = new DatabaseInfoResponseDTO("ExistsByCodExtern", produsService.existsByCodExtern(codExtern));
        return ResponseEntity.status(HttpStatus.OK).body(databaseInfoResponseDTO);
    }

    @GetMapping("/existsByCodIntern/{codIntern}")
    public ResponseEntity existsByCodIntern(@PathVariable long codIntern) {
        DatabaseInfoResponseDTO databaseInfoResponseDTO = new DatabaseInfoResponseDTO("ExistsByCodIntern", produsService.existsByCodIntern(codIntern));
        return ResponseEntity.status(HttpStatus.OK).body(databaseInfoResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGrupaTVA(@PathVariable short id) {
        produsService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/all")
    public ResponseEntity deleteAll() {
        produsService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
