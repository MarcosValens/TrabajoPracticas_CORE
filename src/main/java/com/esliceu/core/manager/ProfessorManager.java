package com.esliceu.core.manager;

import com.esliceu.core.entity.Professor;
import com.esliceu.core.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorManager {

    @Autowired
    private ProfessorRepository professorRepository;

    public void create(Professor professor) {
        professorRepository.save(professor);
    }
}
