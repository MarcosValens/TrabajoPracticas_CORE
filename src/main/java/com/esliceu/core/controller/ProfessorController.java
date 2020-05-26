package com.esliceu.core.controller;

import com.esliceu.core.entity.*;
import com.esliceu.core.manager.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@RestController
public class ProfessorController {

    @Autowired
    CursManager cursManager;

    @Autowired
    GrupManager grupManager;

    @Autowired
    ProfessorManager professorManager;

    @Autowired
    TokenManager tokenManager;

    @Autowired
    UsuariAppManager usuariAppManager;

    @Autowired
    UsuariAppProfessorManager usuariAppProfessorManager;

    @GetMapping("/private/cursos")
    public ResponseEntity<List<Curs>> getCursos() {
        List<Curs> cursos = cursManager.findAll();
        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }

    @GetMapping("/private/grupos")
    public ResponseEntity<List<Grup>> getGrupos() {
        List<Grup> grupos = grupManager.findAll();
        return new ResponseEntity<>(grupos, HttpStatus.OK);
    }

    @GetMapping("/private/professor")
    public List<Professor> getAllProfessor() {
        return professorManager.findAll();
    }

    /*
     * Este endpoint solamente retorna el numero de profesores que tenemos dentro de nuestra app
     * */
    @GetMapping("/private/professor/counter")
    public Integer getNumberOfProfessor() {
        return professorManager.findAll().size();
    }

    @GetMapping("/private/professor/{codigo}")
    public Professor getProfessor(@PathVariable String codigo) {
        return professorManager.findById(codigo);
    }

    /*
     * TODO: Imagino que este endpoint habra que usarlo con un rol mayor que el token normal,
     *  falta añadir dicho ROL, crear un TOKEN  NUEVO  y definir que afectará en los endpoints /admin/** por ejemplo
     *
     * El json recibe un email, y un codigo. El email es el que se le ha de asignar,
     * el codi es el usuario que ha de tener ese email
     * */
    @PutMapping("/admin/professor/email")
    public ResponseEntity<String> setEmailProfesor(@RequestBody String json) {
        JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
        String email = convertedObject.get("email").getAsString();
        String codi = convertedObject.get("codi").getAsString();
        Professor professor = professorManager.findById(codi);
        if (professor == null) {
            return new ResponseEntity<>("No existeix cap professor/a amb aquest codi", HttpStatus.BAD_REQUEST);
        }
        UsuariApp usuariApp = new UsuariApp();
        usuariApp.setEmail(email);
        usuariApp.setProfessor(professor);
        usuariApp.setIsProfessor(true);
        professor.setUsuariApp(usuariApp);
        professorManager.createOrUpdate(professor);
        return new ResponseEntity<>("El/la professor/a " + professor.getNom()
                + " " + professor.getAp1()
                + " " + professor.getAp2()
                + " te assignat el correu "
                + email, HttpStatus.OK);
    }

    @DeleteMapping("/admin/professor/email")
    public ResponseEntity<String> setEmailProfessor(@RequestBody String json) {
        JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
        String codi = convertedObject.get("codi").getAsString();
        Professor professor = professorManager.findById(codi);
        if (professor.getUsuariApp() == null) {
            return new ResponseEntity<>("Aquest professor/a no te usuari", HttpStatus.BAD_REQUEST);
        } else {

            UsuariApp usuariApp = professor.getUsuariApp();
            professor.setUsuariApp(null);
            professorManager.createOrUpdate(professor);
            usuariApp.setProfessor(null);
            usuariAppManager.delete(usuariApp);
            return new ResponseEntity<>("L'usuari de en/na "
                    + professor.getNom()
                    + " " + professor.getAp1()
                    + " " + professor.getAp2()
                    + " a estat eliminat", HttpStatus.OK);
        }
    }

    /*
     * TODO dado un codi de un profesor, queremos recibir todos sus marcages en
     *  el comedor con toda la info. Fecha y quien ha sido el cuiner que ha marcado a dicho profesor
     * */
    @GetMapping("/private/professor/{codi}/comedor/marcaje")
    public ResponseEntity<List<UsuariAppProfessor>> getMarcajesSpecificProfesor(@PathVariable String codi) {
        List<UsuariAppProfessor> usuariAppProfessor = usuariAppProfessorManager.findAllByProfessor(professorManager.findById(codi));
        return new ResponseEntity<>(usuariAppProfessor, HttpStatus.OK);
    }

    @GetMapping("/private/professor/comedor/listado1")
    public List<Professor> getAllProfesorsListadoComedor() {
        return professorManager.findAllLowCharge();
    }
}
