package com.POS.POS.Controller;

import com.POS.POS.Model.Request.UnitateDeMasuraRequestDTO;
import com.POS.POS.Model.Response.UnitateDeMasuraResponseDTO;
import com.POS.POS.Service.UnitateDeMasuraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Slf4j
@RestController
@RequestMapping("/unitateDeMasura")
@CrossOrigin(origins = {"http://localhost:8081"})
public class UnitateDeMasuraController {

    private UnitateDeMasuraService unitateDeMasuraService;

    private UnitateDeMasuraController(UnitateDeMasuraService unitateDeMasuraService) {
        this.unitateDeMasuraService = unitateDeMasuraService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getUnitateDeMasuraById(@PathVariable short id) {

        UnitateDeMasuraResponseDTO unitateDeMasuraResponseDTO = unitateDeMasuraService.getUnitateDeMasuraById(id);
        return ResponseEntity.status(HttpStatus.OK).body(unitateDeMasuraResponseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity getAllUnitateDeMasurae() {
        Set<UnitateDeMasuraResponseDTO> unitateDeMasuraResponseDTOS = unitateDeMasuraService.getAllUnitateDeMasurae();
        return ResponseEntity.status(HttpStatus.OK).body(unitateDeMasuraResponseDTOS);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody UnitateDeMasuraRequestDTO unitateDeMasuraRequestDTO) {
        UnitateDeMasuraResponseDTO unitateDeMasuraResponseDTO = unitateDeMasuraService.save(unitateDeMasuraRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(unitateDeMasuraResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUnitateDeMasura(@RequestBody UnitateDeMasuraRequestDTO unitateDeMasuraRequestDTO, @PathVariable short id) {
        UnitateDeMasuraResponseDTO unitateDeMasuraResponseDTOModificat = unitateDeMasuraService.update(unitateDeMasuraRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(unitateDeMasuraResponseDTOModificat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUnitateDeMasura(@PathVariable short id) {
        unitateDeMasuraService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
