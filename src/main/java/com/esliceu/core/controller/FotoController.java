package com.esliceu.core.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class FotoController {

    @PostMapping("/private/uploadPhoto")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {

        try {

            byte[] bytes = file.getBytes();
            Path path = Paths.get("./src/main/resources/photos/" + file.getOriginalFilename());
            Files.write(path, bytes);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity downloadFileName(@PathVariable String fileName) {

        Path path = Paths.get("./src/main/resources/photos/" + fileName);

        Resource resource = null;

        try {

            resource = new UrlResource(path.toUri());

        } catch (MalformedURLException e) {

            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(value = "/zip-download/{codiGroup}", produces = "application/zip")
    public ResponseEntity zipDownload(@PathVariable long codiGroup, HttpServletResponse response) throws IOException {

        System.out.println("LLEGA AL CONTROLLER");

        final String nombreZip = "fotosGrup-" + codiGroup + ".zip";

        // Directorio donde se guardará el ZIP cont odas las fotos del grupo seleccionado
        File directorio = new File("./src/main/java/com/esliceu/core/photos/");

        // Obtenemos el nombre de todos los archivos del directorio
        String[] nombreFotos = directorio.list();

        FileOutputStream fileOutput = new FileOutputStream("./src/main/java/com/esliceu/core/photos/" + nombreZip);
        ZipOutputStream zipOut = new ZipOutputStream(fileOutput);

        System.out.println(nombreFotos);

        for (String nombre : nombreFotos) {
            System.out.println(nombre);
            if (nombre.contains(Long.toString(codiGroup))) {
                System.out.println("ENTRA");
                File fileToZip = new File("./src/main/java/com/esliceu/core/photos/" + nombre);
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

        Path path = Paths.get("./src/main/java/com/esliceu/core/photos/" + nombreZip);
        Resource resource = null;

        resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }
}
