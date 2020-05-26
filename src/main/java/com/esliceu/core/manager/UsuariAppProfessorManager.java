package com.esliceu.core.manager;

import com.esliceu.core.entity.Professor;
import com.esliceu.core.entity.UsuariAppProfessor;
import com.esliceu.core.entity.UsuariAppProfessorID;
import com.esliceu.core.repository.UsuariAppProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuariAppProfessorManager {
    @Autowired
    private UsuariAppProfessorRepository usuariAppProfessorRepository;

    public void createOrUpdate(UsuariAppProfessor usuariAppProfessor){
        usuariAppProfessorRepository.save(usuariAppProfessor);
    }
    public List<UsuariAppProfessor> findAllByProfessor(Professor professor){
        return usuariAppProfessorRepository.findAllByProfessor(professor);
    }

    public UsuariAppProfessor findById(UsuariAppProfessorID usuariAppProfessorID){
        return usuariAppProfessorRepository.findById(usuariAppProfessorID).orElse(null);
    }

    public List<UsuariAppProfessor> findByDia(LocalDate dia) {
        return usuariAppProfessorRepository.findByData(dia);
    }
}
