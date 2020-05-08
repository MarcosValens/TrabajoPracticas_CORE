package com.esliceu.core.manager;

import com.esliceu.core.entity.Horarip;
import com.esliceu.core.repository.HoraripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoraripManager {

    @Autowired
    private HoraripRepository horaripRepository;

    public void create(Horarip horarip) {
        horaripRepository.save(horarip);
    }
}
