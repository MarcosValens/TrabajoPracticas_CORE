package com.esliceu.core.manager;

import com.esliceu.core.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class XmlParser {

    @Autowired
    private DepartamentManager departamentManager;

    @Autowired
    private AulaManager aulaManager;

    @Autowired
    private ProfessorManager professorManager;

    @Autowired
    private ActivitatManager activitatManager;

    @Autowired
    private CursManager cursManager;

    @Autowired
    private GrupManager grupManager;

    public void insertData(File file) {
        try {
            FileInputStream fileIS = new FileInputStream(file);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(fileIS);
            XPath xPath = XPathFactory.newInstance().newXPath();

            //Buscamos todos los departamentos y los añadimos a la BBDD
            final String findDepartaments = "CENTRE_EXPORT/DEPARTAMENTS/DEPARTAMENT";
            final NodeList nodeListDepartaments = (NodeList) xPath.compile(findDepartaments).evaluate(xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListDepartaments.getLength(); i++) {
                //Departament
                Element element = (Element) nodeListDepartaments.item(i);
                Departament departament = new Departament();
                final long codi = Long.parseLong(element.getAttribute("codi"));
                final String descripcio = element.getAttribute("descripcio");
                departament.setCodi(codi);
                departament.setDescripcio(descripcio);
                departamentManager.createOrUpdate(departament);
            }

            //Buscamos todos las aulas y las añadimos a la BBDD
            final String findAules = "CENTRE_EXPORT/AULES/AULA";
            final NodeList nodeListAules = (NodeList) xPath.compile(findAules).evaluate(xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListAules.getLength(); i++) {
                Element element = (Element) nodeListAules.item(i);
                final long codi = Integer.parseInt(element.getAttribute("codi"));
                final String descripcio = element.getAttribute("descripcio");
                Aula aula = new Aula();
                aula.setCodi(codi);
                aula.setDescripcio(descripcio);
                aulaManager.createOrUpdate(aula);
            }

            //Buscamos todas las actividades y las añadimos a la BBDD
            final String findActivitats = "CENTRE_EXPORT/ACTIVITATS/ACTIVITAT";
            final NodeList nodeListActivitats = (NodeList) xPath.compile(findActivitats).evaluate(xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListActivitats.getLength(); i++) {
                Element element = (Element) nodeListActivitats.item(i);

                final Long codi = Long.parseLong(element.getAttribute("codi"));
                final String descripcio = element.getAttribute("descripcio");
                final String curta = element.getAttribute("curta");

                Activitat activitat = new Activitat();
                activitat.setCodi(codi);
                activitat.setDescripcio(descripcio);
                activitat.setCurta(curta);
                activitatManager.createOrUpdate(activitat);
            }

            //Buscamos todos los profesores y los añadimos a la BBDD asignando su correspondiente departamento si tienen
            final String findProfessors = "CENTRE_EXPORT/PROFESSORS/PROFESSOR";
            final NodeList nodeListProfessors = (NodeList) xPath.compile(findProfessors).evaluate(xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListProfessors.getLength(); i++) {
                Element element = (Element) nodeListProfessors.item(i);

                final String codi = element.getAttribute("codi");
                final String nom = element.getAttribute("nom");
                final String ap1 = element.getAttribute("ap1");
                final String ap2 = element.getAttribute("ap2");
                final String username = element.getAttribute("username");
                final String departament = element.getAttribute("departament");

                Professor professor = new Professor();
                professor.setCodi(codi);
                professor.setNom(nom);
                professor.setAp1(ap1);
                professor.setAp2(ap2);
                professor.setUsername(username);
                //Aqui se asigna el departamento del profesor
                if (departament != null && !departament.equals("")) {
                    Departament departamentProfessor = departamentManager.findById(Long.parseLong(departament));
                    professor.setDepartament(departamentProfessor);
                }
                professorManager.createOrUpdate(professor);
            }

            //Buscamos todos los cursos y los añadimos a la BBDD
            final String findCursos = "CENTRE_EXPORT/CURSOS/CURS";
            final NodeList nodeListCursos = (NodeList) xPath.compile(findCursos).evaluate(xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListCursos.getLength(); i++) {
                Element element = (Element) nodeListCursos.item(i);
                final Long codi = Long.parseLong(element.getAttribute("codi"));
                final String descripcio = element.getAttribute("descripcio");
                Curs curs = new Curs();
                curs.setCodi(codi);
                curs.setDescripcio(descripcio);
                cursManager.createOrUpdate(curs);
                //Por cada curso sacamos sus grupos
                NodeList nodeListGrups = element.getElementsByTagName("GRUP");
                for (int j = 0; j < nodeListGrups.getLength() ; j++) {
                    Element elementGrup = (Element) nodeListGrups.item(j);

                    Grup grup = new Grup();
                    final long codiGrup = Long.parseLong(elementGrup.getAttribute("codi"));
                    final String nom = elementGrup.getAttribute("nom");

                    grup.setCodi(codiGrup);
                    grup.setNom(nom);

                    //Sacamos todos los atributos de ese grupo para poder buscar todos sus tutores
                    NamedNodeMap nodeListAttr = elementGrup.getAttributes();
                    List<Professor> professors = new ArrayList<>();
                    for (int k = 0; k <nodeListAttr.getLength() ; k++) {
                        Attr attr = (Attr) nodeListAttr.item(k);
                        //Buscamos cada tutor en la BBDD y se lo asignamos al grupo
                        if (attr.getName().contains("tutor")){
                            final String tutor = attr.getValue();
                            Professor professorTutor = professorManager.findById(tutor);
                            if (professorTutor != null){
                                professors.add(professorTutor);
                            }
                        }
                    }
                    //Buscamos el curso por la ID y se lo asignamos al grupo
                    Curs grupCurso = cursManager.findById(codi);
                    grup.setCurs(grupCurso);
                    grup.setProfessors(professors);
                    grupManager.createOrUpdate(grup);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
