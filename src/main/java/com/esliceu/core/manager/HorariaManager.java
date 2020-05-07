package com.esliceu.core.manager;

import com.esliceu.core.entity.Horaria;
import com.esliceu.core.repository.HorariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorariaManager {

    @Autowired
    private HorariaRepository horariaRepository;

    public void create(Horaria horaria) {
        horariaRepository.save(horaria);
    }
}
