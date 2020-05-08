/*
package com.esliceu.core.manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;

public class XmlParser {

    public static void main(String[] args) {
        String fileName = "./exportacioDadesCentre.xml";
        parseXML(fileName);
    }

    private static List<List> parseXML(String fileName) {

        */
/*PRIMER BLOQUE "CURS"*//*

        List<Curs> cursList = new ArrayList<>();
        List<Grup> grupList = new ArrayList<>();
        List<Avaluacio> avaluacioList = new ArrayList<>();
        List<List> avaluacioLists = new ArrayList<>();
        List<Note> noteList = new ArrayList<>();
        List<List> noteLists = new ArrayList<>();
        Curs curs;
        Grup grup;
        Avaluacio avaluacio;
        Note note;

        */
/*SEGUNDO BLOQUE "SUBMATERIES"*//*

        List<Submateria> submateries = new ArrayList<>();
        Submateria submateria;

        */
/*TERCER BLOQUE "ACTIVITATS"*//*

        List<Activitat> activitats = new ArrayList<>();
        Activitat activitat;

        */
/*CUARTO BLOQUE "ALUMNE"*//*

        List<Alumne> alumnes = new ArrayList<>();
        Alumne alumne;

        */
/*CUARTO BLOQUE "PROFESSOR"*//*

        List<Professor> professors = new ArrayList<>();
        Professor professor;

        */
/*QUINTO BLOQUE "SESSIO"*//*


        List<Sessio> sessions = new ArrayList<>();
        Sessio sessio;

        */
/*SEXTO BLOQUE "TUTOR"*//*

        List<Tutor> tutors = new ArrayList<>();
        Tutor tutor;

        */
/*NOVENO BLOQUE "DEPARTAMENT"*//*

        List<Departament> departaments = new ArrayList<>();
        Departament departament;

        */
