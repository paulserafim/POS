package com.POS.POS.Repository;

import com.POS.POS.Model.BonFiscal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BonFiscalRepository extends CrudRepository<BonFiscal, Long> {
    BonFiscal getBonFiscalById(long id);
    List<BonFiscal> getBonFiscalByCui(String cui);
    List<BonFiscal> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
    List<BonFiscal> findAll();
    List<BonFiscal> findAllByStatus(Enum status);
    void deleteAll();
    long count();
    boolean existsById(long id);

    List<BonFiscal> findAllByCui(String codFiscal);
}
