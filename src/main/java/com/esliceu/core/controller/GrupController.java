package com.esliceu.core.controller;

import com.esliceu.core.entity.Grup;
import com.esliceu.core.entity.Professor;
import com.esliceu.core.manager.GrupManager;
import com.esliceu.core.manager.ProfessorManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GrupController {

    @Autowired
    private GrupManager grupManager;

    @Autowired
    private Gson gson;

    @Autowired
    private ProfessorManager professorManager;

    @GetMapping("/getAllGrups")
    public ResponseEntity<List<Grup>> getAll() {
        return new ResponseEntity<>(grupManager.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getGrupById/{codi}")
    public ResponseEntity<Grup> getById(@PathVariable("codi") Long codi) {
        return new ResponseEntity<>(grupManager.findById(codi), HttpStatus.OK);
    }

    @GetMapping("/getTutorsGrup/{codi}")
    public ResponseEntity<List<Professor>> getTutorsGrup(@PathVariable("codi") Long codi) {
        Grup grup = grupManager.findById(codi);
        List<Professor> professors = new ArrayList<>();
        Professor tutor1 = grup.getTutor1();
        Professor tutor2 = grup.getTutor2();
        Professor tutor3 = grup.getTutor3();

        professors.add(tutor1);
        professors.add(tutor2);
        professors.add(tutor3);

        return new ResponseEntity<>(professors, HttpStatus.OK);
    }

    @PutMapping("/updateTutorsGrup")
    public ResponseEntity<String> updateTutorsGrup(@RequestBody String json) {
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        JsonArray jsonArray = jsonObject.get("tutors").getAsJsonArray();
        Long grupCodi = jsonObject.get("grupCodi").getAsLong();
        Grup grup = grupManager.findById(grupCodi);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement codiJson = jsonArray.get(i);
            Professor professor;

            if (codiJson.isJsonNull()) {
                professor = null;
            } else {
                String codi = codiJson.getAsString();
                professor = professorManager.findById(codi);
            }

            if (i == 0) {
                grup.setTutor1(professor);
            }
            if (i == 1) {
                grup.setTutor2(professor);
            }
            if (i == 2) {
                grup.setTutor3(professor);
            }
        }
        grupManager.createOrUpdate(grup);
        return new ResponseEntity<>("Actualitzat", HttpStatus.OK);
    }
}
