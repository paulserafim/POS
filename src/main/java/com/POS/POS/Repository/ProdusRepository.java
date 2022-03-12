package com.POS.POS.Repository;

import com.POS.POS.Model.Produs;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdusRepository extends CrudRepository<Produs, Long> {
    Produs getProdusByCodIntern(long codIntern);
    List<Produs> getProdusByCodExtern(String codExtern);
    Produs getProdusById(long id);
    List<Produs> getProdusByPret(double pret);
    List<Produs> findByDenumireContainingIgnoreCase(String denumire);
    void deleteAll();
    long count();
    boolean existsByCodIntern(long codIntern);
    boolean existsById(long id);
    List<Produs> findByDenumireContainingIgnoreCaseAndPretEquals(String denumire, double pret);

    boolean existsByCodExtern(String codExtern);

    Iterable<Produs> findAll(Sort denumire);
}
