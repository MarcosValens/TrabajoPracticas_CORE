package com.esliceu.core.manager;

import com.esliceu.core.entity.Horarig;
import com.esliceu.core.repository.HorarigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorarigManager {

    @Autowired
    private HorarigRepository horarigRepository;

    public void create(Horarig horarig) {
        horarigRepository.save(horarig);
    }
}
