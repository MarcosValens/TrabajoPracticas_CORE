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

        String zipFile = "./src/main/java/com/esliceu/core/photos";

        try {

            File dir = new File("./src/main/java/com/esliceu/core/photos");

            String[] srcFiles = dir.list();
            FileOutputStream fosas = new FileOutputStream("./src/main/java/com/esliceu/core/photos/multiCompressed.zip");
            ZipOutputStream zipOut = new ZipOutputStream(fosas);
            for (String srcFile : srcFiles) {
                File fileToZip = new File("./src/main/java/com/esliceu/core/photos/"+srcFile);
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
            fosas.close();

            Path path = Paths.get("./src/main/resources/photos/" + fileName);
            Resource resource = null;

            resource = new UrlResource(path.toUri());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);


/*        String[] paths = fasd.list();

        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

        for (String file : paths) {
            System.out.println(file);
            if (file.contains(Long.toString(codiGroup))) {
                System.out.println("ALGUNO COINCIDE");
                FileSystemResource resource = new FileSystemResource("./src/main/java/com/esliceu/core/photos/" + file);
                System.out.println(resource.getFilename());
                ZipEntry zipEntry = new ZipEntry(resource.getFilename());
                zipEntry.setSize(resource.contentLength());
                zipOut.putNextEntry(zipEntry);
                StreamUtils.copy(resource.getInputStream(), zipOut);
                zipOut.closeEntry();

            }
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "probandoZIP");
        responseHeaders.add(HttpHeaders.CONTENT_TYPE,MediaType.)*/


    }
}
