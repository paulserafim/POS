package com.POS.POS.Model.Request;

import com.POS.POS.Model.CommandType;
import com.POS.POS.Model.Response.CommandResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandRequestDTO {
    private String path;
    private CommandType commandType;

    public CommandRequestDTO(CommandType commandType) {
        this.commandType = commandType;
    }
}
