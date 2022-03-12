package com.POS.POS.Service;


import com.POS.POS.Model.GrupaTVA;
import com.POS.POS.Model.IntrareBonFiscal;
import com.POS.POS.Model.Produs;
import com.POS.POS.Model.Request.IntrareBonFiscalRequestDTO;
import com.POS.POS.Model.Response.IntrareBonFiscalResponseDTO;
import com.POS.POS.Model.Response.ProdusResponseDTO;
import com.POS.POS.Model.UnitateDeMasura;
import com.POS.POS.Repository.GrupaTVARepository;
import com.POS.POS.Repository.IntrareBonFiscalRepository;
import com.POS.POS.Repository.ProdusRepository;
import com.POS.POS.Repository.UnitateDeMasuraRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class IntrareBonFiscalService {
    private IntrareBonFiscalRepository intrareBonFiscalRepository;
    private UnitateDeMasuraRepository unitateDeMasuraRepository;
    private GrupaTVARepository grupaTVARepository;
    private ProdusRepository produsRepository;

    public IntrareBonFiscalService(IntrareBonFiscalRepository intrareBonFiscalRepository, UnitateDeMasuraRepository unitateDeMasuraRepository, GrupaTVARepository grupaTVARepository, ProdusRepository produsRepository) {
        this.intrareBonFiscalRepository = intrareBonFiscalRepository;
        this.unitateDeMasuraRepository = unitateDeMasuraRepository;
        this.grupaTVARepository = grupaTVARepository;
        this.produsRepository = produsRepository;
    }

    public IntrareBonFiscalResponseDTO save(IntrareBonFiscalRequestDTO intrareBonFiscalRequestDTO) {
        UnitateDeMasura unitateDeMasura = unitateDeMasuraRepository.getUnitateDeMasuraById(intrareBonFiscalRequestDTO.getProdus().getIdUM());
        GrupaTVA grupaTVA = grupaTVARepository.getGrupaTVAById(intrareBonFiscalRequestDTO.getProdus().getIdGrupaTVA());
        Produs produs = produsRepository.getProdusById(intrareBonFiscalRequestDTO.getProdus().getId());
        IntrareBonFiscal intrareBonFiscalSalvat = intrareBonFiscalRepository.save(
                new IntrareBonFiscal(
                        intrareBonFiscalRequestDTO.getCantitate(),
                        produs,
                        intrareBonFiscalRequestDTO.getDiscountPercentage(),
                        intrareBonFiscalRequestDTO.getDiscountValue(),
                        intrareBonFiscalRequestDTO.getIncreasePercentage(),
                        intrareBonFiscalRequestDTO.getIncreaseValue(),
                        intrareBonFiscalRequestDTO.isCancelled()
                )
        );

        return new IntrareBonFiscalResponseDTO(
                intrareBonFiscalSalvat.getId(),
                intrareBonFiscalSalvat.getCantitate(),
                new ProdusResponseDTO(
                        intrareBonFiscalSalvat.getProdus().getId(),
                        intrareBonFiscalSalvat.getProdus().getCodIntern(),
                        intrareBonFiscalSalvat.getProdus().getDenumire(),
                        intrareBonFiscalSalvat.getProdus().getCodExtern(),
                        intrareBonFiscalSalvat.getProdus().getPret(),
                        intrareBonFiscalSalvat.getProdus().getGrupaTVA().getValoare(),
                        intrareBonFiscalSalvat.getProdus().getUm().getDenumire()
                ),
                intrareBonFiscalSalvat.getDiscountPercentage(),
                intrareBonFiscalSalvat.getDiscountValue(),
                intrareBonFiscalSalvat.getIncreasePercentage(),
                intrareBonFiscalSalvat.getIncreaseValue(),
                intrareBonFiscalSalvat.isCancelled()
        );
    }

    public Set<IntrareBonFiscalResponseDTO> getAllIntrareBonFiscal() {
        Iterable<IntrareBonFiscal> intrareBonFiscalIterable = intrareBonFiscalRepository.findAll();
        Set<IntrareBonFiscalResponseDTO> intrareBonFiscalResponseDTOSet = new HashSet<>();
        Iterator<IntrareBonFiscal> intrareBonFiscalIterator = intrareBonFiscalIterable.iterator();
        while(intrareBonFiscalIterator.hasNext()) {
            IntrareBonFiscal intrareBonFiscal = intrareBonFiscalIterator.next();

            intrareBonFiscalResponseDTOSet.add(
                    new IntrareBonFiscalResponseDTO(
                            intrareBonFiscal.getId(),
                            intrareBonFiscal.getCantitate(),
                            new ProdusResponseDTO(
                                    intrareBonFiscal.getProdus().getId(),
                                    intrareBonFiscal.getProdus().getCodIntern(),
                                    intrareBonFiscal.getProdus().getDenumire(),
                                    intrareBonFiscal.getProdus().getCodExtern(),
                                    intrareBonFiscal.getProdus().getPret(),
                                    intrareBonFiscal.getProdus().getGrupaTVA().getValoare(),
                                    intrareBonFiscal.getProdus().getUm().getDenumire()
                            ),
                            intrareBonFiscal.getDiscountPercentage(),
                            intrareBonFiscal.getDiscountValue(),
                            intrareBonFiscal.getIncreasePercentage(),
                            intrareBonFiscal.getIncreaseValue(),
                            intrareBonFiscal.isCancelled()
                    )
            );
        }
        return intrareBonFiscalResponseDTOSet;
    }


    public List<IntrareBonFiscalResponseDTO> getIntrareBonFiscaleByProdusCodExtern(String codExtern) {
        List<Produs> produsList = produsRepository.getProdusByCodExtern(codExtern);
        List<IntrareBonFiscal> intrareBonFiscalList = intrareBonFiscalRepository.getIntrareBonFiscalByProdus(produsList.get(0));
        List <IntrareBonFiscalResponseDTO> intrareBonFiscalResponseDTOS = new ArrayList<IntrareBonFiscalResponseDTO>();

        intrareBonFiscalList.forEach(
                intrareBonFiscal -> {
                    intrareBonFiscalResponseDTOS.add(
                            new IntrareBonFiscalResponseDTO(
                                    intrareBonFiscal.getId(),
                                    intrareBonFiscal.getCantitate(),
                                    new ProdusResponseDTO(
                                            intrareBonFiscal.getProdus().getId(),
                                            intrareBonFiscal.getProdus().getCodIntern(),
                                            intrareBonFiscal.getProdus().getDenumire(),
                                            intrareBonFiscal.getProdus().getCodExtern(),
                                            intrareBonFiscal.getProdus().getPret(),
                                            intrareBonFiscal.getProdus().getGrupaTVA().getValoare(),
                                            intrareBonFiscal.getProdus().getUm().getDenumire()
                                    ),
                                    intrareBonFiscal.getDiscountPercentage(),
                                    intrareBonFiscal.getDiscountValue(),
                                    intrareBonFiscal.getIncreasePercentage(),
                                    intrareBonFiscal.getIncreaseValue(),
                                    intrareBonFiscal.isCancelled()
                            )
                    );
                }
        );

        return intrareBonFiscalResponseDTOS;
    }

    public List<IntrareBonFiscalResponseDTO> getIntrareBonFiscaleByProdusCodIntern(long codIntern) {
        Produs produs = produsRepository.getProdusByCodIntern(codIntern);
        List<IntrareBonFiscal> intrareBonFiscalList = intrareBonFiscalRepository.getIntrareBonFiscalByProdus(produs);
        List<IntrareBonFiscalResponseDTO> intrareBonFiscalResponseDTOS = new ArrayList<IntrareBonFiscalResponseDTO>();

        intrareBonFiscalList.forEach(
                intrareBonFiscal -> {
                    intrareBonFiscalResponseDTOS.add(
                            new IntrareBonFiscalResponseDTO(
                                    intrareBonFiscal.getId(),
                                    intrareBonFiscal.getCantitate(),
                                    new ProdusResponseDTO(
                                            intrareBonFiscal.getProdus().getId(),
                                            intrareBonFiscal.getProdus().getCodIntern(),
                                            intrareBonFiscal.getProdus().getDenumire(),
                                            intrareBonFiscal.getProdus().getCodExtern(),
                                            intrareBonFiscal.getProdus().getPret(),
                                            intrareBonFiscal.getProdus().getGrupaTVA().getValoare(),
                                            intrareBonFiscal.getProdus().getUm().getDenumire()
                                    ),
                                    intrareBonFiscal.getDiscountPercentage(),
                                    intrareBonFiscal.getDiscountValue(),
                                    intrareBonFiscal.getIncreasePercentage(),
                                    intrareBonFiscal.getIncreaseValue(),
                                    intrareBonFiscal.isCancelled()
                            )
                    );
                }
        );
        return intrareBonFiscalResponseDTOS;
    }


    public void deleteAll() {
        intrareBonFiscalRepository.deleteAll();
    }

    public IntrareBonFiscal saveDAO(IntrareBonFiscal intrareBonFiscal) {
        IntrareBonFiscal intrareBonFiscalSalvat = intrareBonFiscalRepository.save(intrareBonFiscal);

        return intrareBonFiscalSalvat;
    }


    public void deleteById(long id) {
        intrareBonFiscalRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return intrareBonFiscalRepository.existsById(id);
    }

    public IntrareBonFiscal getIntrareBonFiscalByIdDAO(long id) {
        return intrareBonFiscalRepository.getIntrareBonFiscalById(id);
    }

    public long getSize() {
        return intrareBonFiscalRepository.count();
    }

}
