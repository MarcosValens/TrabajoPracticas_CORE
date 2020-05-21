package com.esliceu.core.controller;

import com.esliceu.core.entity.Curs;
import com.esliceu.core.entity.Grup;
import com.esliceu.core.entity.Professor;
import com.esliceu.core.entity.UsuariApp;
import com.esliceu.core.manager.*;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

@RestController
public class ProfessorController {

    @Autowired
    CursManager cursManager;

    @Autowired
    GrupManager grupManager;

    @Autowired
    ProfessorManager professorManager;

    @Autowired
    TokenManager tokenManager;

    @Autowired
    UsuariAppManager usuariAppManager;

    @GetMapping("/private/cursos")
    public ResponseEntity<List<Curs>> getCursos() {
        List<Curs> cursos = cursManager.findAll();
        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }

    @GetMapping("/private/grupos")
    public ResponseEntity<List<Grup>> getGrupos() {
        List<Grup> grupos = grupManager.findAll();
        return new ResponseEntity<>(grupos, HttpStatus.OK);
    }

    @GetMapping("/private/professor")
    public List<Professor> getAllProfessor() {
        return professorManager.findAll();
    }

    @GetMapping("/private/professor/{codigo}")
    public Professor getProfessor(@PathVariable String codigo) {
        return professorManager.findById(codigo);
    }

    /*
     * TODO: Imagino que este endpoint habra que usarlo con un rol mayor que el token normal,
     *  falta añadir dicho ROL, crear un TOKEN  NUEVO  y definir que afectará en los endpoints /admin/** por ejemplo
     *
     * El json recibe un email, y un codigo. El email es el que se le ha de asignar,
     * el codi es el usuario que ha de tener ese email
     * */
    @PutMapping("/admin/professor/email")
    public ResponseEntity<String> setEmailProfesor(@RequestBody String json) {
        JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
        String email = convertedObject.get("email").getAsString();
        String codi = convertedObject.get("codi").getAsString();
        Professor professor = professorManager.findById(codi);
        UsuariApp usuariApp = new UsuariApp();
        usuariApp.setEmail(email);
        usuariApp.setProfessor(professor);
        usuariAppManager.create(usuariApp);
        System.out.println(professor.getCodi());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /*
     * TODO: Falta establecer el Login de manera local, con password y email o usuario, eso falta decidirlo
     *
     * El json recibirá un email o username, lo que decidais y una password,
     * la cual tendreis que validar correctamente y retornar access_tokens
     * */
    @PostMapping("/auth/login")
    public Map<String, String> login(@RequestBody String json, HttpServletResponse response) {
        System.out.println("Entra al controller");

        /*
         * PLACEHOLDER PARA PODER TRABAJAR EN FRONT
         * */
        boolean validaLogin = true; /* TODO: esto deberia venir de un manager que valide el login*/
        if (!validaLogin) {

            /*
             * TODO BORRAR ESTE COMENTARIO UNA VEZ ESTE ACABADO ESTE ENDPOINT
             * ESTO FUNCIONA
             * */
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        /*
         * TODO BORRAR ESTE COMENTARIO UNA VEZ ESTE ACABADO ESTE ENDPOINT
         * ESTO FUNCIONA
         * */
        response.setStatus(HttpServletResponse.SC_OK);

        /*
         * PLACEHOLDER
         * */
        UsuariApp user = new UsuariApp();// TODO: Cambiar este usuariapp por que venga de la bbdd
        user.setEmail("prueba@prueba.pru");


        /*
         * TODO BORRAR ESTE COMENTARIO UNA VEZ ESTE ACABADO ESTE ENDPOINT
         * ESTO FUNCIONA
         * */
        Map<String, String> map = new HashMap<>();
        map.put("access_token", tokenManager.generateAcessToken(user));
        map.put("refresh_token", tokenManager.generateRefreshToken(user));
        return map;

    }
}
