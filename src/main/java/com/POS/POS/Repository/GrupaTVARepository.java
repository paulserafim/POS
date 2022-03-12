package com.POS.POS.Repository;

import com.POS.POS.Model.Produs;
import com.POS.POS.Model.GrupaTVA;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupaTVARepository extends CrudRepository<GrupaTVA, Short> {
    GrupaTVA getGrupaTVAById(short id);
    List<GrupaTVA> getGrupaTVAByValoare(Double valoare);
    void deleteById(short id);
}
