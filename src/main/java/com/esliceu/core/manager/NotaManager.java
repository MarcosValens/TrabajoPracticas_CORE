package com.esliceu.core.manager;

import com.esliceu.core.entity.Nota;
import com.esliceu.core.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotaManager {

    @Autowired
    private NotaRepository notaRepository;

    public void createOrUpdate(Nota nota) {
        notaRepository.save(nota);
    }

    public void deleteAll() {
        notaRepository.deleteAll();
    }
}
