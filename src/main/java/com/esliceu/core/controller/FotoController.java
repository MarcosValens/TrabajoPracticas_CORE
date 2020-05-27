package com.esliceu.core.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class FotoController {

    @PostMapping("/private/uploadPhoto")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("codiGrup") String codiGrup) {

        String directorioFotos = "./src/main/resources/photos/" + codiGrup;

        try {

            byte[] bytes = file.getBytes();

            File directory = new File(directorioFotos);

            if (!directory.exists()) {
                directory.mkdir();
            }

            File fileFoto = new File(directorioFotos + "/" + file.getOriginalFilename());

            FileOutputStream out = new FileOutputStream(fileFoto);
            out.write(bytes);
            out.close();

        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/private/download/{fileName:.+}/{codiGroup}")
    public ResponseEntity downloadFileName(@PathVariable String fileName, @PathVariable long codiGroup) throws IOException {

        Path path = Paths.get("./src/main/resources/photos/" + codiGroup + "/" + fileName);

        if (!Files.exists(path)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Resource resource;

        resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(value = "/private/generate-zip/{codiGrup}", produces = "application/zip")
    public ResponseEntity zipGenerate(@PathVariable long codiGrup) {

        try {

            final String directorioZip = "./src/main/resources/zip/";
            final String directorioFotos = "./src/main/resources/photos/" + codiGrup + "/";
            final String nombreZip = "fotosGrup-" + codiGrup + ".zip";

            // Directorio donde se encuentran las fotos
            File directorio = new File(directorioFotos);

            // Obtenemos el nombre de todos los archivos del directorio
            String[] nombreFotos = directorio.list();

            FileOutputStream fileOutput = new FileOutputStream(directorioZip + nombreZip);
            ZipOutputStream zipOut = new ZipOutputStream(fileOutput);

            for (String nombre : nombreFotos) {

                File fileToZip = new File(directorioFotos + nombre);
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;

                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }

                fis.close();
            }

            zipOut.close();
            fileOutput.close();

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/private/download-zip/{codiGroup}", produces = "application/zip")
    public ResponseEntity zipDownload(@PathVariable long codiGroup) throws IOException {

        final String directorioZip = "./src/main/resources/zip/";
        final String directorioFotos = "./src/main/resources/photos/" + codiGroup;
        final String nombreZip = "fotosGrup-" + codiGroup + ".zip";

        // Eliminamos los posibles ZIP anteriores
        File ZIPFiles = new File(directorioZip);

        /*
        Comprobar que si el zip ya existe sobreescriba con el zip nuevo
         */

        // Directorio donde se encuentran las fotos
        File directorio = new File(directorioFotos);

        // Obtenemos el nombre de todos los archivos del directorio
        String[] nombreFotos = directorio.list();

        FileOutputStream fileOutput = new FileOutputStream(directorioZip + nombreZip);
        ZipOutputStream zipOut = new ZipOutputStream(fileOutput);

        for (String nombre : nombreFotos) {
            String codigoNombre = nombre.split("-")[0];
            if (codigoNombre.equals(Long.toString(codiGroup))) {
                File fileToZip = new File(directorioFotos + nombre);


                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;

                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }

                fis.close();
            }
        }

        zipOut.close();
        fileOutput.close();

        Path path = Paths.get(directorioZip + nombreZip);
        Resource resource;
        resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

}
