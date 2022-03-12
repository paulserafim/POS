package com.POS.POS.Repository;

import com.POS.POS.Model.Monetar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonetarRepository extends CrudRepository<Monetar, Long> {

}
