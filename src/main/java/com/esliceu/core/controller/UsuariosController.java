package com.esliceu.core.controller;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.entity.Professor;
import com.esliceu.core.entity.UsuariApp;
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


    /*
     * TODO: necesitamos un endpoint el cual, recibe un JSON con un listado de usuarios, este
     *  ha de marcar a todos los usuarios que recibe como que han venido a comer.
     *  Necesitamos tambien asegurarnos de quien es el que está haciendo la accion
     *  si un cocinero o un monitor.
     *  Si es un monitor, de todos los usuarios que recibimos, solo podrá marcar los que son alumnos
     *  Si es un cocinero, podrá marcar a todos
     * */
    @PostMapping("/private/usuarios/comedor/listado")
    public ResponseEntity<String> marcarListadoComedor(@RequestBody String json, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        UsuariApp personaMarcadora = usuariAppManager.findByEmail(tokenManager.getBody(token).get("email").toString());
        // TODO: Sacamos el usuario (profe, monitor o cuiner del token)


        // TODO A parte de marcar el usuario, tenemos que marcar que cocinero
        //  o monitor lo ha hecho, es lo podemos sacar del usuario "personaMarcadora"
        //  ya que viene del token del login.
        //  A parte del cocinero o monitor, tambien necesitamos el dia que se marca.
        //  una de dos, puede venir del cliente, o de un Date().now() (algo asi).

        // TODO: Esto es un placeholder de como podria ser

        JsonArray comensales = new JsonParser().parse(json).getAsJsonArray();
        for (JsonElement comensal:comensales) {
            JsonObject object = comensal.getAsJsonObject();
            Professor professor = professorManager.findById(object.get("codi").toString());
            Alumne alumne = alumneManager.findById(object.get("codi").toString());
            if (professor != null) {//&&personaMarcadora.rol.equals("cuiner")
                //marcar professor si no estaba marcado para hoy

            } else if (alumne != null) {
                //marcar alumno si no estaba marcado para hoy
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

    /*
     * TODO: Este endpoint tiene que devolver todos los usuarios que se hayan registrado en nuestra aplicacion.
     *  No queremos ninguna entidad profesor o alumno, queremos la tabla usuariapp.
     * */
    @GetMapping("/admin/usuaris")
    public List<UsuariApp> getAllUsuaris() {
        return null;
    }


    /*
     * Este endpoint solo retorna el numero de usuarios asi el cliente
     * no tiene por que recibir todos los usuarios cuando solo necesita saber el numero
     * */
    @GetMapping("/admin/usuaris/counter")
    public Integer getNumberOfUsuaris() {
        return this.usuariAppManager.findAll().size();
    }

}
