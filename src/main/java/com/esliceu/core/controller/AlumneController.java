package com.esliceu.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlumneController {

    @GetMapping("/getTodosAlumnosCurso")
    public ResponseEntity<String> getTodosAlumnosCurso(@RequestBody String curso) {
        return new ResponseEntity<>("Alumnos", HttpStatus.OK);
    }

    @GetMapping("/getDatosAlumno")
    public ResponseEntity<String> getDatosAlumno() {
        return new ResponseEntity<>("Datos Alumno", HttpStatus.OK);
    }

    @PutMapping("/subirFoto")
    public ResponseEntity<String> subirFoto() {
        return new ResponseEntity<>("Foto subida", HttpStatus.OK);
    }

    @GetMapping("/eliminarFoto")
    public ResponseEntity<String> eliminarFoto() {
        return new ResponseEntity<>("Foto eliminada", HttpStatus.OK);
    }
}
