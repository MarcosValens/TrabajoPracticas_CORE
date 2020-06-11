
package com.esliceu.core.controller;


import com.esliceu.core.manager.AlumneManager;
import com.esliceu.core.manager.GrupManager;
import com.esliceu.core.manager.LDAPManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/ldap/user")
    public ResponseEntity<String> ldapuser() {
        ldapManager.addAlumne(alumneManager.findAll());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/ldap/group")
    public ResponseEntity<String> ldapgroup(){
        ldapManager.addGroup(grupManager.findAll());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/ldap/search")
    public ResponseEntity<String> ldapsearch(){
        try {
            ldapManager.searchAlumne("aaguila");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/ldap/update")
    public ResponseEntity<String> ldapupdate(){
        try {
            ldapManager.editAlumne("aaguila", "pepe");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/ldap/delete")
    public ResponseEntity<String> ldapdelete(){
        try {
            ldapManager.deleteAlumne("aaguila");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

