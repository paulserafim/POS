package com.POS.POS.Repository;

import com.POS.POS.Model.BonFiscal;
import com.POS.POS.Model.Incasare;
import com.POS.POS.Model.Produs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncasareRepository extends CrudRepository<Incasare, Long> {
    Incasare getIncasareById(long id);
    List<Incasare> getIncasareByBonFiscal(BonFiscal bonFiscal);
    void deleteAll();
    long count();
    boolean existsById(long id);
}
