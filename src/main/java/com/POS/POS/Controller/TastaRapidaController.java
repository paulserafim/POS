package com.POS.POS.Controller;

import com.POS.POS.Model.Request.TastaRapidaRequestDTO;
import com.POS.POS.Model.Response.TastaRapidaResponseDTO;
import com.POS.POS.Service.TastaRapidaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Slf4j
@RestController
@RequestMapping("/tastaRapida")
@CrossOrigin(origins = {"http://localhost:8081"})
public class TastaRapidaController {

    private TastaRapidaService tastaRapidaService;

    private TastaRapidaController(TastaRapidaService tastaRapidaService) {
        this.tastaRapidaService = tastaRapidaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getTastaRapidaById(@PathVariable short id) {

        TastaRapidaResponseDTO tastaRapidaResponseDTO = tastaRapidaService.getTastaRapidaById(id);
        return ResponseEntity.status(HttpStatus.OK).body(tastaRapidaResponseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity getAllTastaRapidae() {
        Set<TastaRapidaResponseDTO> tastaRapidaResponseDTOS = tastaRapidaService.getAllTastaRapida();
        return ResponseEntity.status(HttpStatus.OK).body(tastaRapidaResponseDTOS);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody TastaRapidaRequestDTO tastaRapidaRequestDTO) {
        TastaRapidaResponseDTO tastaRapidaResponseDTO = tastaRapidaService.save(tastaRapidaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tastaRapidaResponseDTO);
    }

    @PutMapping("/{numar}")
    public ResponseEntity updateTastaRapida(@RequestBody TastaRapidaRequestDTO tastaRapidaRequestDTO, @PathVariable short numar) {
        TastaRapidaResponseDTO tastaRapidaResponseDTOModificat = tastaRapidaService.update(tastaRapidaRequestDTO, numar);
        return ResponseEntity.status(HttpStatus.OK).body(tastaRapidaResponseDTOModificat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTastaRapida(@PathVariable short id) {
        tastaRapidaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
