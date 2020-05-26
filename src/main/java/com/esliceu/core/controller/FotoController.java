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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class FotoController {

    @PostMapping("/private/uploadPhoto")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("codiGrup") String codiGrup) {
        System.out.println(codiGrup);
        try {

            byte[] bytes = file.getBytes();
            Path path = Paths.get("./src/main/resources/photos/" + file.getOriginalFilename());
            Files.write(path, bytes);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/private/download/{fileName:.+}")
    public ResponseEntity downloadFileName(@PathVariable String fileName) throws IOException {

        Path path = Paths.get("./src/main/resources/photos/" + fileName);

        Resource resource;

        resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(value = "/private/zip-download/{codiGroup}", produces = "application/zip")
    public ResponseEntity zipDownload(@PathVariable long codiGroup) throws IOException {

        final String directorioZip = "./src/main/resources/zip/";
        final String directorioFotos = "./src/main/resources/photos/";
        final String nombreZip = "fotosGrup-" + codiGroup + ".zip";

        // Eliminamos los posibles ZIP anteriores
        File ZIPFiles = new File(directorioZip);

        String[] nameZip = ZIPFiles.list();

        if(nameZip != null){
            for (String name : nameZip) {

                File file = new File(directorioZip + name);

                if (file.delete()) {
                    System.out.println("Se ha borrado el archivo");
                } else {
                    System.out.println("No se ha podido borrar");
                }
            }
        }

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
