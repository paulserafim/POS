package com.POS.POS.Controller;

import com.POS.POS.Model.Request.IntrareBonFiscalRequestDTO;
import com.POS.POS.Model.Response.DatabaseInfoResponseDTO;
import com.POS.POS.Model.Response.IntrareBonFiscalResponseDTO;
import com.POS.POS.Service.IntrareBonFiscalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/intrareBonFiscal")
@CrossOrigin(origins = {"http://localhost:8081"})
public class IntrareBonFiscalController {

    private IntrareBonFiscalService intrareBonFiscalService;

    private IntrareBonFiscalController(IntrareBonFiscalService intrareBonFiscalService) {
        this.intrareBonFiscalService = intrareBonFiscalService;
    }


    @GetMapping("/all")
    public ResponseEntity getAllIntrareBonFiscal() {
        Set<IntrareBonFiscalResponseDTO> intrareBonFiscalResponseDTOS = intrareBonFiscalService.getAllIntrareBonFiscal();
        return ResponseEntity.status(HttpStatus.OK).body(intrareBonFiscalResponseDTOS);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody IntrareBonFiscalRequestDTO intrareBonFiscalRequestDTO) {
        IntrareBonFiscalResponseDTO intrareBonFiscalResponseDTO = intrareBonFiscalService.save(intrareBonFiscalRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(intrareBonFiscalResponseDTO);
    }

    @GetMapping("/codExtern")
    public ResponseEntity getIntrareBonFiscalByProdusCodExtern(@RequestParam(value = "codExtern") String codExtern) {
        List<IntrareBonFiscalResponseDTO> intrareBonFiscalResponseDTOS = intrareBonFiscalService.getIntrareBonFiscaleByProdusCodExtern(codExtern);
        return ResponseEntity.status(HttpStatus.OK).body(intrareBonFiscalResponseDTOS);
    }

    @GetMapping("/codIntern")
    public ResponseEntity getIntrareBonFiscalByProdusCodIntern(@RequestParam(value = "codIntern") long codIntern) {
        List<IntrareBonFiscalResponseDTO> intrareBonFiscalResponseDTOS = intrareBonFiscalService.getIntrareBonFiscaleByProdusCodIntern(codIntern);
        return ResponseEntity.status(HttpStatus.OK).body(intrareBonFiscalResponseDTOS);
    }

    @GetMapping("/size")
    public ResponseEntity getSize() {
        DatabaseInfoResponseDTO databaseInfoResponseDTO = new DatabaseInfoResponseDTO("IntrareBonFiscal", intrareBonFiscalService.getSize());
        return ResponseEntity.status(HttpStatus.OK).body(databaseInfoResponseDTO);
    }

    @DeleteMapping("/all")
    public ResponseEntity deleteAll() {
        intrareBonFiscalService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