/*EL RESULTADO DE LA FUNCION*//*

        List<List> parsedXML = new ArrayList<>();

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "CURS":
                            curs = new Curs();
                            //Get the 'id' attribute from curs element
                            Attribute codiCurs = startElement.getAttributeByName(new QName("codi"));
                            Attribute descripcioCurs = startElement.getAttributeByName(new QName("descripcio"));
                            curs.setCodi(Integer.parseInt(codiCurs.getValue()));
                            curs.setDescripcio(descripcioCurs.getValue());
                            cursList.add(curs);
                            curs.setGrup(grupList);
                            break;
                        case "GRUP":
                            grup = new Grup();
                            Attribute codiGrup = startElement.getAttributeByName(new QName("codi"));
                            Attribute nomGrup = startElement.getAttributeByName(new QName("nom"));
                            Attribute tutorGrup = startElement.getAttributeByName(new QName("tutor"));
                            grup.setCodi(Integer.parseInt(codiGrup.getValue()));
                            grup.setNom(nomGrup.getValue());
                            grup.setTutor(tutorGrup.getValue());
                            grupList.add(grup);
                            grup.setAvaluacions(avaluacioLists);
                            break;
                        case "AVALUACIONS":
                            if (!avaluacioList.isEmpty()) {
                                avaluacioList = new ArrayList<>();
                            }
                            avaluacioLists.add(avaluacioList);
                            break;
                        case "AVALUACIO":
                            avaluacio = new Avaluacio();
                            Attribute codiAvaluacio = startElement.getAttributeByName(new QName("codi"));
                            Attribute descripcioAvaluacio = startElement.getAttributeByName(new QName("descripcio"));
                            Attribute data_inici = startElement.getAttributeByName(new QName("data_inici"));
                            Attribute data_fi = startElement.getAttributeByName(new QName("data_fi"));
                            avaluacio.setCodi(Integer.parseInt(codiAvaluacio.getValue()));
                            avaluacio.setDescripcio(descripcioAvaluacio.getValue());
                            avaluacio.setData_inici(data_inici.getValue());
                            avaluacio.setData_fi(data_fi.getValue());
                            avaluacioList.add(avaluacio);
                            break;
                        case "NOTES":
                            noteList = new ArrayList<>();
                            noteLists.add(noteList);
                            break;
                        case "NOTA":
                            note = new Note();
                            Attribute qualificacioNote = startElement.getAttributeByName(new QName("qualificacio"));
                            Attribute descNote = startElement.getAttributeByName(new QName("desc"));
                            note.setQualificacio(Integer.parseInt(qualificacioNote.getValue()));
                            note.setDesc(descNote.getValue());
                            noteList.add(note);
                            break;
                        case "SUBMATERIA":
                            submateria = new Submateria();
                            Attribute codiSubmateria = startElement.getAttributeByName(new QName("codi"));
                            Attribute cursSubmateria = startElement.getAttributeByName(new QName("curs"));
                            Attribute descripcioSubmateria = startElement.getAttributeByName(new QName("descripcio"));
                            Attribute curtaSubmateria = startElement.getAttributeByName(new QName("curta"));
                            submateria.setCodi(Integer.parseInt(codiSubmateria.getValue()));
                            submateria.setCurs(Integer.parseInt(cursSubmateria.getValue()));
                            submateria.setDescripcio(descripcioSubmateria.getValue());
                            submateria.setCurta(curtaSubmateria.getValue());
                            submateries.add(submateria);
                            break;
                        case "ACTIVITAT":
                            activitat = new Activitat();
                            Attribute codiActivitat = startElement.getAttributeByName(new QName("codi"));
                            Attribute descripcioActivitat = startElement.getAttributeByName(new QName("descripcio"));
                            Attribute curtaActivitat = startElement.getAttributeByName(new QName("curta"));
                            activitat.setCodi(Integer.parseInt(codiActivitat.getValue()));
                            activitat.setDescripcio(descripcioActivitat.getValue());
                            activitat.setCurta(curtaActivitat.getValue());
                            activitats.add(activitat);
                            break;
                        case "ALUMNE":
                            alumne = new Alumne();
                            Attribute codiAlumne = startElement.getAttributeByName(new QName("codi"));
                            Attribute nomAlumne = startElement.getAttributeByName(new QName("nom"));
                            Attribute ap1Alumne = startElement.getAttributeByName(new QName("ap1"));
                            Attribute ap2Alumne = startElement.getAttributeByName(new QName("ap2"));
                            Attribute expedientAlumne = startElement.getAttributeByName(new QName("expedient"));
                            Attribute grupAlumne = startElement.getAttributeByName(new QName("grup"));
                            alumne.setCodi(codiAlumne.getValue());
                            alumne.setNom(nomAlumne.getValue());
                            alumne.setAp1(ap1Alumne.getValue());
                            alumne.setAp2(ap2Alumne.getValue());
                            alumne.setExpedient(Integer.parseInt(expedientAlumne.getValue()));
                            alumne.setGrup(Integer.parseInt(grupAlumne.getValue()));
                            alumnes.add(alumne);
                            break;
                        case "PROFESSOR":
                            professor = new Professor();
                            Attribute codiProfessor = startElement.getAttributeByName(new QName("codi"));
                            Attribute nomProfessor = startElement.getAttributeByName(new QName("nom"));
                            Attribute ap1Professor = startElement.getAttributeByName(new QName("ap1"));
                            Attribute ap2Professor = startElement.getAttributeByName(new QName("ap2"));
                            Attribute usernameProfessor = startElement.getAttributeByName(new QName("username"));
                            Attribute departamentProfessor = startElement.getAttributeByName(new QName("departament"));
                            professor.setCodi(codiProfessor.getValue());
                            professor.setNom(nomProfessor.getValue());
                            professor.setAp1(ap1Professor.getValue());
                            professor.setAp2(ap2Professor.getValue());
                            professor.setUsername(usernameProfessor.getValue());
                            if (departamentProfessor.getValue().equals("")) {
                                professor.setDepartament(0);
                            } else {
                                professor.setDepartament(Integer.parseInt(departamentProfessor.getValue()));
                            }
                            professors.add(professor);
                            break;
                        case "SESSIO":
                            sessio = new Sessio();
                            Attribute professorSessio = startElement.getAttributeByName(new QName("professor"));
                            Attribute alumneSessio = startElement.getAttributeByName(new QName("alumne"));
                            Attribute cursSessio = startElement.getAttributeByName(new QName("curs"));
                            Attribute grupSessio = startElement.getAttributeByName(new QName("grup"));
                            Attribute diaSessio = startElement.getAttributeByName(new QName("dia"));
                            Attribute horaSessio = startElement.getAttributeByName(new QName("hora"));
                            Attribute duradaSessio = startElement.getAttributeByName(new QName("durada"));
                            Attribute aulaSessio = startElement.getAttributeByName(new QName("aula"));
                            Attribute submateriaSessio = startElement.getAttributeByName(new QName("submateria"));
                            Attribute activitatSessio = startElement.getAttributeByName(new QName("activitat"));
                            Attribute placaSessio = startElement.getAttributeByName(new QName("placa"));
                            if (professorSessio != null) {
                                sessio.setProfessor(professorSessio.getValue());
                            }
                            if (alumneSessio != null) {
                                sessio.setAlumne(alumneSessio.getValue());
                            }
                            if (cursSessio != null) {
                                sessio.setCurs(cursSessio.getValue());
                            }
                            if (grupSessio != null) {
                                sessio.setGrup(grupSessio.getValue());
                            }
                            sessio.setDia(diaSessio.getValue());
                            sessio.setHora(horaSessio.getValue());
                            sessio.setDurada(duradaSessio.getValue());
                            if (aulaSessio != null) {
                                sessio.setAula(aulaSessio.getValue());
                            }
                            if (submateriaSessio != null) {
                                sessio.setSubmateria(submateriaSessio.getValue());
                            }
                            if (activitatSessio != null) {
                                sessio.setActivitat(activitatSessio.getValue());
                            }
                            if (placaSessio != null) {
                                sessio.setPlaca(placaSessio.getValue());
                            }
                            if (professorSessio != null && alumneSessio == null) {
                                sessio.setHorari("HorariP");
                            } else if (professorSessio == null && alumneSessio != null) {
                                sessio.setHorari("HorariA");
                            } else sessio.setHorari("HorariG");
                            sessions.add(sessio);
                            break;
                        case "TUTOR":
                            tutor = new Tutor();
                            Attribute codiAlumneTutor = startElement.getAttributeByName(new QName("codiAlumne"));
                            Attribute codiTutor = startElement.getAttributeByName(new QName("codiTutor"));
                            Attribute llinatge1Tutor = startElement.getAttributeByName(new QName("llinatge1"));
                            Attribute llinatge2Tutor = startElement.getAttributeByName(new QName("llinatge2"));
                            Attribute nomTutor = startElement.getAttributeByName(new QName("nom"));
                            Attribute relacioTutor = startElement.getAttributeByName(new QName("relacio"));
                            tutor.setAlumne(Long.parseLong(codiAlumneTutor.getValue()));
                            tutor.setCodi(Long.parseLong(codiTutor.getValue()));
                            tutor.setLlinatge1(llinatge1Tutor.getValue());
                            tutor.setLlinatge2(llinatge2Tutor.getValue());
                            tutor.setNom(nomTutor.getValue());
                            tutor.setRelacio(relacioTutor.getValue());
                            tutors.add(tutor);
                            break;
                        case "DEPARTAMENT":
                            departament = new Departament();
                            Attribute codiDepartament = startElement.getAttributeByName(new QName("codi"));
                            Attribute descripcioDepartament = startElement.getAttributeByName(new QName("descripcio"));
                            departament.setCodi(Integer.parseInt(codiDepartament.getValue()));
                            departament.setDescripcio(descripcioDepartament.getValue());
                            departaments.add(departament);
                            break;
                    }
                }
            }

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        parsedXML.add(cursList);
        parsedXML.add(noteLists);
        parsedXML.add(submateries);
        parsedXML.add(activitats);
        parsedXML.add(alumnes);
        parsedXML.add(professors);
        parsedXML.add(sessions);
        parsedXML.add(tutors);
        parsedXML.add(departaments);
        return parsedXML;
    }
}
*/
