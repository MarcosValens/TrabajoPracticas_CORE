package com.esliceu.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FotoController {

    @PostMapping("/uploadPhoto")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file){
        try{
            byte[] bytes = file.getBytes();
            Path path = Paths.get("./src/main/java/com/esliceu/core/photos/"+file.getOriginalFilename());
            Files.write(path, bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
