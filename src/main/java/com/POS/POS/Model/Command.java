package com.POS.POS.Model;

import com.POS.POS.Factory.MonetarFactory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static com.POS.POS.Model.CommandType.*;

@Data
@NoArgsConstructor
@Entity
@Slf4j
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDate date;
    private String path;

    @Enumerated(EnumType.STRING)
    private CommandType command;

    public void send() throws IOException {
        this.date = LocalDate.now();

        if(this.command == RAPORT_X || this.command == RAPORT_Z || this.command == ANULARE_BON) {
            createFile(path, command);
        }
    }


    private static void createFile(String path, CommandType command) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "Bon.txt"));
        switch (command) {
            case RAPORT_X:
                writer.write("X^");
                break;
            case RAPORT_Z:
                writer.write("Z^");
                break;
            case ANULARE_BON:
                writer.write("VB^");
                break;
        }
        writer.close();
    }

}
