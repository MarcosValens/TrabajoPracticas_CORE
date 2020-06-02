package com.esliceu.core.repository;

import com.esliceu.core.entity.*;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;


public interface UsuariAppAlumneRepository extends CrudRepository<UsuariAppAlumne, UsuariAppAlumneID> {
    List<UsuariAppAlumne> findByAlumne_Codi(String codiAlumne);

    List<UsuariAppAlumne> findByData(LocalDate dia);

    List<UsuariAppAlumne> findByDataBetween(LocalDate dataInici, LocalDate dataFi);
}
