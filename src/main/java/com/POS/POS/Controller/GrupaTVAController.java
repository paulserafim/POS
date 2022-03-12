package com.POS.POS.Controller;

import com.POS.POS.Model.Request.GrupaTVARequestDTO;
import com.POS.POS.Model.Response.GrupaTVAResponseDTO;
import com.POS.POS.Service.GrupaTVAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Slf4j
@RestController
@RequestMapping("/grupaTVA")
@CrossOrigin(origins = {"http://localhost:8081"})
public class GrupaTVAController {

    private GrupaTVAService grupaTVAService;

    private GrupaTVAController(GrupaTVAService grupaTVAService) {
        this.grupaTVAService = grupaTVAService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getGrupaTVAById(@PathVariable short id) {

        GrupaTVAResponseDTO grupaTVAResponseDTO = grupaTVAService.getGrupaTVAById(id);
        return ResponseEntity.status(HttpStatus.OK).body(grupaTVAResponseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity getAllGrupaTVAe() {
        Set<GrupaTVAResponseDTO> grupaTVAResponseDTOS = grupaTVAService.getAllGrupaTVAe();
        return ResponseEntity.status(HttpStatus.OK).body(grupaTVAResponseDTOS);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody GrupaTVARequestDTO grupaTVARequestDTO) {
        GrupaTVAResponseDTO grupaTVAResponseDTO = grupaTVAService.save(grupaTVARequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(grupaTVAResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateGrupaTVA(@RequestBody GrupaTVARequestDTO grupaTVARequestDTO, @PathVariable short id) {
        GrupaTVAResponseDTO grupaTVAResponseDTOModificat = grupaTVAService.update(grupaTVARequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(grupaTVAResponseDTOModificat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGrupaTVA(@PathVariable short id) {
        grupaTVAService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
