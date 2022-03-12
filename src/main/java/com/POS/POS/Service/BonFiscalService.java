package com.POS.POS.Service;


import com.POS.POS.Model.*;
import com.POS.POS.Model.Request.BonFiscalRequestDTO;
import com.POS.POS.Model.Response.BonFiscalResponseDTO;
import com.POS.POS.Model.Response.IncasareResponseDTO;
import com.POS.POS.Model.Response.IntrareBonFiscalResponseDTO;
import com.POS.POS.Model.Response.ProdusResponseDTO;
import com.POS.POS.Repository.BonFiscalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class BonFiscalService {
    private BonFiscalRepository bonFiscalRepository;
    private IncasareService incasareService;
    private IntrareBonFiscalService intrareBonFiscalService;
    private ProdusService produsService;
    private ConstantaService constantaService;

    public BonFiscalService(BonFiscalRepository bonFiscalRepository, IntrareBonFiscalService intrareBonFiscalService,
                            IncasareService incasareService, ProdusService produsService, ConstantaService constantaService) {
        this.bonFiscalRepository = bonFiscalRepository;
        this.incasareService = incasareService;
        this.intrareBonFiscalService = intrareBonFiscalService;
        this.produsService = produsService;
        this.constantaService = constantaService;
    }

    public BonFiscal getBonFiscalByIdDAO(long id) {
        BonFiscal bonFiscal = bonFiscalRepository.getBonFiscalById(id);
        return bonFiscal;
    }


    public BonFiscalResponseDTO save(BonFiscalRequestDTO bonFiscalRequestDTO) throws IOException {
        List<IntrareBonFiscal> intrareBonFiscalList = new ArrayList<>();
        bonFiscalRequestDTO.getIntrareBonFiscalList().forEach(
                intrareBonFiscal->{
                    intrareBonFiscalList.add(
                            new IntrareBonFiscal(
                                    intrareBonFiscal.getCantitate(),
                                    produsService.getProdusByIdDAO(intrareBonFiscal.getProdus().getId()),
                                    intrareBonFiscal.getDiscountPercentage(),
                                    intrareBonFiscal.getDiscountValue(),
                                    intrareBonFiscal.getIncreasePercentage(),
                                    intrareBonFiscal.getIncreaseValue(),
                                    intrareBonFiscal.isCancelled()
                            )
                    );
                }
        );


        List<Incasare> incasareList = new ArrayList<>();
        bonFiscalRequestDTO.getIncasareList().forEach(
                incasare-> {
                    incasareList.add(
                            new Incasare(
                                    incasare.getInstrument(),
                                    incasare.getValue()
                            )
                    );
                }
        );

        BonFiscal bonFiscalSalvat = bonFiscalRepository.save(
                new BonFiscal(
                        bonFiscalRequestDTO.getCui(),
                        incasareList,
                        intrareBonFiscalList,
                        bonFiscalRequestDTO.isFiscal(),
                        bonFiscalRequestDTO.isCancelled(),
                        bonFiscalRequestDTO.getText(),
                        bonFiscalRequestDTO.getDiscountPercentage(),
                        bonFiscalRequestDTO.getDiscountValue(),
                        bonFiscalRequestDTO.getIncreasePercentage(),
                        bonFiscalRequestDTO.getIncreaseValue(),
                        bonFiscalRequestDTO.getStatus()
                )
        );
        bonFiscalRepository.save(bonFiscalSalvat);

        intrareBonFiscalList.forEach(intrareBonFiscal -> {
            intrareBonFiscal.setBonFiscal(bonFiscalSalvat);
            intrareBonFiscalService.saveDAO(intrareBonFiscal);
        });

        incasareList.forEach(incasare -> {
            incasare.setBonFiscal(bonFiscalSalvat);
            incasareService.saveDAO(incasare);
        });

        List<IntrareBonFiscalResponseDTO> intrareBonFiscalResponseDTOList = new ArrayList<>();
        bonFiscalSalvat.getIntrareBonFiscalList().forEach(
                intrareBonFiscalDTO -> {
                        intrareBonFiscalResponseDTOList.add(
                                new IntrareBonFiscalResponseDTO(
                                    intrareBonFiscalDTO.getId(),
                                    intrareBonFiscalDTO.getCantitate(),
                                    new ProdusResponseDTO(
                                        intrareBonFiscalDTO.getProdus().getId(),
                                        intrareBonFiscalDTO.getProdus().getCodIntern(),
                                        intrareBonFiscalDTO.getProdus().getDenumire(),
                                        intrareBonFiscalDTO.getProdus().getCodExtern(),
                                        intrareBonFiscalDTO.getProdus().getPret(),
                                        intrareBonFiscalDTO.getProdus().getGrupaTVA().getValoare(),
                                        intrareBonFiscalDTO.getProdus().getUm().getDenumire()
                                    ),
                                    intrareBonFiscalDTO.getDiscountPercentage(),
                                    intrareBonFiscalDTO.getDiscountValue(),
                                    intrareBonFiscalDTO.getIncreasePercentage(),
                                    intrareBonFiscalDTO.getIncreaseValue(),
                                    intrareBonFiscalDTO.isCancelled()
                                )
                        );
                }
        );
        List<IncasareResponseDTO> incasareResponseDTOList = new ArrayList<>();
        bonFiscalSalvat.getIncasareList().forEach(
                incasareDTO-> {
                    incasareResponseDTOList.add(
                            new IncasareResponseDTO(
                                incasareDTO.getId(),
                                incasareDTO.getInstrument(),
                                incasareDTO.getValue()
                            )
                    );
                }
        );

        if(bonFiscalSalvat.getStatus().equals(StatusBonFiscal.LISTAT)) {
            BonFiscalFiscalNetFile bonFiscalFiscalNetFile = new BonFiscalFiscalNetFile();
            bonFiscalFiscalNetFile.setBonFiscal(bonFiscalSalvat);
            bonFiscalFiscalNetFile.setPath(constantaService.getValoareConstantaTextByDenumire("caleFisierBonFiscal"));
            bonFiscalFiscalNetFile.convertToFiscalNetText();
            bonFiscalFiscalNetFile.createFile(constantaService.getValoareConstantaTextByDenumire("caleFisierBonFiscal"));
        }

        return new BonFiscalResponseDTO(
                bonFiscalSalvat.getId(),
                bonFiscalSalvat.getCui(),
                incasareResponseDTOList,
                intrareBonFiscalResponseDTOList,
                bonFiscalSalvat.isFiscal(),
                bonFiscalSalvat.isCancelled(),
                bonFiscalSalvat.getText(),
                bonFiscalSalvat.getDate(),
                bonFiscalSalvat.getDiscountPercentage(),
                bonFiscalSalvat.getDiscountValue(),
                bonFiscalSalvat.getIncreasePercentage(),
                bonFiscalSalvat.getIncreaseValue(),
                bonFiscalSalvat.getStatus()
        );
    }

    public void deleteAll() {
        bonFiscalRepository.deleteAll();
    }

    public BonFiscal saveDAO(BonFiscal bonFiscal) {
        BonFiscal bonFiscalSalvat = bonFiscalRepository.save(bonFiscal);

        return bonFiscalSalvat;
    }

    public boolean existsById(long id) {
        return bonFiscalRepository.existsById(id);
    }

    public long getSize() {
        return bonFiscalRepository.count();
    }

    public List<BonFiscalResponseDTO> getBonFiscalByDateBetween(LocalDate startDate, LocalDate endDate) {
        List<BonFiscal> bonFiscalList = bonFiscalRepository.findAllByDateBetween(startDate, endDate);
        List<BonFiscalResponseDTO> bonFiscalResponseDTOList = new ArrayList<>();

        bonFiscalList.forEach(bonFiscal -> {
            List<IncasareResponseDTO> incasareResponseDTOList = new ArrayList<>();
            bonFiscal.getIncasareList().forEach(incasare -> {
                incasareResponseDTOList.add(new IncasareResponseDTO(
                        incasare.getId(),
                        incasare.getInstrument(),
                        incasare.getValue()
                ));
            });

            List<IntrareBonFiscalResponseDTO> intrareBonFiscalResponseDTOList = new ArrayList<>();
            bonFiscal.getIntrareBonFiscalList().forEach(intrareBonFiscal -> {
                intrareBonFiscalResponseDTOList.add(new IntrareBonFiscalResponseDTO(
                        intrareBonFiscal.getId(),
                        intrareBonFiscal.getCantitate(),
                        new ProdusResponseDTO(
                                intrareBonFiscal.getProdus().getId(),
                                intrareBonFiscal.getProdus().getCodIntern(),
                                intrareBonFiscal.getProdus().getDenumire(),
                                intrareBonFiscal.getProdus().getCodExtern(),
                                intrareBonFiscal.getProdus().getPret(),
                                intrareBonFiscal.getProdus().getGrupaTVA().getValoare(),
                                intrareBonFiscal.getProdus().getUm().getDenumire(),
                                ""
                        ),
                        intrareBonFiscal.getDiscountPercentage(),
                        intrareBonFiscal.getDiscountValue(),
                        intrareBonFiscal.getIncreasePercentage(),
                        intrareBonFiscal.getIncreaseValue(),
                        intrareBonFiscal.isCancelled()
                ));
            });
            bonFiscalResponseDTOList.add(new BonFiscalResponseDTO(
                    bonFiscal.getId(),
                    bonFiscal.getCui(),
                    incasareResponseDTOList,
                    intrareBonFiscalResponseDTOList,
                    bonFiscal.isFiscal(),
                    bonFiscal.isCancelled(),
                    bonFiscal.getText(),
                    bonFiscal.getDate(),
                    bonFiscal.getDiscountPercentage(),
                    bonFiscal.getDiscountValue(),
                    bonFiscal.getIncreasePercentage(),
                    bonFiscal.getIncreaseValue(),
                    bonFiscal.getStatus()
            ));
        });

        return bonFiscalResponseDTOList;
    }

    public List<BonFiscalResponseDTO> getAll() {
        List<BonFiscal> bonFiscalList = bonFiscalRepository.findAll();
        List<BonFiscalResponseDTO> bonFiscalResponseDTOList = new ArrayList<>();

        bonFiscalList.forEach(bonFiscal -> {
            List<IncasareResponseDTO> incasareResponseDTOList = new ArrayList<>();
            bonFiscal.getIncasareList().forEach(incasare -> {
                incasareResponseDTOList.add(new IncasareResponseDTO(
                        incasare.getId(),
                        incasare.getInstrument(),
                        incasare.getValue()
                ));
            });

            List<IntrareBonFiscalResponseDTO> intrareBonFiscalResponseDTOList = new ArrayList<>();
            bonFiscal.getIntrareBonFiscalList().forEach(intrareBonFiscal -> {
                intrareBonFiscalResponseDTOList.add(new IntrareBonFiscalResponseDTO(
                        intrareBonFiscal.getId(),
                        intrareBonFiscal.getCantitate(),
                        new ProdusResponseDTO(
                                intrareBonFiscal.getProdus().getId(),
                                intrareBonFiscal.getProdus().getCodIntern(),
                                intrareBonFiscal.getProdus().getDenumire(),
                                intrareBonFiscal.getProdus().getCodExtern(),
                                intrareBonFiscal.getProdus().getPret(),
                                intrareBonFiscal.getProdus().getGrupaTVA().getValoare(),
                                intrareBonFiscal.getProdus().getUm().getDenumire(),
                                ""
                        ),
                        intrareBonFiscal.getDiscountPercentage(),
                        intrareBonFiscal.getDiscountValue(),
                        intrareBonFiscal.getIncreasePercentage(),
                        intrareBonFiscal.getIncreaseValue(),
                        intrareBonFiscal.isCancelled()
                ));
            });
            bonFiscalResponseDTOList.add(new BonFiscalResponseDTO(
                    bonFiscal.getId(),
                    bonFiscal.getCui(),
                    incasareResponseDTOList,
                    intrareBonFiscalResponseDTOList,
                    bonFiscal.isFiscal(),
                    bonFiscal.isCancelled(),
                    bonFiscal.getText(),
                    bonFiscal.getDate(),
                    bonFiscal.getDiscountPercentage(),
                    bonFiscal.getDiscountValue(),
                    bonFiscal.getIncreasePercentage(),
                    bonFiscal.getIncreaseValue(),
                    bonFiscal.getStatus()
            ));
        });

        return bonFiscalResponseDTOList;

    }

    public List<BonFiscalResponseDTO> getBonFiscalByCodFiscal(String codFiscal) {
        List<BonFiscal> bonFiscalList = bonFiscalRepository.findAllByCui(codFiscal);
        List<BonFiscalResponseDTO> bonFiscalResponseDTOList = new ArrayList<>();

        bonFiscalList.forEach(bonFiscal -> {
            List<IncasareResponseDTO> incasareResponseDTOList = new ArrayList<>();
            bonFiscal.getIncasareList().forEach(incasare -> {
                incasareResponseDTOList.add(new IncasareResponseDTO(
                        incasare.getId(),
                        incasare.getInstrument(),
                        incasare.getValue()
                ));
            });

            List<IntrareBonFiscalResponseDTO> intrareBonFiscalResponseDTOList = new ArrayList<>();
            bonFiscal.getIntrareBonFiscalList().forEach(intrareBonFiscal -> {
                intrareBonFiscalResponseDTOList.add(new IntrareBonFiscalResponseDTO(
                        intrareBonFiscal.getId(),
                        intrareBonFiscal.getCantitate(),
                        new ProdusResponseDTO(
                                intrareBonFiscal.getProdus().getId(),
                                intrareBonFiscal.getProdus().getCodIntern(),
                                intrareBonFiscal.getProdus().getDenumire(),
                                intrareBonFiscal.getProdus().getCodExtern(),
                                intrareBonFiscal.getProdus().getPret(),
                                intrareBonFiscal.getProdus().getGrupaTVA().getValoare(),
                                intrareBonFiscal.getProdus().getUm().getDenumire(),
                                ""
                        ),
                        intrareBonFiscal.getDiscountPercentage(),
                        intrareBonFiscal.getDiscountValue(),
                        intrareBonFiscal.getIncreasePercentage(),
                        intrareBonFiscal.getIncreaseValue(),
                        intrareBonFiscal.isCancelled()
                ));
            });
            bonFiscalResponseDTOList.add(new BonFiscalResponseDTO(
                    bonFiscal.getId(),
                    bonFiscal.getCui(),
                    incasareResponseDTOList,
                    intrareBonFiscalResponseDTOList,
                    bonFiscal.isFiscal(),
                    bonFiscal.isCancelled(),
                    bonFiscal.getText(),
                    bonFiscal.getDate(),
                    bonFiscal.getDiscountPercentage(),
                    bonFiscal.getDiscountValue(),
                    bonFiscal.getIncreasePercentage(),
                    bonFiscal.getIncreaseValue(),
                    bonFiscal.getStatus()
            ));
        });

        return bonFiscalResponseDTOList;

    }

    public List<BonFiscalResponseDTO> getBonFiscalByStatus(String status) {
        StatusBonFiscal statusBonFiscalEnum = StatusBonFiscal.valueOf(status);
        List<BonFiscal> bonFiscalList = bonFiscalRepository.findAllByStatus(statusBonFiscalEnum);
        List<BonFiscalResponseDTO> bonFiscalResponseDTOList = new ArrayList<>();

        bonFiscalList.forEach(bonFiscal -> {
            List<IncasareResponseDTO> incasareResponseDTOList = new ArrayList<>();
            bonFiscal.getIncasareList().forEach(incasare -> {
                incasareResponseDTOList.add(new IncasareResponseDTO(
                        incasare.getId(),
                        incasare.getInstrument(),
                        incasare.getValue()
                ));
            });

            List<IntrareBonFiscalResponseDTO> intrareBonFiscalResponseDTOList = new ArrayList<>();
            bonFiscal.getIntrareBonFiscalList().forEach(intrareBonFiscal -> {
                intrareBonFiscalResponseDTOList.add(new IntrareBonFiscalResponseDTO(
                        intrareBonFiscal.getId(),
                        intrareBonFiscal.getCantitate(),
                        new ProdusResponseDTO(
                                intrareBonFiscal.getProdus().getId(),
                                intrareBonFiscal.getProdus().getCodIntern(),
                                intrareBonFiscal.getProdus().getDenumire(),
                                intrareBonFiscal.getProdus().getCodExtern(),
                                intrareBonFiscal.getProdus().getPret(),
                                intrareBonFiscal.getProdus().getGrupaTVA().getValoare(),
                                intrareBonFiscal.getProdus().getUm().getDenumire(),
                                ""
                        ),
                        intrareBonFiscal.getDiscountPercentage(),
                        intrareBonFiscal.getDiscountValue(),
                        intrareBonFiscal.getIncreasePercentage(),
                        intrareBonFiscal.getIncreaseValue(),
                        intrareBonFiscal.isCancelled()
                ));
            });
            bonFiscalResponseDTOList.add(new BonFiscalResponseDTO(
                    bonFiscal.getId(),
                    bonFiscal.getCui(),
                    incasareResponseDTOList,
                    intrareBonFiscalResponseDTOList,
                    bonFiscal.isFiscal(),
                    bonFiscal.isCancelled(),
                    bonFiscal.getText(),
                    bonFiscal.getDate(),
                    bonFiscal.getDiscountPercentage(),
                    bonFiscal.getDiscountValue(),
                    bonFiscal.getIncreasePercentage(),
                    bonFiscal.getIncreaseValue(),
                    bonFiscal.getStatus()
            ));
        });

        return bonFiscalResponseDTOList;
    }

    public void updateBonFiscal(BonFiscalRequestDTO bonFiscalRequestDTO, long id) {
        BonFiscal bonFiscal = bonFiscalRepository.getBonFiscalById(id);

        bonFiscal.setStatus(bonFiscalRequestDTO.getStatus());
        bonFiscalRepository.save(bonFiscal);
    }
}
