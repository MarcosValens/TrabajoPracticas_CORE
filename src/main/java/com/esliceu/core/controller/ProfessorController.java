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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
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
    public ResponseEntity<Professor> getProfessor(String codi) {
        Professor professor = professorManager.findById(codi);
        return new ResponseEntity<>(professor, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String professorJson){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
