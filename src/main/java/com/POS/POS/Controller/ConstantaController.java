package com.POS.POS.Controller;

import com.POS.POS.Model.Request.ConstantaRequestDTO;
import com.POS.POS.Model.Response.ConstantaResponseDTO;
import com.POS.POS.Service.ConstantaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Slf4j
@RestController
@RequestMapping("/constanta")
@CrossOrigin(origins = {"http://localhost:8081"})
public class ConstantaController {

    private ConstantaService constantaService;

    private ConstantaController(ConstantaService constantaService) {
        this.constantaService = constantaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getConstantaById(@PathVariable short id) {

        ConstantaResponseDTO constantaResponseDTO = constantaService.getConstantaById(id);
        return ResponseEntity.status(HttpStatus.OK).body(constantaResponseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity getAllConstantae() {
        Set<ConstantaResponseDTO> constantaResponseDTOS = constantaService.getAllConstante();
        return ResponseEntity.status(HttpStatus.OK).body(constantaResponseDTOS);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ConstantaRequestDTO constantaRequestDTO) {
        ConstantaResponseDTO constantaResponseDTO = constantaService.save(constantaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(constantaResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateConstanta(@RequestBody ConstantaRequestDTO constantaRequestDTO, @PathVariable short id) {
        ConstantaResponseDTO constantaResponseDTOModificat = constantaService.update(constantaRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(constantaResponseDTOModificat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteConstanta(@PathVariable short id) {
        constantaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
