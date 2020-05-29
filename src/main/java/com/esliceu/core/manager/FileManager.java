package com.esliceu.core.manager;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileManager {

    public BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public void generateZip(String directorioZip, String directorioFotos, String nombreZip) throws IOException {

        File zipDirectory = new File(directorioZip);

        if (!zipDirectory.exists()) {
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

    }
}
