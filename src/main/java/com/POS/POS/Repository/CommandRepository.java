package com.POS.POS.Repository;

import com.POS.POS.Model.BonFiscal;
import com.POS.POS.Model.Command;
import com.POS.POS.Model.CommandType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository extends CrudRepository<Command, Long> {
    Command getCommandById(long id);
}
