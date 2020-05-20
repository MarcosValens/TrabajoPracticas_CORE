package com.esliceu.core.controller;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.manager.AlumneManager;
import com.esliceu.core.manager.GrupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
public class AlumneController {

    @Autowired
    AlumneManager alumneManager;

    @Autowired
    GrupManager grupManager;

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
}
