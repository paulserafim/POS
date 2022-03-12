package com.POS.POS.Repository;

import com.POS.POS.Model.Produs;
import com.POS.POS.Model.TastaRapida;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TastaRapidaRepository extends CrudRepository<TastaRapida, Short> {
    TastaRapida getTastaRapidaById(short id);
    TastaRapida getTastaRapidaByNumar(short numar);
    void deleteById(short id);
    List<TastaRapida> getTastaRapidaByDenumire(String denumire);
}
