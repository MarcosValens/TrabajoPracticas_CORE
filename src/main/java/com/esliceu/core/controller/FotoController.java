package com.esliceu.core.controller;

import com.esliceu.core.manager.FileManager;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@RestController
public class FotoController {

    @Value("${UPLOAD.DIRECTORY.ZIP}")
    private String directorioZip;

    @Value("${UPLOAD.DIRECTORY.FOTOS}")
    private String direcotrioFotos;

    @Autowired
    private FileManager fileManager;

    @PostMapping("/private/uploadPhoto")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("codiGrup") String codiGrup) {

        String directorioFotosGrup = this.direcotrioFotos + codiGrup;

        try {

            byte[] bytes = file.getBytes();
            File directory = new File(directorioFotosGrup);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            File fileFoto = new File(directorioFotosGrup + "/" + file.getOriginalFilename());
            FileOutputStream out = new FileOutputStream(fileFoto);
            out.write(bytes);
            out.close();

            BufferedImage image = ImageIO.read(fileFoto);
            BufferedImage resized = fileManager.resize(image, 80, 80);

            File output = new File(directorioFotosGrup + "/" + file.getOriginalFilename());
            ImageIO.write(resized, "png", output);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/private/download/{numeroExpedient}/{codiGrup}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<String> downloadFileName(@PathVariable String numeroExpedient, @PathVariable String codiGrup) throws IOException {

        String directoriFotosGrup = this.direcotrioFotos + codiGrup + "/" + numeroExpedient + ".png";

        InputStream iSteamReader = new FileInputStream(directoriFotosGrup);
        byte[] imageBytes = IOUtils.toByteArray(iSteamReader);
        String base64 = Base64.getEncoder().encodeToString(imageBytes);

        return ResponseEntity.ok().body(base64);
    }

    @GetMapping(value = "/private/generate-zip/{codiGrup}", produces = "application/zip")
    public ResponseEntity zipGenerate(@PathVariable long codiGrup) {

        try {

            final String directorioZip = this.directorioZip;
            final String directorioFotos = this.direcotrioFotos + codiGrup + "/";
            final String nombreZip = "fotosGrup-" + codiGrup + ".zip";

            fileManager.generateZip(directorioZip, directorioFotos, nombreZip);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/private/download-zip/{nombreZip}", produces = "application/zip")
    public ResponseEntity zipDownload(@PathVariable String nombreZip) throws IOException {

        // TODO este endpoint funcionaba sin los tokens hay que hacer
        // ciertas modificaciones para que funcione con los tokens
        // ya que el resource solo funcionaría con un href y no puede llevar tokens.
        final String directorioZip = this.directorioZip + nombreZip;

        Path path = Paths.get(directorioZip);

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

    @GetMapping(value = "/private/list-zip")
    public ResponseEntity<String[]> listZipDownload() {

        final String directorioZip = this.directorioZip;
        File file = new File(directorioZip);

        String[] zipFiles = file.list();

        if (zipFiles.length == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(zipFiles, HttpStatus.OK);

    }
}
