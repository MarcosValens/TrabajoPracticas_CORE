package com.esliceu.core.controller;

import com.esliceu.core.entity.Curs;
import com.esliceu.core.entity.Grup;
import com.esliceu.core.entity.Professor;
import com.esliceu.core.entity.UsuariApp;
import com.esliceu.core.manager.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/admin/professor/email")
    public ResponseEntity<String> setEmailProfesor(@RequestBody String json) {
        JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
        String email = convertedObject.get("email").getAsString();
        String codi = convertedObject.get("codi").getAsString();
        Professor professor = professorManager.findById(codi);
        if (professor == null) {
            return new ResponseEntity<>("No existeix cap professor amb aquest codi", HttpStatus.BAD_REQUEST);
        }

        UsuariApp usuariApp = new UsuariApp();
        usuariApp.setEmail(email);
        usuariApp.setProfessor(professor);
        usuariApp.setIsProfessor(true);
        professor.setUsuariApp(usuariApp);
        professorManager.createOrUpdate(professor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin/professor/email")
    public ResponseEntity<String> setEmailProfessor(@RequestBody String json) {
        JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
        String email = convertedObject.get("email").getAsString();
        UsuariApp usuariApp = usuariAppManager.findByEmail(email);
        if (usuariApp == null) {
            return new ResponseEntity<>("No existeix cap usuari amb aquest correu", HttpStatus.BAD_REQUEST);
        }
        usuariApp.setEmail("");
        usuariApp.setIsProfessor(true);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
