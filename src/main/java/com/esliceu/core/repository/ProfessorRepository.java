package com.esliceu.core.repository;

import com.esliceu.core.entity.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor, String> {

}
