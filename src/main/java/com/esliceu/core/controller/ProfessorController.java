package com.esliceu.core.controller;

import com.esliceu.core.entity.Curs;
import com.esliceu.core.entity.Grup;
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


    @GetMapping("/cursos")
    public ResponseEntity<List<Curs>> getCursos() {
        List<Curs> cursos = new LinkedList<>();
        Curs curs1 = new Curs();
        Curs curs2 = new Curs();
        curs1.setCodi((long) 1);
        curs1.setDescripcio("1º ESO");
        curs2.setCodi((long) 2);
        curs2.setDescripcio("2º ESO");
        cursos.add(curs1);
        cursos.add(curs2);
        return new ResponseEntity<>(cursos,HttpStatus.OK);
    }

    @GetMapping("/grupos")
    public ResponseEntity<List<Grup>> getGrupos() {
        List<Grup> grups = new LinkedList<>();
        Curs curs1 = new Curs();
        Curs curs2 = new Curs();
        Grup grup1 = new Grup();
        Grup grup2 = new Grup();
        Grup grup3 = new Grup();
        Grup grup4 = new Grup();
        curs1.setCodi((long) 1);
        curs1.setDescripcio("1º ESO");
        curs2.setCodi((long) 2);
        curs2.setDescripcio("2º ESO");
        grup1.setCodi((long) 1);
        grup1.setCurs(curs1);
        grup1.setNom("A");
        grup2.setCodi((long) 2);
        grup2.setCurs(curs1);
        grup2.setNom("B");
        grup3.setCodi((long) 3);
        grup3.setCurs(curs2);
        grup3.setNom("A");
        grup4.setCodi((long) 4);
        grup4.setCurs(curs2);
        grup4.setNom("B");
        grups.add(grup1);
        grups.add(grup2);
        grups.add(grup3);
        grups.add(grup4);
        System.out.println(grups);
        return new ResponseEntity<>(grups,HttpStatus.OK);
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
