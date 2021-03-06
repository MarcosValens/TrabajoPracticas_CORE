package com.esliceu.core.manager;

import com.esliceu.core.entity.Departament;
import com.esliceu.core.repository.DepartamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartamentManager {

    @Autowired
    private DepartamentRepository departamentRepository;

    public void createOrUpdate(Departament departament) {
        departamentRepository.save(departament);
    }

    public Departament findById(long codi) {
        return departamentRepository.findById(codi).orElse(null);
    }

    public void deleteAll() {
        departamentRepository.deleteAll();
    }
}