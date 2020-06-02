package com.esliceu.core.manager;

import com.esliceu.core.entity.Curs;
import com.esliceu.core.repository.CursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursManager {

    @Autowired
    private CursRepository cursRepository;

    public void createOrUpdate(Curs curs) {
        cursRepository.save(curs);
    }

    public Curs findById(Long id) {
        return cursRepository.findById(id).orElse(null);
    }

    public List<Curs> findAll() {
        return (List<Curs>) cursRepository.findAll();
    }

    public void deleteAll() {
        cursRepository.deleteAll();
    }
}
