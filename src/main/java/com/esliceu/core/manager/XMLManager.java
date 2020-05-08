package com.esliceu.core.manager;

import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class XMLManager {

    @Autowired
    private Environment environment;

    public void readXML() {


    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {

        /*
         * Volvemos a juntar las partes del archivo ya que desde el
         * cliente nos llega en partes
         * */
        File convFile = new File(path + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
