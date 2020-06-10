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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@EnableTransactionManagement
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
        boolean isCuinerOrMonitor = personaMarcadora.isCuiner() || personaMarcadora.isMonitor();
        if (!isCuinerOrMonitor) {
            return new ResponseEntity<>("Error marcant els alumnes i professors.", HttpStatus.BAD_REQUEST);
        }
        JsonObject data = gson.fromJson(json, JsonObject.class);
        JsonArray users = data.get("users").getAsJsonArray();
        String fecha = data.get("fecha").getAsString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(fecha, formatter);
        usuariAppAlumneManager.deleteAllByData(parsedDate);
        usuariAppProfessorManager.deleteAllByData(parsedDate);

        for (JsonElement comensal : users) {
            JsonObject object = comensal.getAsJsonObject();
            String codi = object.get("codi").toString().replace("\"", "");
            Alumne alumne = alumneManager.findById(codi);
            Professor professor = professorManager.findById(codi);

            if (professor != null && personaMarcadora.isCuiner()) {

                UsuariAppProfessor usuariAppProfessor = new UsuariAppProfessor();
                usuariAppProfessor.setData(parsedDate);
                usuariAppProfessor.setProfessor(professor);
                usuariAppProfessor.setUsuariApp(personaMarcadora);
                usuariAppProfessorManager.createOrUpdate(usuariAppProfessor);
                System.out.println("No estaba marcado");
                continue;
            }
            if (alumne == null){
                continue;
            }
            UsuariAppAlumne usuariAppAlumne = new UsuariAppAlumne();
            usuariAppAlumne.setData(parsedDate);
            usuariAppAlumne.setAlumne(alumne);
            usuariAppAlumne.setUsuariApp(personaMarcadora);
            usuariAppAlumneManager.createOrUpdate(usuariAppAlumne);
            System.out.println("No estaba marcado");
        }
        /*for (JsonElement comensal:users) {
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
        }*/
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
    public ResponseEntity<UsuariApp> getInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        UsuariApp usuariApp = tokenManager.getUsuariFromToken(token);

        return new ResponseEntity<>(usuariApp, HttpStatus.OK);
    }
}
