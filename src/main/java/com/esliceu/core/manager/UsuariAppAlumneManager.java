package com.esliceu.core.manager;

import com.esliceu.core.entity.*;
import com.esliceu.core.repository.TutorAlumneRepository;
import com.esliceu.core.repository.UsuariAppAlumneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariAppAlumneManager {
    @Autowired
    private UsuariAppAlumneRepository usuariAppAlumneRepository;

    public void createOrUpdate(UsuariAppAlumne usuariAppAlumne){
        usuariAppAlumneRepository.save(usuariAppAlumne);
    }

    public UsuariAppAlumne findById(UsuariAppAlumneID usuariAppAlumneID){
        return usuariAppAlumneRepository.findById(usuariAppAlumneID).orElse(null);
    }

    public List<UsuariAppAlumne> findByAlumne_Codi(String codiAlumne){
        return usuariAppAlumneRepository.findByAlumne_Codi(codiAlumne);
    }
}
