package com.esliceu.core.controller;

import com.esliceu.core.entity.*;
import com.esliceu.core.manager.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsuariosController {
    @Autowired
    Gson gson;

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
        //JsonArray comensales = new JsonParser().parse(json).getAsJsonArray();
        JsonArray comensales = gson.fromJson(json, JsonArray.class);
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
                } else {
                    System.out.println("estaba marcado");
                }
            }
            else {
                return new ResponseEntity<>("Error marcant els alumnes i professors.", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Usuarios marcados", HttpStatus.OK); // ESTO ES UN PLACEHOLDER
    }


    /*
     * TODO endpoint el cual pasado un dia (YA NOS DIREIS QUE FORMATO QUEREIS),
     *  retornar un listado de usuarios que han sido marcados dicho dia en el comedor
     * */
    @GetMapping("/private/comedor/marcajes/{dia}")
    public ResponseEntity<Map<String, Object>> getAllMarcajesDia(@PathVariable(name = "dia") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dia) {

        List<UsuariAppAlumne> alumnes = usuariAppAlumneManager.findByDia(dia);
        List<UsuariAppProfessor> professors = usuariAppProfessorManager.findByDia(dia);

        Map<String, Object> map = new HashMap<>();
        map.put("alumnes", alumnes);
        map.put("professors", professors);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/private/usuarios/comedor/listado")
    public List<Object> getAllProfesoresAndEstudiantes() {
        List<Object> listAlumne = new java.util.ArrayList<>(Collections.singletonList(alumneManager.findAll()));
        List<Object> listProfessor = Collections.singletonList(professorManager.findAll());
        listAlumne.addAll(listProfessor);
        return listAlumne;
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

    @GetMapping("/admin/auth/roles")
    public List<String> getRols() {
        UsuariApp usuariApp = new UsuariApp();
        return usuariApp.getRols();
    }

    @GetMapping("/private/usuario/me")
    public ResponseEntity<UsuariApp> getInfo(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        UsuariApp usuariApp = tokenManager.getUsuariFromToken(token);

        return new ResponseEntity<>(usuariApp, HttpStatus.OK);
    }
}
