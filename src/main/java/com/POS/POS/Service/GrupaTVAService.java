package com.POS.POS.Service;

import com.POS.POS.Model.GrupaTVA;
import com.POS.POS.Model.Request.GrupaTVARequestDTO;
import com.POS.POS.Model.Response.GrupaTVAResponseDTO;
import com.POS.POS.Repository.GrupaTVARepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrupaTVAService {
    private GrupaTVARepository grupaTVARepository;

    public GrupaTVAService(GrupaTVARepository grupaTVARepository) {
        this.grupaTVARepository = grupaTVARepository;

    }

    public GrupaTVA getGrupaTVAByIdDAO(short id) {
        GrupaTVA grupaTVA = grupaTVARepository.getGrupaTVAById(id);
        return grupaTVA;
    }

    public GrupaTVAResponseDTO getGrupaTVAById(short id) {
        GrupaTVA grupaTVA = grupaTVARepository.getGrupaTVAById(id);


        return new GrupaTVAResponseDTO(
                grupaTVA.getId(),
                grupaTVA.getNumarGrupa(),
                grupaTVA.getValoare()
        );
    }


    public GrupaTVAResponseDTO save(GrupaTVARequestDTO grupaTVARequestDTO) {
        GrupaTVA grupaTVA = grupaTVARepository.getGrupaTVAById(grupaTVARequestDTO.getId());
        GrupaTVA grupaTVASalvat = grupaTVARepository.save(
                new GrupaTVA(
                        grupaTVARequestDTO.getId(),
                        grupaTVARequestDTO.getValoare()
                )
        );

        return new GrupaTVAResponseDTO(
                grupaTVASalvat.getId(),
                grupaTVASalvat.getNumarGrupa(),
                grupaTVASalvat.getValoare()
        );
    }


    public GrupaTVAResponseDTO update(GrupaTVARequestDTO grupaTVARequestDTO, short id) {

        GrupaTVA grupaTVADeModificat = grupaTVARepository.getGrupaTVAById(grupaTVARequestDTO.getId());

        grupaTVADeModificat.setValoare(grupaTVARequestDTO.getValoare());


        GrupaTVA grupaTVAModificat = grupaTVARepository.save(grupaTVADeModificat);

        return new GrupaTVAResponseDTO(
                grupaTVAModificat.getId(),
                grupaTVAModificat.getNumarGrupa(),
                grupaTVAModificat.getValoare()
        );
    }

    public Set<GrupaTVAResponseDTO> getAllGrupaTVAe() {
        Iterable<GrupaTVA> grupaTVAIterable = grupaTVARepository.findAll();
        Set<GrupaTVAResponseDTO> grupaTVAResponseDTOSet = new HashSet<>();
        Iterator<GrupaTVA> grupaTVAIterator = grupaTVAIterable.iterator();
        while(grupaTVAIterator.hasNext()) {
            GrupaTVA grupaTVA = grupaTVAIterator.next();
            grupaTVAResponseDTOSet.add(
                    new GrupaTVAResponseDTO(
                            grupaTVA.getId(),
                            grupaTVA.getNumarGrupa(),
                            grupaTVA.getValoare()
                    )
            );
        }
        return grupaTVAResponseDTOSet;
    }


    public boolean existsById(short id) {
        return grupaTVARepository.existsById(id);
    }

    public void deleteAll() {
        grupaTVARepository.deleteAll();
    }

    public GrupaTVA saveDAO(GrupaTVA grupaTVA) {
        GrupaTVA grupaTVASalvat = grupaTVARepository.save(grupaTVA);

        return grupaTVASalvat;
    }

    public List<GrupaTVAResponseDTO> getGrupaTVAByValoare(double valoare) {
        List<GrupaTVA> grupaTVAList = grupaTVARepository.getGrupaTVAByValoare(valoare);

        List<GrupaTVAResponseDTO> grupaTVAResponseDTOS = new ArrayList<>();

        grupaTVAList.forEach(
                grupaTVA -> {

                    grupaTVAResponseDTOS.add(
                            new GrupaTVAResponseDTO(
                                    grupaTVA.getId(),
                                    grupaTVA.getNumarGrupa(),
                                    grupaTVA.getValoare()
                            )
                    );
                });

        return grupaTVAResponseDTOS;
    }

    public short getLastAvailableNumarGrupaTVA() {
        Set<Short> grupaTVASet = new HashSet<>();
        grupaTVARepository.findAll().forEach(grupaTVA -> {
            grupaTVASet.add(grupaTVA.getNumarGrupa());
        });
        if(grupaTVASet.size() == 0) {
            return 1;
        }
        for (short index = 1; index <= grupaTVASet.size(); index ++) {
            if(!grupaTVASet.contains(index)) {
                return index;
            }
        }
        return (short) (grupaTVASet.size() + 1);
    }

    public void deleteById(short id) {
        grupaTVARepository.deleteById(id);
    }

    public List<GrupaTVA> getGrupaTVAByValoareDAO(double v) {
        return grupaTVARepository.getGrupaTVAByValoare(v);
    }
}
