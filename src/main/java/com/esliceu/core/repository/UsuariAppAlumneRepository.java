package com.esliceu.core.repository;

import com.esliceu.core.entity.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UsuariAppAlumneRepository extends CrudRepository<UsuariAppAlumne, UsuariAppAlumneID> {
    List<UsuariAppAlumne> findByAlumne_Codi(String codiAlumne);
}
