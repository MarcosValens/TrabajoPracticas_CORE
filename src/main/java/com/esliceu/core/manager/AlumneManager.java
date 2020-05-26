package com.esliceu.core.manager;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.entity.Grup;
import com.esliceu.core.repository.AlumneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class AlumneManager {

    @Autowired
    private AlumneRepository alumneRepository;

    @Autowired
    private EntityManager em;

    public void createOrUpdate(Alumne alumne) {
        alumneRepository.save(alumne);
    }

    public void delete(String codi) {
        alumneRepository.deleteById(codi);
    }

    public Alumne findById(String codi) {
        return alumneRepository.findById(codi).orElse(null);
    }

    public List<Alumne> findAll() {
        return (List<Alumne>) alumneRepository.findAll();
    }

    public List<Alumne> findByGrup(Grup grup) {
        return alumneRepository.findByGrup(grup);
    }

}
