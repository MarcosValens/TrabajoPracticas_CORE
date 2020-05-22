package com.esliceu.core.manager;

import com.esliceu.core.entity.UsuariAppProfessor;
import com.esliceu.core.entity.UsuariAppProfessorID;
import com.esliceu.core.repository.UsuariAppProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuariAppProfessorManager {
    @Autowired
    private UsuariAppProfessorRepository usuariAppProfessorRepository;

    public void createOrUpdate(UsuariAppProfessor usuariAppProfessor){
        usuariAppProfessorRepository.save(usuariAppProfessor);
    }

    public UsuariAppProfessor findById(UsuariAppProfessorID usuariAppProfessorID){
        return usuariAppProfessorRepository.findById(usuariAppProfessorID).orElse(null);
    }
}
