package com.POS.POS.Service;


import com.POS.POS.Model.GrupaTVA;
import com.POS.POS.Model.Produs;
import com.POS.POS.Model.Request.ProdusRequestDTO;
import com.POS.POS.Model.Response.ProdusResponseDTO;
import com.POS.POS.Model.Response.UnitateDeMasuraResponseDTO;
import com.POS.POS.Model.UnitateDeMasura;
import com.POS.POS.Repository.GrupaTVARepository;
import com.POS.POS.Repository.ProdusRepository;
import com.POS.POS.Repository.UnitateDeMasuraRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ProdusService {
    private ProdusRepository produsRepository;
    private UnitateDeMasuraRepository unitateDeMasuraRepository;
    private GrupaTVARepository grupaTVARepository;

    public ProdusService(ProdusRepository produsRepository, UnitateDeMasuraRepository unitateDeMasuraRepository, GrupaTVARepository grupaTVARepository) {
        this.produsRepository = produsRepository;
        this.unitateDeMasuraRepository = unitateDeMasuraRepository;
        this.grupaTVARepository = grupaTVARepository;

    }

    public Produs getProdusByCodInternDAO(long id) {
        Produs produs = produsRepository.getProdusByCodIntern(id);
        return produs;
    }

    public ProdusResponseDTO getProdusByCodIntern(long id) {
        Produs produs = produsRepository.getProdusByCodIntern(id);


        return new ProdusResponseDTO(
                produs.getId(),
                produs.getCodIntern(),
                produs.getDenumire(),
                produs.getCodExtern(),
                produs.getPret(),
                produs.getGrupaTVA().getValoare(),
                produs.getUm().getDenumire()
        );
    }


    public ProdusResponseDTO save(ProdusRequestDTO produsRequestDTO) {
        UnitateDeMasura unitateDeMasura = unitateDeMasuraRepository.getUnitateDeMasuraById(produsRequestDTO.getIdUM());
        GrupaTVA grupaTVA = grupaTVARepository.getGrupaTVAById(produsRequestDTO.getIdGrupaTVA());
        Produs produsSalvat = produsRepository.save(
                new Produs(
                        produsRequestDTO.getCodIntern(),
                        produsRequestDTO.getDenumire(),
                        produsRequestDTO.getCodExtern(),
                        produsRequestDTO.getPret(),
                        unitateDeMasura,
                        grupaTVA
                )
        );

        return new ProdusResponseDTO(
                produsSalvat.getId(),
                produsSalvat.getCodIntern(),
                produsSalvat.getDenumire(),
                produsSalvat.getCodExtern(),
                produsSalvat.getPret(),
                produsSalvat.getGrupaTVA().getValoare(),
                produsSalvat.getUm().getDenumire()
        );
    }


    public ProdusResponseDTO update(ProdusRequestDTO produsRequestDTO, long id) {
        Produs produsDeModificat = produsRepository.getProdusById(id);
        UnitateDeMasura unitateDeMasura = unitateDeMasuraRepository.getUnitateDeMasuraById(produsRequestDTO.getIdUM());
        GrupaTVA grupaTVA = grupaTVARepository.getGrupaTVAById(produsRequestDTO.getIdGrupaTVA());

        produsDeModificat.setDenumire(produsRequestDTO.getDenumire());
        produsDeModificat.setCodExtern(produsRequestDTO.getCodExtern());
        produsDeModificat.setPret(produsRequestDTO.getPret());
        produsDeModificat.setUm(unitateDeMasura);
        produsDeModificat.setGrupaTVA(grupaTVA);

        Produs produsModificat = produsRepository.save(produsDeModificat);

        return new ProdusResponseDTO(
                produsModificat.getId(),
                produsModificat.getCodIntern(),
                produsModificat.getDenumire(),
                produsModificat.getCodExtern(),
                produsModificat.getPret(),
                produsModificat.getGrupaTVA().getValoare(),
                produsModificat.getUm().getDenumire(),
                "Produsul a fost moditicat cu succes!"
        );
    }

    public Set<ProdusResponseDTO> getAllProduse() {
        Iterable<Produs> produsIterable = produsRepository.findAll();
        Set<ProdusResponseDTO> produsResponseDTOSet = new HashSet<>();
        Iterator<Produs> produsIterator = produsIterable.iterator();
        while(produsIterator.hasNext()) {
            Produs produs = produsIterator.next();

            produsResponseDTOSet.add(
                    new ProdusResponseDTO(
                            produs.getId(),
                            produs.getCodIntern(),
                            produs.getDenumire(),
                            produs.getCodExtern(),
                            produs.getPret(),
                            produs.getGrupaTVA().getValoare(),
                            produs.getUm().getDenumire()
                    )
            );
        }
        return produsResponseDTOSet;
    }

    public List<ProdusResponseDTO> getProduseByCodExtern(String codExtern) {
        List<Produs> produsByCodExtern = produsRepository.getProdusByCodExtern(codExtern);
        List <ProdusResponseDTO> produsResponseDTOS = new ArrayList<ProdusResponseDTO>();


        produsByCodExtern.forEach(
                produs -> {
                    produsResponseDTOS.add(new ProdusResponseDTO(
                            produs.getId(),
                            produs.getCodIntern(),
                            produs.getDenumire(),
                            produs.getCodExtern(),
                            produs.getPret(),
                            produs.getGrupaTVA().getValoare(),
                            produs.getUm().getDenumire()
                    ));
                }
        );

        return produsResponseDTOS;
    }

    public List<Produs> getProduseByCodExternDAO(String codExtern) {
        List<Produs> produsByCodExtern = produsRepository.getProdusByCodExtern(codExtern);

        return produsByCodExtern;
    }

    public List<ProdusResponseDTO> getProdusByDenumireProdus(String denumireProdus) {
        List<Produs> produseByDenumireProdus = produsRepository.findByDenumireContainingIgnoreCase(denumireProdus);
        List <ProdusResponseDTO> produsResponseDTOS = new ArrayList<ProdusResponseDTO>();

        produseByDenumireProdus.forEach(
                produs -> {
                    produsResponseDTOS.add(new ProdusResponseDTO(
                            produs.getId(),
                            produs.getCodIntern(),
                            produs.getDenumire(),
                            produs.getCodExtern(),
                            produs.getPret(),
                            produs.getGrupaTVA().getValoare(),
                            produs.getUm().getDenumire()
                    ));
                }
        );

        return produsResponseDTOS;
    }

    public boolean existsByCodIntern(long id) {
        return produsRepository.existsByCodIntern(id);
    }

    public List<ProdusResponseDTO> getProduseByPret(double pret) {
        List<Produs> produseByPret = produsRepository.getProdusByPret(pret);
        List <ProdusResponseDTO> produsResponseDTOS = new ArrayList<ProdusResponseDTO>();

        produseByPret.forEach(
                produs -> {
                    produsResponseDTOS.add(new ProdusResponseDTO(
                            produs.getId(),
                            produs.getCodIntern(),
                            produs.getDenumire(),
                            produs.getCodExtern(),
                            produs.getPret(),
                            produs.getGrupaTVA().getValoare(),
                            produs.getUm().getDenumire()
                    ));
                }
        );

        return produsResponseDTOS;
    }


    public void deleteAll() {
        produsRepository.deleteAll();
    }

    public Produs saveDAO(Produs produs) {
        Produs produsSalvat = produsRepository.save(produs);

        return produsSalvat;
    }

    public List<ProdusResponseDTO> getProdusByDenumirePret(String denumire, double pret) {
        List<Produs> produsList = produsRepository.findByDenumireContainingIgnoreCaseAndPretEquals(denumire, pret);

        List<ProdusResponseDTO> produsResponseDTOS = new ArrayList<>();

        produsList.forEach(
                produs -> {

                    produsResponseDTOS.add(
                            new ProdusResponseDTO(
                                    produs.getId(),
                                    produs.getCodIntern(),
                                    produs.getDenumire(),
                                    produs.getCodExtern(),
                                    produs.getPret(),
                                    produs.getGrupaTVA().getValoare(),
                                    produs.getUm().getDenumire()
                            )
                    );
                });

        return produsResponseDTOS;
    }

    public boolean existsByCodExtern(String codExtern) {
        return produsRepository.existsByCodExtern(codExtern);
    }

    public void deleteById(long id) {
        produsRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return produsRepository.existsById(id);
    }

    public Produs getProdusByIdDAO(long id) {
        return produsRepository.getProdusById(id);
    }

    public List<ProdusResponseDTO> getProduseByCharacter(String character) {
        List<ProdusResponseDTO> listOfProductsByCharacter = new ArrayList<>();
        Iterable<Produs> produsIterable = produsRepository.findAll(Sort.by(Sort.Direction.ASC, "denumire"));
        Iterator<Produs> produsIterator = produsIterable.iterator();

        while(produsIterator.hasNext()) {
            Produs produs = produsIterator.next();
            if(produs.getDenumire().startsWith(character)) {
                listOfProductsByCharacter.add(
                        new ProdusResponseDTO(
                                produs.getId(),
                                produs.getCodIntern(),
                                produs.getDenumire(),
                                produs.getCodExtern(),
                                produs.getPret(),
                                produs.getGrupaTVA().getValoare(),
                                produs.getUm().getDenumire()
                        )
                );
            }
        }
        return  listOfProductsByCharacter;
    }

    public long getSize() {
        return produsRepository.count();
    }

    public ProdusResponseDTO getProduseByCodIntern(long codIntern) {
        Produs produsByCodIntern = produsRepository.getProdusByCodIntern(codIntern);

        return new ProdusResponseDTO(
                produsByCodIntern.getId(),
                produsByCodIntern.getCodIntern(),
                produsByCodIntern.getDenumire(),
                produsByCodIntern.getCodExtern(),
                produsByCodIntern.getPret(),
                produsByCodIntern.getGrupaTVA().getValoare(),
                produsByCodIntern.getUm().getDenumire()
        );
    }
}
