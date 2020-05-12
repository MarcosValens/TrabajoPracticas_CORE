package com.esliceu.core.controller;

import com.esliceu.core.entity.Alumne;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;


@RestController
public class AlumneController {

    @GetMapping("/getTodosAlumnosCurso")
    public ResponseEntity<List<Alumne>> getTodosAlumnosCurso() {
        List<Alumne> alumnes = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            Alumne alumne = new Alumne();
            alumne.setNom("Alumne " + i);
            alumne.setAp1("Cognom Alumne " + i);
            alumne.setAp2("Cognom Alumne " + i);
            alumne.setCodi("0000" + i);
            alumne.setExpedient(Long.parseLong("0000" + i));
            alumnes.add(alumne);
        }
        return new ResponseEntity<>(alumnes, HttpStatus.OK);
    }

    @GetMapping("/getTodosAlumnosGrupo")
    public ResponseEntity<String> getTodosAlumnosGrupo(@RequestBody String grupo) {
        return new ResponseEntity<>("Alumnos", HttpStatus.OK);
    }

    @GetMapping("/getDatosAlumno")
    public ResponseEntity<Alumne> getDatosAlumno() {
        Alumne alumne = new Alumne();
        alumne.setCodi("1");
        alumne.setNom("Christian");
        alumne.setAp1("Martinez");
        alumne.setAp2("Piza");
        return new ResponseEntity<>(alumne, HttpStatus.OK);
    }

    @PutMapping("/subirFoto")
    public ResponseEntity<String> subirFoto() {
        return new ResponseEntity<>("Foto subida", HttpStatus.OK);
    }

    @PostMapping("/subirFoto")
    public ResponseEntity<String> subirFotoNueva() {
        return new ResponseEntity<>("Foto subida", HttpStatus.OK);
    }

    @DeleteMapping("/eliminarFoto")
    public ResponseEntity<String> eliminarFoto() {
        return new ResponseEntity<>("Foto eliminada", HttpStatus.OK);
    }
}
