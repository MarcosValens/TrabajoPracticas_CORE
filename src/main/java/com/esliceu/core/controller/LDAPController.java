
package com.esliceu.core.controller;


import com.esliceu.core.entity.Alumne;
import com.esliceu.core.manager.AlumneManager;
import com.esliceu.core.manager.GrupManager;
import com.esliceu.core.manager.LDAPManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NamingException;

@RestController
public class LDAPController {

    @Autowired
    private LDAPManager ldapManager;

    @Autowired
    private GrupManager grupManager;

    @Autowired
    private AlumneManager alumneManager;

   @PostMapping("/ldap/actualitzarAlumnes")
    public ResponseEntity<String> actualitzarLdapAlumnes() throws NamingException {
       ldapManager.actualitzarAlumnesLdap(alumneManager.findAll());
       return new ResponseEntity<>("Canvis realitzats", HttpStatus.OK);
   }
}

