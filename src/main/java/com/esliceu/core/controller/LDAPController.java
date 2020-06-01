package com.esliceu.core.controller;


import com.esliceu.core.manager.LDAPManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.naming.NamingException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

@RestController
public class LDAPController {

    @Autowired
    private LDAPManager ldapManager;

    @GetMapping("/ldap/user")
    public ResponseEntity<String> ldapuser() {

        ldapManager.addUser();

        System.out.println("Llega al Controller");

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
