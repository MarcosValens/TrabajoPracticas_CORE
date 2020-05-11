package com.esliceu.core.manager;

import com.esliceu.core.entity.Aula;
import com.esliceu.core.entity.Departament;
import com.esliceu.core.entity.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;


@Service
public class XmlParser {

    @Autowired
    private DepartamentManager departamentManager;

    @Autowired
    private AulaManager aulaManager;

    @Autowired
    private ProfessorManager professorManager;

    public void insertData(File file) {
        try {
            FileInputStream fileIS = new FileInputStream(file);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(fileIS);
            XPath xPath = XPathFactory.newInstance().newXPath();

            final String findDepartaments = "CENTRE_EXPORT/DEPARTAMENTS/DEPARTAMENT";
            final NodeList nodeListDepartaments = (NodeList) xPath.compile(findDepartaments).evaluate(xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListDepartaments.getLength(); i++) {
                //Departament
                Element element = (Element) nodeListDepartaments.item(i);
                Departament departament = new Departament();
                final long codi = Long.parseLong(element.getAttribute("codi"));
                final String descripcio = element.getAttribute("descripcio");
                System.out.println(codi);
                System.out.println(descripcio);
                departament.setCodi(codi);
                departament.setDescripcio(descripcio);
                departamentManager.create(departament);
            }

            final String findAules = "CENTRE_EXPORT/AULES/AULA";
            final NodeList nodeListAules = (NodeList) xPath.compile(findAules).evaluate(xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListAules.getLength(); i++) {
                Element element = (Element) nodeListAules.item(i);
                final long codi = Integer.parseInt(element.getAttribute("codi"));
                final String descripcio = element.getAttribute("descripcio");
                Aula aula = new Aula();
                aula.setCodi(codi);
                aula.setDescripcio(descripcio);
                aulaManager.create(aula);
            }

            final String findProfessors = "CENTRE_EXPORT/PROFESSORS/PROFESSOR";
            final NodeList nodeListProfessors = (NodeList) xPath.compile(findProfessors).evaluate(xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i <nodeListProfessors.getLength() ; i++) {
                Element element = (Element) nodeListAules.item(i);

                final String codi = element.getAttribute("codi");
                final String nom = element.getAttribute("nom");
                final String ap1 = element.getAttribute("ap1");
                final String ap2 = element.getAttribute("ap2");
                final String username = element.getAttribute("username");
                //final long departament = Long.parseLong(element.getAttribute("departament"));

                Professor professor = new Professor();
                professor.setCodi(codi);
                professor.setNom(nom);
                professor.setAp1(ap1);
                professor.setAp2(ap2);
                professor.setUsername(username);

                professorManager.create(professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
