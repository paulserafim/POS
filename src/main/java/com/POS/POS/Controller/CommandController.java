package com.POS.POS.Controller;

import com.POS.POS.Model.Request.CommandRequestDTO;
import com.POS.POS.Model.Response.CommandResponseDTO;
import com.POS.POS.Service.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;


@Slf4j
@RestController
@RequestMapping("/command")
@CrossOrigin(origins = {"http://localhost:8081"})
public class CommandController {

    private CommandService commandService;

    private CommandController(CommandService commandService) {
        this.commandService = commandService;
    }
    
    @PostMapping
    public ResponseEntity execute(@RequestBody CommandRequestDTO commandRequestDTO) throws IOException {
        CommandResponseDTO commandResponseDTO = commandService.saveAndExecute(commandRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(commandResponseDTO);
    }
}
