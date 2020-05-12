package com.esliceu.core.controller;

import com.esliceu.core.manager.XMLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@RestController
public class XMLController {

    @Autowired
    private XMLManager xmlManager;

    @PutMapping("/uploadxml")
    @Transactional
    public ResponseEntity<String> uploadXML(@RequestPart(value = "file") final MultipartFile uploadfile) {

        boolean correcto = xmlManager.readAndInsertXML(uploadfile);

        if (correcto == false) {
            return new ResponseEntity<>("L'XML no s'ha pogut desar", HttpStatus.BAD_REQUEST);

        } else {
            return new ResponseEntity<>("L'XML s'ha desat correctament", HttpStatus.OK);
        }
    }
}
