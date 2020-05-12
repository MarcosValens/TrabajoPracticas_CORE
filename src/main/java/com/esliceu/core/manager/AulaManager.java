package com.esliceu.core.manager;

import com.esliceu.core.entity.Aula;
import com.esliceu.core.repository.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AulaManager {

    @Autowired
    private AulaRepository aulaRepository;

    public void createOrUpdate(Aula aula) {
        aulaRepository.save(aula);
    }

    public Aula findById(Long id){
        return aulaRepository.findById(id).orElse(null);
    }
}
