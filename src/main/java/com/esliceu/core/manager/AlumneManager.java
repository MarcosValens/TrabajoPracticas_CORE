package com.esliceu.core.manager;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.entity.Grup;
import com.esliceu.core.repository.AlumneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.File;
import java.util.List;

@Service
public class AlumneManager {

    @Autowired
    private AlumneRepository alumneRepository;

    @Autowired
    private EntityManager em;

    @Value("${UPLOAD.DIRECTORY.FOTOS}")
    private String directorioFotos;

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

    public List<Alumne> findEliminat() {
        return alumneRepository.findAllByEliminatIsTrue();
    }

    public String deleteEliminatFotos(){
        List<Alumne> elimimnats = alumneRepository.findAllByEliminatIsTrue();
        int i = 0;
        for (Alumne alumne: elimimnats) {
            try {
                File f= new File(directorioFotos+alumne.getGrup().getCodi()+"/"+alumne.getExpedient()+".png");
                boolean deleted = f.delete();
                if(deleted) i++;
            }
            catch (Exception exception) {
                continue;
            }
        };
        return "Deleted "+i+" fotos";

    public List<Alumne> findNousIEliminats(){
        return alumneRepository.findAllByEliminatIsTrueOrIsNewIsTrue();
    }
}
