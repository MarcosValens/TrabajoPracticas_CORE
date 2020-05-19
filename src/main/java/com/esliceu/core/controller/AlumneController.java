package com.esliceu.core.controller;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.manager.AlumneManager;
import com.esliceu.core.manager.GrupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
public class AlumneController {

    @Autowired
    AlumneManager alumneManager;

    @Autowired
    GrupManager grupManager;

    @GetMapping("/getTodosAlumnosCurso")
    public ResponseEntity<List<Alumne>> getTodosAlumnosCurso() {
        List<Alumne> alumnos = alumneManager.findAll();
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }

    @GetMapping("/getTodosAlumnosGrupo")
    public ResponseEntity<List<Alumne>> getTodosAlumnosGrupo(@RequestParam("codi") Long grup) {
        List<Alumne> alumnos = alumneManager.findByGrup(grupManager.findById(grup));
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }

    @GetMapping("/getAlumno")
    public ResponseEntity<Alumne> getAlumne(@RequestParam("codi") String codi) {
        Alumne alumno = alumneManager.findById(codi);
        return new ResponseEntity<>(alumno, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAlumno")
    public ResponseEntity<String> deleteAlumne(String codi) {
        alumneManager.delete(codi);
        return new ResponseEntity<String>("Alumno eliminado", HttpStatus.OK);
    }

    @PostMapping("/foto")
    public ResponseEntity<String> subirFoto(@RequestPart(value = "file") final MultipartFile uploadfile) {
        return new ResponseEntity<>("Foto subida", HttpStatus.OK);
    }

    @PutMapping("/foto")
    public ResponseEntity<String> subirFotoNueva(@RequestPart(value = "file") final MultipartFile uploadfile) {
        return new ResponseEntity<>("Foto subida", HttpStatus.OK);
    }

    @DeleteMapping("/foto")
    public ResponseEntity<String> eliminarFoto() {
        return new ResponseEntity<>("Foto eliminada", HttpStatus.OK);
    }
}
