package com.esliceu.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@RestController
public class XMLController {

    @PutMapping("/uploadxml")
    @Transactional
    public ResponseEntity<String> uploadXML(@RequestPart(value = "file") final MultipartFile uploadfile) {

        System.out.println(uploadfile);
        System.out.println("Llega al controller");

        return new ResponseEntity<>("L'XML s'ha desat correctament", HttpStatus.OK);

    }
}
