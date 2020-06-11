package com.esliceu.core.controller;

import com.esliceu.core.entity.Grup;
import com.esliceu.core.manager.GrupManager;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GrupController {

    @Autowired
    private GrupManager grupManager;

    @Autowired
    private Gson gson;

    @GetMapping("/getAllGrups")
    public ResponseEntity<List<Grup>> getAll(){
        return new ResponseEntity<>(grupManager.findAll(), HttpStatus.OK);
    }
}
