package com.POS.POS.Service;

import com.POS.POS.Model.UnitateDeMasura;
import com.POS.POS.Model.Request.UnitateDeMasuraRequestDTO;
import com.POS.POS.Model.Response.UnitateDeMasuraResponseDTO;
import com.POS.POS.Repository.GrupaTVARepository;
import com.POS.POS.Repository.UnitateDeMasuraRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UnitateDeMasuraService {
    private UnitateDeMasuraRepository unitateDeMasuraRepository;

    public UnitateDeMasuraService(UnitateDeMasuraRepository unitateDeMasuraRepository) {
        this.unitateDeMasuraRepository = unitateDeMasuraRepository;

    }

    public UnitateDeMasura getUnitateDeMasuraByIdDAO(short id) {
        UnitateDeMasura unitateDeMasura = unitateDeMasuraRepository.getUnitateDeMasuraById(id);
        return unitateDeMasura;
    }

    public UnitateDeMasuraResponseDTO getUnitateDeMasuraById(short id) {
        UnitateDeMasura unitateDeMasura = unitateDeMasuraRepository.getUnitateDeMasuraById(id);


        return new UnitateDeMasuraResponseDTO(
                unitateDeMasura.getId(),
                unitateDeMasura.getDenumire()
        );
    }


    public UnitateDeMasuraResponseDTO save(UnitateDeMasuraRequestDTO unitateDeMasuraRequestDTO) {
        UnitateDeMasura unitateDeMasura = unitateDeMasuraRepository.getUnitateDeMasuraById(unitateDeMasuraRequestDTO.getId());
        UnitateDeMasura unitateDeMasuraSalvat = unitateDeMasuraRepository.save(
                new UnitateDeMasura(
                        unitateDeMasuraRequestDTO.getId(),
                        unitateDeMasuraRequestDTO.getDenumire()
                )
        );

        return new UnitateDeMasuraResponseDTO(
                unitateDeMasuraSalvat.getId(),
                unitateDeMasuraSalvat.getDenumire()
        );
    }


    public UnitateDeMasuraResponseDTO update(UnitateDeMasuraRequestDTO unitateDeMasuraRequestDTO, short id) {

        UnitateDeMasura unitateDeMasuraDeModificat = unitateDeMasuraRepository.getUnitateDeMasuraById(id);

        unitateDeMasuraDeModificat.setDenumire(unitateDeMasuraRequestDTO.getDenumire());


        UnitateDeMasura unitateDeMasuraModificat = unitateDeMasuraRepository.save(unitateDeMasuraDeModificat);

        return new UnitateDeMasuraResponseDTO(
                unitateDeMasuraModificat.getId(),
                unitateDeMasuraModificat.getDenumire()
        );
    }

    public Set<UnitateDeMasuraResponseDTO> getAllUnitateDeMasurae() {
        Iterable<UnitateDeMasura> unitateDeMasuraIterable = unitateDeMasuraRepository.findAll();
        Set<UnitateDeMasuraResponseDTO> unitateDeMasuraResponseDTOSet = new HashSet<>();
        Iterator<UnitateDeMasura> unitateDeMasuraIterator = unitateDeMasuraIterable.iterator();
        while(unitateDeMasuraIterator.hasNext()) {
            UnitateDeMasura unitateDeMasura = unitateDeMasuraIterator.next();
            unitateDeMasuraResponseDTOSet.add(
                    new UnitateDeMasuraResponseDTO(
                            unitateDeMasura.getId(),
                            unitateDeMasura.getDenumire()
                    )
            );
        }
        return unitateDeMasuraResponseDTOSet;
    }


    public boolean existsById(short id) {
        return unitateDeMasuraRepository.existsById(id);
    }

    public void deleteAll() {
        unitateDeMasuraRepository.deleteAll();
    }

    public UnitateDeMasura saveDAO(UnitateDeMasura unitateDeMasura) {
        UnitateDeMasura unitateDeMasuraSalvat = unitateDeMasuraRepository.save(unitateDeMasura);

        return unitateDeMasuraSalvat;
    }

    public List<UnitateDeMasuraResponseDTO> getUnitateDeMasuraByDenumire(String denumire) {
        List<UnitateDeMasura> unitateDeMasuraList = unitateDeMasuraRepository.getUnitateDeMasuraByDenumire(denumire);

        List<UnitateDeMasuraResponseDTO> unitateDeMasuraResponseDTOS = new ArrayList<>();

        unitateDeMasuraList.forEach(
                unitateDeMasura -> {

                    unitateDeMasuraResponseDTOS.add(
                            new UnitateDeMasuraResponseDTO(
                                    unitateDeMasura.getId(),
                                    unitateDeMasura.getDenumire()
                            )
                    );
                });

        return unitateDeMasuraResponseDTOS;
    }

    public void deleteById(short id) {
        unitateDeMasuraRepository.deleteById(id);
    }

    public Iterable<UnitateDeMasura> getAllUnitateDeMasuraeDAO() {
        Iterable<UnitateDeMasura> unitateDeMasuraIterable = unitateDeMasuraRepository.findAll();
        Set<UnitateDeMasura> unitateDeMasuraSet = new HashSet<>();
        Iterator<UnitateDeMasura> unitateDeMasuraIterator = unitateDeMasuraIterable.iterator();
        while(unitateDeMasuraIterator.hasNext()) {
            UnitateDeMasura unitateDeMasura = unitateDeMasuraIterator.next();
            unitateDeMasuraSet.add(
                    new UnitateDeMasura(
                            unitateDeMasura.getId(),
                            unitateDeMasura.getDenumire()
                    )
            );
        }
        return unitateDeMasuraSet;
    }
}
