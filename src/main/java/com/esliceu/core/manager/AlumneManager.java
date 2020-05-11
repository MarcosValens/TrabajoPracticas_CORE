package com.esliceu.core.manager;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.repository.AlumneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlumneManager {

    @Autowired
    private AlumneRepository alumneRepository;

    public void createOrUpdate(Alumne alumne) {
        alumneRepository.save(alumne);
    }
}
