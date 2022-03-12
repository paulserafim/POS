package com.POS.POS.Service;

import com.POS.POS.Model.Constanta;
import com.POS.POS.Model.Request.ConstantaRequestDTO;
import com.POS.POS.Model.Response.ConstantaResponseDTO;
import com.POS.POS.Repository.ConstantaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConstantaService {
    private ConstantaRepository constantaRepository;

    public ConstantaService(ConstantaRepository constantaRepository) {
        this.constantaRepository = constantaRepository;

    }

    public Constanta getConstantaByIdDAO(short id) {
        Constanta constanta = constantaRepository.getConstantaById(id);
        return constanta;
    }

    public ConstantaResponseDTO getConstantaById(short id) {
        Constanta constanta = constantaRepository.getConstantaById(id);


        return new ConstantaResponseDTO(
                constanta.getId(),
                constanta.getDenumire(),
                constanta.getValoareText(),
                constanta.getValoareNumericaIntreaga(),
                constanta.getValoareNumerica()
        );
    }


    public ConstantaResponseDTO save(ConstantaRequestDTO constantaRequestDTO) {

        Constanta constantaSalvat = constantaRepository.save(
                new Constanta(
                        constantaRequestDTO.getDenumire(),
                        constantaRequestDTO.getValoareText(),
                        constantaRequestDTO.getValoareNumericaIntreaga(),
                        constantaRequestDTO.getValoareNumerica()
                )
        );

        return new ConstantaResponseDTO(
                constantaSalvat.getId(),
                constantaSalvat.getDenumire(),
                constantaSalvat.getValoareText(),
                constantaSalvat.getValoareNumericaIntreaga(),
                constantaSalvat.getValoareNumerica(),
                "Constanta: " + constantaSalvat.getDenumire() + " a fost creata cu succes cu valoarea text: " + constantaSalvat.getValoareText() + ", valoarea numerica intreaga: " + constantaSalvat.getValoareNumericaIntreaga() + " si valoarea numerica: " + constantaSalvat.getValoareNumerica()
        );
    }


    public ConstantaResponseDTO update(ConstantaRequestDTO constantaRequestDTO, short id) {

        Constanta constantaDeModificat = constantaRepository.getConstantaById(id);

        constantaDeModificat.setValoareText(constantaRequestDTO.getValoareText());

        constantaDeModificat.setValoareNumerica(constantaRequestDTO.getValoareNumerica());

        constantaDeModificat.setValoareNumericaIntreaga(constantaRequestDTO.getValoareNumericaIntreaga());


        Constanta constantaModificat = constantaRepository.save(constantaDeModificat);

        return new ConstantaResponseDTO(
                constantaDeModificat.getId(),
                constantaDeModificat.getDenumire(),
                constantaDeModificat.getValoareText(),
                constantaDeModificat.getValoareNumericaIntreaga(),
                constantaDeModificat.getValoareNumerica(),
                "Constanta: " + constantaDeModificat.getDenumire() + " a fost actualizata cu succes cu valoarea text: " + constantaDeModificat.getValoareText() + ", valoarea numerica intreaga: " + constantaDeModificat.getValoareNumericaIntreaga() + " si valoarea numerica: " + constantaDeModificat.getValoareNumerica()
        );
    }

    public Set<ConstantaResponseDTO> getAllConstante() {
        Iterable<Constanta> constantaIterable = constantaRepository.findAll();
        Set<ConstantaResponseDTO> constantaResponseDTOSet = new HashSet<>();
        Iterator<Constanta> constantaIterator = constantaIterable.iterator();
        while(constantaIterator.hasNext()) {
            Constanta constanta = constantaIterator.next();
            constantaResponseDTOSet.add(
                    new ConstantaResponseDTO(
                            constanta.getId(),
                            constanta.getDenumire(),
                            constanta.getValoareText(),
                            constanta.getValoareNumericaIntreaga(),
                            constanta.getValoareNumerica()
                    )
            );
        }
        return constantaResponseDTOSet;
    }


    public boolean existsById(short id) {
        return constantaRepository.existsById(id);
    }

    public void deleteAll() {
        constantaRepository.deleteAll();
    }

    public Constanta saveDAO(Constanta constanta) {
        Constanta constantaSalvat = constantaRepository.save(constanta);

        return constantaSalvat;
    }

    public List<ConstantaResponseDTO> getConstantaByValoare(double valoare) {
        List<Constanta> constantaList = constantaRepository.getConstantaByValoareNumerica(valoare);

        List<ConstantaResponseDTO> constantaResponseDTOS = new ArrayList<>();

        constantaList.forEach(
                constanta -> {

                    constantaResponseDTOS.add(
                            new ConstantaResponseDTO(
                                    constanta.getId(),
                                    constanta.getDenumire(),
                                    constanta.getValoareText(),
                                    constanta.getValoareNumericaIntreaga(),
                                    constanta.getValoareNumerica()
                            )
                    );
                });

        return constantaResponseDTOS;
    }

    public void deleteById(short id) {
        constantaRepository.deleteById(id);
    }
    

    public String getValoareConstantaTextByDenumire(String directorImportWinMentor) {
        return constantaRepository.getConstantaByDenumire(directorImportWinMentor).get(0).getValoareText();
    }
}
