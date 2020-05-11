package com.esliceu.core.manager;

import com.esliceu.core.entity.Curs;
import com.esliceu.core.repository.CursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursManager {

    @Autowired
    private CursRepository cursRepository;

    public void create(Curs curs) {
        cursRepository.save(curs);
    }

    public Curs findById(Long id){
        return cursRepository.findById(id).orElse(null);
    }
}
