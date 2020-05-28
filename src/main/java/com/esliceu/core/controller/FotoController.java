package com.esliceu.core.controller;

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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class FotoController {

    @Value("${UPLOAD.DIRECTORY.ZIP}")
    private String directorioZip;

    @Value("${UPLOAD.DIRECTORY.FOTOS}")
    private String direcotrioFotos;

    @PostMapping("/private/uploadPhoto")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("codiGrup") String codiGrup) {

        String directorioFotosGrup = this.direcotrioFotos + codiGrup;

        try {

            System.out.println("Entra en el controller");

            byte[] bytes = file.getBytes();
            File directory = new File(directorioFotosGrup);

            if (!directory.exists()) {
                directory.mkdirs();
            }


            File fileFoto = new File(directorioFotosGrup + "/" + file.getOriginalFilename());
            BufferedImage image = ImageIO.read(fileFoto);

            FileOutputStream out = new FileOutputStream(fileFoto);
            out.write(bytes);
            out.close();

            BufferedImage resized = resize(image, 80, 80);

            File output = new File("./src/main/resources/nuevaaa4.png");
            ImageIO.write(resized, "png", output);


        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    @GetMapping("/private/download/{numeroExpedient}/{codiGroup}")
    public ResponseEntity downloadFileName(@PathVariable String numeroExpedient, @PathVariable long codiGroup) throws IOException {

        Path path = Paths.get(this.direcotrioFotos + codiGroup + "/" + numeroExpedient + ".png");

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

            final String directorioZip = this.directorioZip;
            final String directorioFotos = this.direcotrioFotos + codiGrup + "/";
            final String nombreZip = "fotosGrup-" + codiGrup + ".zip";

            File zipDirectory = new File(this.directorioZip);

            if (!zipDirectory.exists()){
                zipDirectory.mkdirs();
            }

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

    @GetMapping(value = "/private/download-zip/{nombreZip}", produces = "application/zip")
    public ResponseEntity zipDownload(@PathVariable String nombreZip) throws IOException {

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
