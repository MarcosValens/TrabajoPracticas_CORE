package com.esliceu.core.manager;

import com.esliceu.core.entity.Submateria;
import com.esliceu.core.repository.SubmateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmateriaManager {

    @Autowired
    private SubmateriaRepository submateriaRepository;

    public void createOrUpdate(Submateria submateria) {
        submateriaRepository.save(submateria);
    }

    public Submateria findById(Long id) {
        return submateriaRepository.findById(id).orElse(null);
    }

    public void deleteAll() {
        submateriaRepository.deleteAll();
    }
}
