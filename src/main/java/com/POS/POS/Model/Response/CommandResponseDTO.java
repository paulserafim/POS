package com.POS.POS.Model.Response;

import com.POS.POS.Model.CommandType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CommandResponseDTO {

    private long id;
    private String path;
    private CommandType commandType;
    private LocalDate date;
}
