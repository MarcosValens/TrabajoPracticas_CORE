
package com.esliceu.core.controller;


import com.esliceu.core.manager.AlumneManager;
import com.esliceu.core.manager.GrupManager;
import com.esliceu.core.manager.AlumneLDAPManager;
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
    private AlumneManager alumneManager;

   @PostMapping("/ldap/actualitzarAlumnes")
    public ResponseEntity<String> actualitzarLdapAlumnes() throws NamingException {
       alumneLdapManager.actualitzarAlumnesLdap(alumneManager.findAll());
       return new ResponseEntity<>("Canvis realitzats", HttpStatus.OK);
   }
}

