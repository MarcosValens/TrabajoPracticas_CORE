package com.esliceu.core.manager;

import com.esliceu.core.entity.Avaluacio;
import com.esliceu.core.entity.Grup;
import com.esliceu.core.repository.GrupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupManager {

    @Autowired
    private GrupRepository grupRepository;

    public void createOrUpdate(Grup grup) {
        grupRepository.save(grup);
    }

    public Grup findById(Long id) {
        return grupRepository.findById(id).orElse(null);
    }

    public List<Grup> findAll() {
        return (List<Grup>) grupRepository.findAll();
    }

    public void deleteAll() {
        grupRepository.deleteAll();
    }

    public void deleteGrup(Grup grup) {
        grupRepository.delete(grup);
    }

}
