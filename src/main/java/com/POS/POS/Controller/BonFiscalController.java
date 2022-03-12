package com.POS.POS.Controller;

import com.POS.POS.Model.BonFiscal;
import com.POS.POS.Model.Request.BonFiscalRequestDTO;
import com.POS.POS.Model.Response.DatabaseInfoResponseDTO;
import com.POS.POS.Model.Response.BonFiscalResponseDTO;
import com.POS.POS.Service.BonFiscalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/bonFiscal")
@CrossOrigin(origins = {"http://localhost:8081"})
public class BonFiscalController {

    private BonFiscalService bonFiscalService;

    private BonFiscalController(BonFiscalService bonFiscalService) {
        this.bonFiscalService = bonFiscalService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody BonFiscalRequestDTO bonFiscalRequestDTO) throws IOException {
        BonFiscalResponseDTO bonFiscalResponseDTO = bonFiscalService.save(bonFiscalRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bonFiscalResponseDTO);
    }

    @GetMapping("/size")
    public ResponseEntity getSize() {
        DatabaseInfoResponseDTO databaseInfoResponseDTO = new DatabaseInfoResponseDTO("BonFiscal", bonFiscalService.getSize());
        return ResponseEntity.status(HttpStatus.OK).body(databaseInfoResponseDTO);
    }

    @GetMapping("/date")
    public ResponseEntity getBonFiscalByDateBetween(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<BonFiscalResponseDTO> bonFiscalResponseDTOList = bonFiscalService.getBonFiscalByDateBetween(start, end);
        return ResponseEntity.status(HttpStatus.OK).body(bonFiscalResponseDTOList);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBonFiscalById(@RequestBody BonFiscalRequestDTO bonFiscalRequestDTO, @PathVariable long id) {
        bonFiscalService.updateBonFiscal(bonFiscalRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/codFiscal")
    public ResponseEntity getBonFiscalByCodFiscal(@RequestParam String codFiscal) {
        List<BonFiscalResponseDTO> bonFiscalResponseDTOList = bonFiscalService.getBonFiscalByCodFiscal(codFiscal);
        return ResponseEntity.status(HttpStatus.OK).body(bonFiscalResponseDTOList);
    }

    @GetMapping("/status")
    public ResponseEntity getBonFiscalByStatus(@RequestParam String status) {
        List<BonFiscalResponseDTO> bonFiscalResponseDTOList = bonFiscalService.getBonFiscalByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(bonFiscalResponseDTOList);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {
        List<BonFiscalResponseDTO> bonFiscalResponseDTOList = bonFiscalService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(bonFiscalResponseDTOList);
    }

    @DeleteMapping("/all")
    public ResponseEntity deleteAll() {
        bonFiscalService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
