package com.esliceu.core.manager;

import com.esliceu.core.entity.Avaluacio;
import com.esliceu.core.repository.AvaluacioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaluacioManager {

    @Autowired
    private AvaluacioRepository avaluacioRepository;

    public void createOrUpdate(Avaluacio avaluacio) {
        avaluacioRepository.save(avaluacio);
    }

    public void deleteAll() {
        avaluacioRepository.deleteAll();
    }
}
