package com.esliceu.core.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class FotoController {

    @PostMapping("/private/uploadPhoto")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("./src/main/java/com/esliceu/core/photos/" + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "/private/downloadPhotos", produces = "application/zip")
    public ResponseEntity<String> downloadPhotos(@RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("./src/main/java/com/esliceu/core/photos/" + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/zip-download/{codiGroup}", produces = "application/zip")
    public void zipDownload(@PathVariable long codiGroup, HttpServletResponse response) throws IOException {

        File path = new File("./src/main/java/com/esliceu/core/photos/");

        File[] listaArchivos = path.listFiles();

        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

        for (File file : listaArchivos) {
            if (file.getName().equals(Long.toString(codiGroup))) {
                System.out.println(file.getName());
                FileSystemResource resource = new FileSystemResource(path.getPath() + file.getName());
                ZipEntry zipEntry = new ZipEntry(resource.getFilename());
                zipEntry.setSize(resource.contentLength());
                zipOut.putNextEntry(zipEntry);
                StreamUtils.copy(resource.getInputStream(), zipOut);
                zipOut.closeEntry();

            }
        }

        zipOut.finish();
        zipOut.close();
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "nombreArchivo" + "\"");
    }
}
