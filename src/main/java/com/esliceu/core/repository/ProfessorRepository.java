package com.esliceu.core.repository;

import com.esliceu.core.entity.Professor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfessorRepository extends CrudRepository<Professor, String> {

    List<Professor> findAllByEliminatIsTrue();

}
