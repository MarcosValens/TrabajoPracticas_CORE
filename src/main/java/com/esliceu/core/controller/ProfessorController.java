package com.esliceu.core.controller;

import com.esliceu.core.entity.Curs;
import com.esliceu.core.entity.Grup;
import com.esliceu.core.entity.Professor;
import com.esliceu.core.manager.CursManager;
import com.esliceu.core.manager.GrupManager;
import com.esliceu.core.manager.ProfessorManager;
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
        System.out.println(professorManager.findAll());
        return professorManager.findAll();
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String professorJson) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
