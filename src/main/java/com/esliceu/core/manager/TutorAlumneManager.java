package com.esliceu.core.manager;

import com.esliceu.core.entity.TutorAlumne;
import com.esliceu.core.entity.TutorAlumneID;
import com.esliceu.core.repository.TutorAlumneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorAlumneManager {
    @Autowired
    private TutorAlumneRepository tutorAlumneRepository;

    public void createOrUpdate(TutorAlumne tutorAlumne){
        tutorAlumneRepository.save(tutorAlumne);
    }

    public TutorAlumne findById(TutorAlumneID tutorAlumneID){
        return tutorAlumneRepository.findById(tutorAlumneID).orElse(null);
    }
}
