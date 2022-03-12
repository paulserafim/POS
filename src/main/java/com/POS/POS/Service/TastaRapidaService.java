package com.POS.POS.Service;

import com.POS.POS.Model.Produs;
import com.POS.POS.Model.TastaRapida;
import com.POS.POS.Model.Request.TastaRapidaRequestDTO;
import com.POS.POS.Model.Response.TastaRapidaResponseDTO;
import com.POS.POS.Repository.TastaRapidaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TastaRapidaService {
    private TastaRapidaRepository tastaRapidaRepository;
    private ProdusService produsService;

    public TastaRapidaService(TastaRapidaRepository tastaRapidaRepository, ProdusService produsService) {
        this.tastaRapidaRepository = tastaRapidaRepository;
        this.produsService = produsService;

    }

    public TastaRapida getTastaRapidaByIdDAO(short id) {
        TastaRapida tastaRapida = tastaRapidaRepository.getTastaRapidaById(id);
        return tastaRapida;
    }

    public TastaRapidaResponseDTO getTastaRapidaById(short id) {
        TastaRapida tastaRapida = tastaRapidaRepository.getTastaRapidaById(id);


        return new TastaRapidaResponseDTO(
                tastaRapida.getId(),
                tastaRapida.getDenumire(),
                tastaRapida.getCodExtern(),
                tastaRapida.getNumar()
        );
    }


    public TastaRapidaResponseDTO save(TastaRapidaRequestDTO tastaRapidaRequestDTO) {

        TastaRapida tastaRapidaSalvat = tastaRapidaRepository.save(
                new TastaRapida(
                        tastaRapidaRequestDTO.getDenumire(),
                        tastaRapidaRequestDTO.getCodExtern(),
                        tastaRapidaRequestDTO.getNumar()
                )
        );

        return new TastaRapidaResponseDTO(
                tastaRapidaSalvat.getId(),
                tastaRapidaSalvat.getDenumire(),
                tastaRapidaSalvat.getCodExtern(),
                tastaRapidaSalvat.getNumar(),
                "TastaRapida: " + tastaRapidaSalvat.getNumar() + " a fost creata cu succes pentru produsul: " + tastaRapidaSalvat.getDenumire() + ", cod extern: " + tastaRapidaSalvat.getCodExtern());
    }


    public TastaRapidaResponseDTO update(TastaRapidaRequestDTO tastaRapidaRequestDTO, short numar) {

        TastaRapida tastaRapidaDeModificat = tastaRapidaRepository.getTastaRapidaByNumar(numar);
        Produs produsDeAsociatTasteiRapide  = produsService.getProduseByCodExternDAO(tastaRapidaRequestDTO.getCodExtern()).get(0);

        tastaRapidaDeModificat.setDenumire(tastaRapidaRequestDTO.getDenumire());

        tastaRapidaDeModificat.setCodExtern(tastaRapidaRequestDTO.getCodExtern());

        tastaRapidaDeModificat.setProdus(produsDeAsociatTasteiRapide);

        TastaRapida tastaRapidaModificata = tastaRapidaRepository.save(tastaRapidaDeModificat);

        return new TastaRapidaResponseDTO(
                tastaRapidaModificata.getId(),
                tastaRapidaModificata.getDenumire(),
                tastaRapidaModificata.getCodExtern(),
                tastaRapidaModificata.getNumar(),
                "TastaRapida: " + tastaRapidaModificata.getNumar() + " a fost actualizata cu succes cu codul extern: " + tastaRapidaModificata.getCodExtern() + " pe produsul: " + tastaRapidaModificata.getDenumire()
        );
    }

    public Set<TastaRapidaResponseDTO> getAllTastaRapida() {
        Iterable<TastaRapida> tastaRapidaIterable = tastaRapidaRepository.findAll();
        Set<TastaRapidaResponseDTO> tastaRapidaResponseDTOSet = new HashSet<>();
        Iterator<TastaRapida> tastaRapidaIterator = tastaRapidaIterable.iterator();
        while(tastaRapidaIterator.hasNext()) {
            TastaRapida tastaRapida = tastaRapidaIterator.next();
            tastaRapidaResponseDTOSet.add(
                    new TastaRapidaResponseDTO(
                            tastaRapida.getId(),
                            tastaRapida.getDenumire(),
                            tastaRapida.getCodExtern(),
                            tastaRapida.getNumar()
                    )
            );
        }
        return tastaRapidaResponseDTOSet;
    }


    public boolean existsById(short id) {
        return tastaRapidaRepository.existsById(id);
    }

    public void deleteAll() {
        tastaRapidaRepository.deleteAll();
    }

    public TastaRapida saveDAO(TastaRapida tastaRapida) {
        TastaRapida tastaRapidaSalvat = tastaRapidaRepository.save(tastaRapida);

        return tastaRapidaSalvat;
    }


    public void deleteById(short id) {
        tastaRapidaRepository.deleteById(id);
    }

}
