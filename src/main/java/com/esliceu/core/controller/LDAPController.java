package com.esliceu.core.controller;


import com.esliceu.core.manager.LDAPManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LDAPController {

    @Autowired
    private LDAPManager ldapManager;

    @GetMapping("/ldap/user")
    public ResponseEntity<String> ldapuser() {

        ldapManager.addUser();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
