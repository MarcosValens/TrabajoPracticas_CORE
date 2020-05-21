package com.esliceu.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UsuariosController {


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
        // Usuario personaMarcadora = tokenManager.getUsuarioFromToken(token); TODO: Sacamos el usuario (profe, monitor o cuiner del token)


        // TODO A parte de marcar el usuario, tenemos que marcar que cocinero
        //  o monitor lo ha hecho, es lo podemos sacar del usuario "personaMarcadora"
        //  ya que viene del token del loguin

        // TODO: Esto es un placeholder de como podria ser

        // List<Usuario> usuariosDelJSON = usuariomanager.getFromJSON(json)
//        for (Usuarios userAmarcar : usuariosDelJSON){
//            if (userAmarcar Es un profesor && personaMarcadora ES un CUINER){
        //ANTES DE MARCAR MIRAR QUE HOY EL MISMO DIA NO HAYA SIDO MARCADO
//                // MARCAMOS ESE USUARIO
//            }else{
        //ANTES DE MARCAR MIRAR QUE HOY EL MISMO DIA NO HAYA SIDO MARCADO
//                // MARCAMOS AL USUARIO SEAMOS QUIEN SEAMOS YA QUE SERA UN ALUMNO
//            }
//        }

        return new ResponseEntity<>("Usuarios marcados", HttpStatus.OK); // ESTO ES UN PLACEHOLDER
    }

}
