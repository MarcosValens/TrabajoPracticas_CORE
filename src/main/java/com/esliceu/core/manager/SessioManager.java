package com.esliceu.core.manager;

import com.esliceu.core.entity.Sessio;
import com.esliceu.core.repository.SessioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessioManager {
    @Autowired
    SessioRepository sessioRepository;

    public void createOrUpdate(Sessio sessio) {
        sessioRepository.save(sessio);
    }

    public void deleteAll() {
        sessioRepository.deleteAll();
    }
}
