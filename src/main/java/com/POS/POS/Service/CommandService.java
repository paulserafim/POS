package com.POS.POS.Service;

import com.POS.POS.Model.Command;
import com.POS.POS.Model.Request.CommandRequestDTO;
import com.POS.POS.Model.Response.CommandResponseDTO;
import com.POS.POS.Repository.CommandRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CommandService {
    private CommandRepository commandRepository;

    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;

    }

    public CommandResponseDTO saveAndExecute(CommandRequestDTO commandRequestDTO) throws IOException {

        Command command = new Command();
        command.setCommand(commandRequestDTO.getCommandType());
        command.setPath(commandRequestDTO.getPath());
        command.send();
        Command savedCommand = commandRepository.save(command);

        return new CommandResponseDTO(
                savedCommand.getId(),
                savedCommand.getPath(),
                savedCommand.getCommand(),
                savedCommand.getDate()
        );

    }

}
