package com.esliceu.core.manager;

import com.esliceu.core.entity.Activitat;
import com.esliceu.core.repository.ActivitatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivitatManager {

    @Autowired
    private ActivitatRepository activitatRepository;

    public void createOrUpdate(Activitat activitat) {
        activitatRepository.save(activitat);
    }
}
