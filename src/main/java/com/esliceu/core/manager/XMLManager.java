package com.esliceu.core.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class XMLManager {

    @Autowired
    private Environment environment;

    @Autowired
    private XmlParser xmlParser;

    public boolean readXML(MultipartFile fileMultiPart) {

        File file;
        List<List> listaObjetosXML = new ArrayList<>();

        try {

            boolean insertBD;

            file = convertMultiPartToFile(fileMultiPart);
            listaObjetosXML = xmlParser.parseXML(file);
            /*            insertBD=insertarBD(listaObjetosXML);*/


            // Leer cada objeto y con el manager correspondiente a cada objeto meter en la base de datos

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

/*    public boolean

    // Inserta en la base de datos todos los objetos de la lista obtenida del XML
    public boolean insertarBD(List<>listaXML){



        return true;

    }*/
}
