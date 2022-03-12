package com.POS.POS.Repository;

import com.POS.POS.Model.Produs;
import com.POS.POS.Model.UnitateDeMasura;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitateDeMasuraRepository extends CrudRepository<UnitateDeMasura, Short> {
    UnitateDeMasura getUnitateDeMasuraById(short id);
    List<UnitateDeMasura> getUnitateDeMasuraByDenumire(String denumire);
    void deleteById(short id);
}
