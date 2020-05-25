package com.esliceu.core.repository;

import com.esliceu.core.entity.Professor;
import com.esliceu.core.entity.UsuariAppProfessor;
import com.esliceu.core.entity.UsuariAppProfessorID;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UsuariAppProfessorRepository extends CrudRepository<UsuariAppProfessor, UsuariAppProfessorID> {
    List<UsuariAppProfessor> findAllByProfessor(Professor professor);
}
