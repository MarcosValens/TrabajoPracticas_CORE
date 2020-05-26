package com.esliceu.core.controller;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.entity.UsuariAppAlumne;
import com.esliceu.core.manager.AlumneManager;
import com.esliceu.core.manager.GrupManager;
import com.esliceu.core.manager.UsuariAppAlumneManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


@RestController
public class AlumneController {

    @Autowired
    AlumneManager alumneManager;

    @Autowired
    GrupManager grupManager;

    @Autowired
    UsuariAppAlumneManager usuariAppAlumneManager;

    @Autowired
    EntityManager em;


    @GetMapping("/private/alumnos")
    public List<Alumne> getAllAlumnos() {
        return alumneManager.findAll();
    }

    @GetMapping("/private/alumnos/grupo/{codi}")
    public ResponseEntity<List<Alumne>> getTodosAlumnosGrupo(@PathVariable("codi") Long grup) {
        List<Alumne> alumnos = alumneManager.findByGrup(grupManager.findById(grup));
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }

    @GetMapping("/private/alumno/{codi}")
    public ResponseEntity<Alumne> getAlumne(@PathVariable("codi") String codi) {
        Alumne alumno = alumneManager.findById(codi);
        return new ResponseEntity<>(alumno, HttpStatus.OK);
    }

    @DeleteMapping("/private/alumno/{codi}")
    public ResponseEntity<String> deleteAlumne(@PathVariable("codi") String codi) {
        alumneManager.delete(codi);
        return new ResponseEntity<String>("Alumno eliminado", HttpStatus.OK);
    }

    @PostMapping("/private/foto")
    public ResponseEntity<String> subirFoto(@RequestPart(value = "file") final MultipartFile uploadfile) {
        return new ResponseEntity<>("Foto subida", HttpStatus.OK);
    }

    @PutMapping("/private/foto")
    public ResponseEntity<String> subirFotoNueva(@RequestPart(value = "file") final MultipartFile uploadfile) {
        return new ResponseEntity<>("Foto subida", HttpStatus.OK);
    }

    @DeleteMapping("/private/foto")
    public ResponseEntity<String> eliminarFoto() {
        return new ResponseEntity<>("Foto eliminada", HttpStatus.OK);
    }


    /*
     * TODO dado un codigo de un alumno, queremos recibir todos sus marcages en
     *  el comedor con toda la info. Fecha y quien ha sido el monitor/cuiner que ha marcado a dicho alumno
     * */
    @GetMapping("/private/alumno/{codi}/comedor/marcaje")
    public ResponseEntity<List<UsuariAppAlumne>> getMarcajesSpecificAlumno(@PathVariable String codi) {
        List<UsuariAppAlumne> marcatgeAlumne = usuariAppAlumneManager.findByAlumne_Codi(codi);
        for (int i = 0; i < marcatgeAlumne.size(); i++) {
            marcatgeAlumne.get(i).setAlumne(null);
        }
        return new ResponseEntity<>(marcatgeAlumne, HttpStatus.OK);
    }

    @GetMapping("/listado")
    public List<Alumne> getAllAlumnesForListado() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Alumne> cq = cb.createQuery(Alumne.class);
        Root<Alumne> alumne = cq.from(Alumne.class);
        cq.select(cb.construct(Alumne.class, alumne.get("codi"), alumne.get("nom"), alumne.get("ap1"), alumne.get("ap2")));
        TypedQuery<Alumne> query = em.createQuery(cq);
        return query.getResultList();
    }
}
