package com.esliceu.core.repository;

import com.esliceu.core.entity.Avaluacio;
import org.springframework.data.repository.CrudRepository;

public interface AvaluacioRepository extends CrudRepository<Avaluacio, Long> {

    Avaluacio findById(long codi);
}
