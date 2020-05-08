package com.esliceu.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XMLController {

    @PutMapping("/uploadxml")
    public ResponseEntity<String> uploadXML() {

        return new ResponseEntity<>("L'XML s'ha desat correctament", HttpStatus.OK);

    }
}
