package com.esliceu.core.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class XMLManager {

    @Autowired
    private Environment environment;

    @Autowired
    private XmlParser xmlParser;

    public boolean readAndInsertXML(MultipartFile fileMultiPart) {

        File file;

        try {

            file = convertMultiPartToFile(fileMultiPart);
            xmlParser.insertData(file);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {

        // Juntamos las partes del archivo ya que cuando hace el upload se sube pòr partes
        File convFile = new File(environment.getProperty("upload.directory") + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
