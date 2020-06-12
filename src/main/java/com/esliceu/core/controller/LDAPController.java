
package com.esliceu.core.controller;


import com.esliceu.core.manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NamingException;

@RestController
public class LDAPController {

    @Autowired
    private AlumneLDAPManager alumneLdapManager;

    @Autowired
    private GrupManager grupManager;

    @Autowired
    private GrupLDAPManager grupLDAPManager;

    @Autowired
    private AlumneManager alumneManager;

    @Autowired
    private ProfessorManager professorManager;

    @Autowired
    private ProfessorLDAPManager professorLDAPManager;

    @PostMapping("/ldap/actualitzarAlumnes")
    public ResponseEntity<String> actualitzarLdapAlumnes() throws NamingException {
        alumneLdapManager.actualitzarAlumnesLdap(alumneManager.findAll());
        return new ResponseEntity<>("Canvis realitzats", HttpStatus.OK);
    }

    @PostMapping("/ldap/actualitzarProfessors")
    public ResponseEntity<String> actualitzarLdapProfessors() throws NamingException {
        professorLDAPManager.actualitzarProfessorsLdap(professorManager.findAll());
        return new ResponseEntity<>("Canvis realitzats", HttpStatus.OK);
    }

    @PostMapping("/ldap/insertarGrups")
    public ResponseEntity<String> insertarLdapGrups(){
        grupLDAPManager.addGroup(grupManager.findAll());
        return new ResponseEntity<>("Grups insertats", HttpStatus.OK);
    }

    @PostMapping("/ldap/insertarMembres")
    public ResponseEntity<String> asignarGrups() throws NamingException {
        grupLDAPManager.addMembers(alumneManager.findAll());
        return new ResponseEntity<>("Membres assignats", HttpStatus.OK);
    }
}

