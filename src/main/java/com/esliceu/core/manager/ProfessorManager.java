package com.esliceu.core.manager;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.entity.Professor;
import com.esliceu.core.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfessorManager {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private EntityManager em;

    public void createOrUpdate(Professor professor) {
        professorRepository.save(professor);
    }

    public Professor findById(String codi) {
        return professorRepository.findById(codi).orElse(null);
    }

    public List<Professor> findAll() {
        List<Professor> toReturn = new LinkedList<>();
        for (Professor profe : professorRepository.findAll()) {
            toReturn.add(profe);
        }
        return toReturn;
    }

    public List<Professor> findAllLowCharge() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Professor> cq = cb.createQuery(Professor.class);
        Root<Professor> professor = cq.from(Professor.class);
        cq.select(cb.construct(Professor.class, professor.get("codi"), professor.get("nom"), professor.get("ap1"), professor.get("ap2")));
        TypedQuery<Professor> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<Professor> findEliminat() {
        return professorRepository.findAllByEliminatIsTrue();
    }

    public void deleteById(String id){
        professorRepository.deleteById(id);
    }

    public List<Professor> findEliminarONou(){
        return professorRepository.findAllByEliminatIsTrueOrIsNewIsTrue();
    }
}
