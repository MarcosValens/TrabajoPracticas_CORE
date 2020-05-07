package com.esliceu.core.manager;

import com.esliceu.core.entity.Grup;
import com.esliceu.core.repository.GrupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrupManager {

    @Autowired
    private GrupRepository grupRepository;

    public void create(Grup grup) {
        grupRepository.save(grup);
    }
}
