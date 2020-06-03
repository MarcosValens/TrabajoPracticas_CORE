package com.esliceu.core.manager;

import com.esliceu.core.entity.Avaluacio;
import com.esliceu.core.repository.AvaluacioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class AvaluacioManager {

    @Autowired
    private AvaluacioRepository avaluacioRepository;

    @Autowired
    private GrupManager grupManager;

    @Autowired
    private EntityManager entityManager;

    public void createOrUpdate(Avaluacio avaluacio) {
        avaluacioRepository.save(avaluacio);
    }

    public void deleteAll() {
        /*List<Avaluacio> avaluacions = this.findAll();
        for (Avaluacio avaluacio : avaluacions){
            avaluacio.getGrup().setAvaluacions(null);
            this.entityManager.persist(avaluacio.getGrup());
            avaluacio.setGrup(null);
        }*/
        avaluacioRepository.deleteAll();
    }

    public List<Avaluacio> findAll() {
        return (List<Avaluacio>) avaluacioRepository.findAll();
    }

    public Avaluacio findById(long codi) {
        return avaluacioRepository.findById(codi);
    }
}
