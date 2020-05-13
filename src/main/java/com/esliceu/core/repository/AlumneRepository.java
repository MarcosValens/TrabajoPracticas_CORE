package com.esliceu.core.repository;

import com.esliceu.core.entity.Alumne;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlumneRepository extends CrudRepository<Alumne, String> {
    List<Alumne> findByGrup(String codiGrup);
}
