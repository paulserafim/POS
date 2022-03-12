package com.POS.POS.Repository;

import com.POS.POS.Model.Produs;
import com.POS.POS.Model.Constanta;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConstantaRepository extends CrudRepository<Constanta, Short> {
    Constanta getConstantaById(short id);
    List<Constanta> getConstantaByValoareText(String valoareText);
    List<Constanta> getConstantaByValoareNumericaIntreaga(Long valoare);
    List<Constanta> getConstantaByValoareNumerica(Double valoare);
    void deleteById(short id);
    List<Constanta> getConstantaByDenumire(String directorImportWinMentor);
}
