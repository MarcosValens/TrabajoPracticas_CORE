package com.esliceu.core.controller;

import com.esliceu.core.manager.XMLManager;
import com.esliceu.core.manager.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;

@RestController
public class XMLController {

    @Autowired
    private XMLManager xmlManager;

    @Autowired
    XmlParser xmlParser;

    @PutMapping("/uploadxml")
    @Transactional
    public ResponseEntity<String> uploadXML(@RequestPart(value = "file") final MultipartFile uploadfile) {

        boolean readAndInsertXML = xmlManager.readAndInsertXML(uploadfile);

        if (readAndInsertXML == false) {
            return new ResponseEntity<>("L'XML no s'ha pogut desar", HttpStatus.BAD_REQUEST);

        } else {
            return new ResponseEntity<>("L'XML s'ha desat correctament", HttpStatus.OK);
        }
    }

    @GetMapping("/xml")
    public ResponseEntity<String> xml(){
        xmlParser.insertData(new File("exportacioDadesCentre.xml"));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
