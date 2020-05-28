package com.esliceu.core.controller;


import com.esliceu.core.entity.Alumne;
import com.esliceu.core.entity.Professor;
import com.esliceu.core.entity.UsuariAppAlumne;
import com.esliceu.core.entity.UsuariAppProfessor;
import com.esliceu.core.manager.UsuariAppAlumneManager;
import com.esliceu.core.manager.UsuariAppProfessorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class MenjadorController {


    @Autowired
    private UsuariAppAlumneManager usuariAppAlumneManager;

    @Autowired
    private UsuariAppProfessorManager usuariAppProfessorManager;


    @GetMapping("/private/comedor/comun/ayer")
    public Map<String, Object> getCommonSelectionYesterday() {
        List<Alumne> alumnes = new LinkedList<>();
        List<Professor> professors = new LinkedList<>();

        /*
         * Sacamos los alumnos
         * Esto se podria comprobar que si estamos a dia LUNES, no extraer 1 sino 3, para asi sacar los del viernes
         * Pero ya que el endpoint dice AYER, lo hacemos a 1 dia menos.
         * */
        List<UsuariAppAlumne> alumnosComedor = this.usuariAppAlumneManager.findByDia(LocalDate.now().minusDays(1));

        for (UsuariAppAlumne usuari : alumnosComedor) {
            alumnes.add(usuari.getAlumne());
        }

        /*
         * Sacamos los profesores
         * */
        List<UsuariAppProfessor> professorsComedor = this.usuariAppProfessorManager.findByDia(LocalDate.now().minusDays(1));

        for (UsuariAppProfessor usuari : professorsComedor) {
            professors.add(usuari.getProfessor());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("alumnes", alumnes);
        map.put("professors", professors);

        return map;
    }

    @GetMapping("/private/comedor/comun/ultima/semana")
    public Map<String, Object> getCommonSelectionLastWeek() {
        List<Alumne> alumnes = new LinkedList<>();
        List<Professor> professors = new LinkedList<>();

        List<UsuariAppAlumne> alumnosComedor = this.usuariAppAlumneManager.findByDia(LocalDate.now().minusDays(7));

        for (UsuariAppAlumne usuari : alumnosComedor) {
            alumnes.add(usuari.getAlumne());
        }

        List<UsuariAppProfessor> professorsComedor = this.usuariAppProfessorManager.findByDia(LocalDate.now().minusDays(7));

        for (UsuariAppProfessor usuari : professorsComedor) {
            professors.add(usuari.getProfessor());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("alumnes", alumnes);
        map.put("professors", professors);

        return map;
    }

}
