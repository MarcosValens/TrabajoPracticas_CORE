package com.esliceu.core.manager;

import com.esliceu.core.entity.Tutor;
import com.esliceu.core.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorManager {

    @Autowired
    private TutorRepository tutorRepository;

    public void createOrUpdate(Tutor tutor) {
        tutorRepository.save(tutor);
    }

    public Tutor findById(String codi) {
        return tutorRepository.findById(codi).orElse(null);
    }

    public void deleteAll() {
        tutorRepository.deleteAll();
    }
}
