package com.esliceu.core.controller;

import com.esliceu.core.entity.*;
import com.esliceu.core.manager.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
public class UsuariosController {
    @Autowired
    TokenManager tokenManager;

    @Autowired
    UsuariAppManager usuariAppManager;

    @Autowired
    ProfessorManager professorManager;

    @Autowired
    AlumneManager alumneManager;

    @Autowired
    UsuariAppAlumneManager usuariAppAlumneManager;

    @Autowired
    UsuariAppProfessorManager usuariAppProfessorManager;
    
    @PostMapping("/private/usuarios/comedor/listado")
    public ResponseEntity<String> marcarListadoComedor(@RequestBody String json, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        UsuariApp personaMarcadora = usuariAppManager.findByEmail(tokenManager.getBody(token).get("sub").toString());

        JsonArray comensales = new JsonParser().parse(json).getAsJsonArray();
        for (JsonElement comensal:comensales) {
            JsonObject object = comensal.getAsJsonObject();
            String codi = object.get("codi").toString().replace("\"", "");
            Professor professor = professorManager.findById(codi);
            Alumne alumne = alumneManager.findById(codi);
            if (professor != null && personaMarcadora.isCuiner()) {
                if(usuariAppProfessorManager.findById(new UsuariAppProfessorID(codi, LocalDate.now()))==null){
                    UsuariAppProfessor usuariAppProfessor = new UsuariAppProfessor();
                    usuariAppProfessor.setData(LocalDate.now());
                    usuariAppProfessor.setProfessor(professor);
                    usuariAppProfessor.setUsuariApp(personaMarcadora);
                    usuariAppProfessorManager.createOrUpdate(usuariAppProfessor);
                    System.out.println("NO estaba marcado");
                }
                else{
                    System.out.println("estaba marcado");
                }
            } else if (alumne != null && (personaMarcadora.isCuiner()||personaMarcadora.isMonitor()) ) {
                if(usuariAppAlumneManager.findById(new UsuariAppAlumneID(codi, LocalDate.now()))==null){
                    UsuariAppAlumne usuariAppAlumne = new UsuariAppAlumne();
                    usuariAppAlumne.setData(LocalDate.now());
                    usuariAppAlumne.setAlumne(alumne);
                    usuariAppAlumne.setUsuariApp(personaMarcadora);
                    usuariAppAlumneManager.createOrUpdate(usuariAppAlumne);
                    System.out.println("NO estaba marcado");
                }
                else {
                    System.out.println("estaba marcado");
                }
            }
        }
        return new ResponseEntity<>("Usuarios marcados", HttpStatus.OK); // ESTO ES UN PLACEHOLDER
    }


    /*
     * TODO: Necesitamos que este endpoint nos retorne una lista de todos los profesores y
     *  alumnos para nostros mostrar en el frontend y poder marcarlos en el comedor.
     *
     *  Para ello, basicamente necesitamos retornar todos los USUARIOS de la bbdd que tengan el rol de `Alumne` o `Professor`.
     * */
    @GetMapping("/private/usuarios/comedor/listado")
    public List<Professor> getAllProfesoresAndEstudiantes() { // Esto de List<Profesor> puede cambiar, esto es un PLACEHOLDER
        return null;
    }


    @GetMapping("/admin/usuaris")
    public List<UsuariApp> getAllUsuaris() {
        return this.usuariAppManager.findAll();
    }


    /*
     * Este endpoint solo retorna el numero de usuarios asi el cliente
     * no tiene por que recibir todos los usuarios cuando solo necesita saber el numero
     * */
    @GetMapping("/admin/usuaris/counter")
    public Integer getNumberOfUsuaris() {
        return this.usuariAppManager.findAll().size();
    }

    @GetMapping("/roles")
    public List<String> getRols() {
        UsuariApp usuariApp = new UsuariApp();
        return usuariApp.getRols();
    }
}
