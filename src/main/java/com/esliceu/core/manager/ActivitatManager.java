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

    public Activitat findById(Long id) {
        return activitatRepository.findById(id).orElse(null);
    }

    public void deleteAll() {
        activitatRepository.deleteAll();
    }
}
