package com.esliceu.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfessorController {

    @GetMapping("/cursos")
    public ResponseEntity<String> getCursos() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/grupos")
    public ResponseEntity<String> getGrupos() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/professor")
    public ResponseEntity<String> getProfessor(String professor) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String professorJson){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
