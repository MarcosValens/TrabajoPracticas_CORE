package com.esliceu.core.repository;

import com.esliceu.core.entity.Nota;
import org.springframework.data.repository.CrudRepository;

public interface NotaRepository extends CrudRepository<Nota, Long> {

    Nota findById(long codi);
}
