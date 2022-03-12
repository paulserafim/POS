package com.POS.POS.Repository;

import com.POS.POS.Model.IntrareBonFiscal;
import com.POS.POS.Model.Produs;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntrareBonFiscalRepository extends CrudRepository<IntrareBonFiscal, Long> {
    IntrareBonFiscal getIntrareBonFiscalById(long id);
    List<IntrareBonFiscal> getIntrareBonFiscalByProdus(Produs produs);
    void deleteAll();
    long count();
    boolean existsById(long id);
    void deleteById(long id);
}
