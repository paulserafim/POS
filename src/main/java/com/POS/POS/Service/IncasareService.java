package com.POS.POS.Service;


import com.POS.POS.Model.*;
import com.POS.POS.Model.Request.IncasareRequestDTO;
import com.POS.POS.Model.Request.IntrareBonFiscalRequestDTO;
import com.POS.POS.Model.Response.IntrareBonFiscalResponseDTO;
import com.POS.POS.Model.Response.ProdusResponseDTO;
import com.POS.POS.Repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class IncasareService {
    private IncasareRepository incasareRepository;

    public IncasareService(IncasareRepository incasareRepository) {
        this.incasareRepository = incasareRepository;
    }

    public Incasare saveDAO(Incasare incasare) {
        return incasareRepository.save(incasare);
    }

    public Incasare save(IncasareRequestDTO incasareRequestDTO) {
        Incasare incasare = new Incasare(
                incasareRequestDTO.getInstrument(),
                incasareRequestDTO.getValue()
        );

        return incasareRepository.save(incasare);
    }

    public void deleteAll() {
        incasareRepository.deleteAll();
    }



    public void deleteById(long id) {
        incasareRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return incasareRepository.existsById(id);
    }


    public long getSize() {
        return incasareRepository.count();
    }

}
