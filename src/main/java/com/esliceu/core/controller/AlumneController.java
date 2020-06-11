package com.esliceu.core.controller;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.entity.UsuariAppAlumne;
import com.esliceu.core.manager.AlumneManager;
import com.esliceu.core.manager.GrupManager;
import com.esliceu.core.manager.UsuariAppAlumneManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
public class AlumneController {

    @Autowired
    AlumneManager alumneManager;

    @Autowired
    GrupManager grupManager;

    @Autowired
    UsuariAppAlumneManager usuariAppAlumneManager;

    @Autowired
    EntityManager em;

    @Autowired
    Gson gson;

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


    /*
     * TODO dado un codigo de un alumno, queremos recibir todos sus marcages en
     *  el comedor con toda la info. Fecha y quien ha sido el monitor/cuiner que ha marcado a dicho alumno
     * */
    @GetMapping("/private/alumno/{codi}/comedor/marcaje")
    public ResponseEntity<List<UsuariAppAlumne>> getMarcajesSpecificAlumno(@PathVariable String codi) {
        List<UsuariAppAlumne> marcatgeAlumne = usuariAppAlumneManager.findByAlumne_Codi(codi);
        for (int i = 0; i < marcatgeAlumne.size(); i++) {
            marcatgeAlumne.get(i).setAlumne(null);
        }
        return new ResponseEntity<>(marcatgeAlumne, HttpStatus.OK);
    }

    @GetMapping("/private/alumne/comedor/listado")
    public List<Alumne> getAllAlumnesForListado() {
        List<Alumne> alumnes = alumneManager.findAll();
        for (Alumne alumne : alumnes) {
            alumne.setTutorsAlumnes(null);
            alumne.setExpedient(null);
        }
        System.out.println(alumnes);
        return alumnes;
    }

    //La fecha de finalización no está incluída. Si se busca entre las fechas 2020-06-02 y 2020-06-03 los del día 03 no van a salir
    @GetMapping("/private/getAlumnesFrom/{dataInici}/from/{dataFi}")
    public ResponseEntity<List<UsuariAppAlumne>> getAlumnesByData(@PathVariable("dataInici")String dataInici, @PathVariable("dataFi") String dataFi){
        LocalDate inici = LocalDate.parse(dataInici);
        LocalDate fi = LocalDate.parse(dataFi);
        return new ResponseEntity<>(usuariAppAlumneManager.findByDates(inici, fi), HttpStatus.OK);
    }

    @GetMapping("/admin/getAllAlumnesEliminatsONous")
    public ResponseEntity<List<Alumne>> getAlumnesByNouOEliminat(){
        return new ResponseEntity<>(alumneManager.findNousIEliminats(), HttpStatus.OK);
    }

    @PostMapping("/admin/updateAlumneLdapCredentials")
    public ResponseEntity<String> updateAlumne(@RequestBody String jsonString){
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        String codi = jsonObject.get("codi").getAsString();
        Alumne alumne = alumneManager.findById(codi);
        String loginLdap =  jsonObject.get("loginLdap").getAsString();
        String passwordLdap = jsonObject.get("passwordLdap").getAsString();
        Long uidNumberLdap = jsonObject.get("uidNumber").getAsLong();
        alumne.setLoginLDAP(loginLdap);
        alumne.setPasswordLDAP(passwordLdap);
        alumne.setUidNumberLDAP(uidNumberLdap);
        alumneManager.createOrUpdate(alumne);
        return new ResponseEntity<>("Ha anat bé", HttpStatus.OK);
    }

}
