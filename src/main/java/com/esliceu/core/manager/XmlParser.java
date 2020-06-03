package com.esliceu.core.manager;

import com.esliceu.core.entity.*;
import com.esliceu.core.utils.DateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Autowired
    private AvaluacioManager avaluacioManager;

    @Autowired
    private NotaManager notaManager;

    @Autowired
    private SubmateriaManager submateriaManager;

    @Autowired
    private AlumneManager alumneManager;

    @Autowired
    private TutorManager tutorManager;

    @Autowired
    private TutorAlumneManager tutorAlumneManager;

    @Autowired
    private SessioManager sessioManager;

    private XPath xPath;
    private Document xmlDocument;

    private void prepare(File file) {
        try {
            FileInputStream fileIS = new FileInputStream(file);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            this.xmlDocument = builder.parse(fileIS);
            this.xPath = XPathFactory.newInstance().newXPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearDepartaments() {
        try {
            long startTime = System.currentTimeMillis();
            //Buscamos todos los departamentos y los añadimos a la BBDD
            final String findDepartaments = "CENTRE_EXPORT/DEPARTAMENTS/DEPARTAMENT";
            final NodeList nodeListDepartaments = (NodeList) this.xPath.compile(findDepartaments).evaluate(this.xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListDepartaments.getLength(); i++) {
                //Departament
                Element elementDepartament = (Element) nodeListDepartaments.item(i);
                Departament departament;
                final long codi = Long.parseLong(elementDepartament.getAttribute("codi"));
                departament = departamentManager.findById(codi);
                if (departament == null) {
                    departament = new Departament();
                    departament.setCodi(codi);
                }
                final String descripcio = elementDepartament.getAttribute("descripcio");
                departament.setDescripcio(descripcio);
                departamentManager.createOrUpdate(departament);
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearAules() {
        try {
            long startTime = System.currentTimeMillis();
            //Buscamos todos las aulas y las añadimos a la BBDD
            final String findAules = "CENTRE_EXPORT/AULES/AULA";
            final NodeList nodeListAules = (NodeList) this.xPath.compile(findAules).evaluate(this.xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListAules.getLength(); i++) {
                Element elementAula = (Element) nodeListAules.item(i);
                final long codi = Integer.parseInt(elementAula.getAttribute("codi"));
                final String descripcio = elementAula.getAttribute("descripcio");
                Aula aula = aulaManager.findById(codi);
                if (aula == null) {
                    aula = new Aula();
                    aula.setCodi(codi);
                }
                aula.setDescripcio(descripcio);
                aulaManager.createOrUpdate(aula);
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearActivitats() {
        try {
            long startTime = System.currentTimeMillis();
            //Buscamos todas las actividades y las añadimos a la BBDD
            final String findActivitats = "CENTRE_EXPORT/ACTIVITATS/ACTIVITAT";
            final NodeList nodeListActivitats = (NodeList) this.xPath.compile(findActivitats).evaluate(this.xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListActivitats.getLength(); i++) {
                Element elementActivitat = (Element) nodeListActivitats.item(i);

                final Long codi = Long.parseLong(elementActivitat.getAttribute("codi"));
                final String descripcio = elementActivitat.getAttribute("descripcio");
                final String curta = elementActivitat.getAttribute("curta");

                Activitat activitat = activitatManager.findById(codi);
                if (activitat == null) {
                    activitat = new Activitat();
                    activitat.setCodi(codi);
                }
                activitat.setDescripcio(descripcio);
                activitat.setCurta(curta);
                activitatManager.createOrUpdate(activitat);
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearProfessors() {
        try {
            long startTime = System.currentTimeMillis();
            //Buscamos todos los profesores y los añadimos a la BBDD asignando su correspondiente departamento si tienen
            final String findProfessors = "CENTRE_EXPORT/PROFESSORS/PROFESSOR";
            final NodeList nodeListProfessors = (NodeList) this.xPath.compile(findProfessors).evaluate(this.xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListProfessors.getLength(); i++) {
                Element elementProfessor = (Element) nodeListProfessors.item(i);

                final String codi = elementProfessor.getAttribute("codi");
                final String nom = elementProfessor.getAttribute("nom");
                final String ap1 = elementProfessor.getAttribute("ap1");
                final String ap2 = elementProfessor.getAttribute("ap2");
                final String username = elementProfessor.getAttribute("username");
                final String departament = elementProfessor.getAttribute("departament");
                Professor professor = professorManager.findById(codi);
                if (professor == null) {
                    professor = new Professor();
                    professor.setCodi(codi);
                    professor.setNom(nom);
                    professor.setAp1(ap1);
                    professor.setAp2(ap2);
                    professor.setUsername(username);
                    professor.setEliminat(false);
                    //Aqui se asigna el departamento del profesor
                    if (departament != null && !departament.equals("")) {
                        Departament departamentProfessor = departamentManager.findById(Long.parseLong(departament));
                        professor.setDepartament(departamentProfessor);
                    }
                } else {
                    if (departament != null && !departament.equals("")) {
                        Departament newDepartamentoProfessor = departamentManager.findById(Long.parseLong(departament));
                        if (professor.getDepartament() == null) {
                            professor.setDepartament(newDepartamentoProfessor);

                        } else {
                            Departament departamentProfessor = departamentManager.findById(professor.getDepartament().getCodi());
                            if (!departamentProfessor.equals(newDepartamentoProfessor)) {
                                professor.setDepartament(newDepartamentoProfessor);
                            }
                        }
                    }
                    professor.setEliminat(false);
                }
                professorManager.createOrUpdate(professor);
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearCursosGruposAvaluacionesNotas() {
        try {
            long startTime = System.currentTimeMillis();
            //Buscamos todos los cursos y los añadimos a la BBDD
            final String findCursos = "CENTRE_EXPORT/CURSOS/CURS";
            final NodeList nodeListCursos = (NodeList) this.xPath.compile(findCursos).evaluate(this.xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListCursos.getLength(); i++) {
                Element elementCurs = (Element) nodeListCursos.item(i);
                final Long codi = Long.parseLong(elementCurs.getAttribute("codi"));
                final String descripcio = elementCurs.getAttribute("descripcio");
                Curs curs = cursManager.findById(codi);
                if (curs == null) {
                    curs = new Curs();
                    curs.setCodi(codi);
                }
                curs.setDescripcio(descripcio);
                cursManager.createOrUpdate(curs);
                //Por cada curso sacamos sus grupos
                NodeList nodeListGrups = elementCurs.getElementsByTagName("GRUP");
                for (int j = 0; j < nodeListGrups.getLength(); j++) {
                    Element elementGrup = (Element) nodeListGrups.item(j);


                    final long codiGrup = Long.parseLong(elementGrup.getAttribute("codi"));
                    final String nom = elementGrup.getAttribute("nom");
                    Grup grup = grupManager.findById(codiGrup);
                    if (grup == null) {
                        grup = new Grup();
                        grup.setCodi(codiGrup);
                    }
                    grup.setNom(nom);

                    //Sacamos todos los atributos de ese grupo para poder buscar todos sus tutores
                    NamedNodeMap nodeListAttr = elementGrup.getAttributes();
                    for (int k = 0; k < nodeListAttr.getLength(); k++) {
                        Attr attr = (Attr) nodeListAttr.item(k);
                        //Buscamos cada tutor en la BBDD y se lo asignamos al grupo
                        if (attr.getName().contains("tutor")) {
                            final String tutor = attr.getValue();
                            Professor professorTutor = professorManager.findById(tutor);
                            if (professorTutor != null) {
                                grup.addProfessor(professorTutor);
                            }
                        }
                    }
                    //Buscamos el curso por la ID y se lo asignamos al grupo
                    Curs grupCurso = cursManager.findById(codi);
                    grup.setCurs(grupCurso);
                    grupManager.createOrUpdate(grup);

                    NodeList nodeListAvaluacio = elementGrup.getElementsByTagName("AVALUACIO");

                    for (int k = 0; k < nodeListAvaluacio.getLength(); k++) {
                        Element elementAvaluacio = (Element) nodeListAvaluacio.item(k);
                        final Long codiAvaluacio = Long.parseLong(elementAvaluacio.getAttribute("codi"));
                        final String descripcioAvaluacio = elementAvaluacio.getAttribute("descripcio");
                        final LocalDate dataIniciAvaluacio = LocalDate.parse(elementAvaluacio.getAttribute("data_inici"));
                        final LocalDate dataFiAvaluacio = LocalDate.parse(elementAvaluacio.getAttribute("data_fi"));

                        Avaluacio avaluacio = avaluacioManager.findById(codiAvaluacio);
                        if (avaluacio == null) {
                            avaluacio = new Avaluacio();
                            avaluacio.setCodi(codi);
                        }
                        avaluacio.setDescripcio(descripcioAvaluacio);
                        avaluacio.setDataInici(dataIniciAvaluacio);
                        avaluacio.setDataFi(dataFiAvaluacio);

                        Grup grupAvaluacio = grupManager.findById(codiGrup);
                        avaluacio.setGrup(grupAvaluacio);

                        avaluacioManager.createOrUpdate(avaluacio);
                    }
                }
                NodeList nodeListNotes = elementCurs.getElementsByTagName("NOTA");
                Curs cursAvaluacio = cursManager.findById(codi);
                for (int j = 0; j < nodeListNotes.getLength(); j++) {
                    Element notaElement = (Element) nodeListNotes.item(j);
                    final long qualificacioNota = Long.parseLong(notaElement.getAttribute("qualificacio"));
                    final String descNota = notaElement.getAttribute("desc");
                    Nota nota = notaManager.findById(qualificacioNota);
                    if (nota == null) {
                        nota = new Nota();
                        nota.setQualificacio(qualificacioNota);
                    }
                    nota.setDescripcio(descNota);
                    nota.addCurs(cursAvaluacio);
                    notaManager.createOrUpdate(nota);
                }
                cursManager.createOrUpdate(cursAvaluacio);
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearSubmateria() {
        try {
            long startTime = System.currentTimeMillis();
            //Buscamos todas las submaterias y las añadimos a la BBDD
            final String findSubmateries = "CENTRE_EXPORT/SUBMATERIES/SUBMATERIA";
            final NodeList nodeListSubmateries = (NodeList) this.xPath.compile(findSubmateries).evaluate(this.xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListSubmateries.getLength(); i++) {
                Element elementSubmateria = (Element) nodeListSubmateries.item(i);

                final Long codi = Long.parseLong(elementSubmateria.getAttribute("codi"));
                final String descripcio = elementSubmateria.getAttribute("descripcio");
                final String curta = elementSubmateria.getAttribute("curta");
                final Long curs = Long.parseLong(elementSubmateria.getAttribute("curs"));
                Curs cursSubmateria = cursManager.findById(curs);

                Submateria submateria = submateriaManager.findById(codi);
                if (submateria == null) {
                    submateria = new Submateria();
                    submateria.setCodi(codi);
                }
                submateria.setDescripcio(descripcio);
                submateria.setCurta(curta);
                submateria.setCurs(cursSubmateria);
                submateriaManager.createOrUpdate(submateria);

            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearAlumno() {
        try {
            long startTime = System.currentTimeMillis();
            //Buscamos todos los alumnos y los añadimos a la BBDD
            final String findAlumnes = "CENTRE_EXPORT/ALUMNES/ALUMNE";
            final NodeList nodeListAlumnes = (NodeList) this.xPath.compile(findAlumnes).evaluate(this.xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListAlumnes.getLength(); i++) {
                Element elementAlumne = (Element) nodeListAlumnes.item(i);

                final String codi = elementAlumne.getAttribute("codi");
                final String nom = elementAlumne.getAttribute("nom");
                final String ap1 = elementAlumne.getAttribute("ap1");
                final String ap2 = elementAlumne.getAttribute("ap2");
                final Long expedient = Long.parseLong(elementAlumne.getAttribute("expedient"));
                final Long grup = Long.parseLong(elementAlumne.getAttribute("grup"));
                Alumne alumne = alumneManager.findById(codi);
                Grup grupAlumne = grupManager.findById(grup);
                if (alumne == null) {

                    alumne = new Alumne();
                    alumne.setCodi(codi);
                    alumne.setNom(nom);
                    alumne.setAp1(ap1);
                    alumne.setAp2(ap2);
                    alumne.setExpedient(expedient);
                    alumne.setGrup(grupAlumne);
                    alumne.setEliminat(false);

                } else {
                    if (alumne.getGrup() == null) {
                        alumne.setGrup(grupAlumne);
                    } else {
                        if (!grupAlumne.equals(alumne.getGrup())) {
                            alumne.setGrup(grupAlumne);
                        }
                    }
                    alumne.setEliminat(false);
                }
                alumneManager.createOrUpdate(alumne);
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearTutores() {
        try {
            long startTime = System.currentTimeMillis();
            //Buscamos todos los tutores y los añadimos a la BBDD
            final String findTutors = "CENTRE_EXPORT/TUTORS/TUTOR";
            final NodeList nodeListTutors = (NodeList) this.xPath.compile(findTutors).evaluate(this.xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListTutors.getLength(); i++) {
                Element elementTutor = (Element) nodeListTutors.item(i);

                final String codiAlumne = elementTutor.getAttribute("codiAlumne");
                Alumne alumneTutor = alumneManager.findById(codiAlumne);
                final String codiTutor = elementTutor.getAttribute("codiTutor");
                final String llinatge1 = elementTutor.getAttribute("llinatge1");
                final String llinatge2 = elementTutor.getAttribute("llinatge2");
                final String nomTutor = elementTutor.getAttribute("nom");
                final String relacio = elementTutor.getAttribute("relacio");

                Tutor tutor = tutorManager.findById(codiTutor);
                if (tutor == null) {
                    tutor = new Tutor();
                    tutor.setCodi(codiTutor);
                }
                tutor.setLlinatge1(llinatge1);
                tutor.setLlinatge2(llinatge2);
                tutor.setNom(nomTutor);
                tutorManager.createOrUpdate(tutor);


                TutorAlumne tutorAlumne = tutorAlumneManager.findById(new TutorAlumneID(alumneTutor.getCodi(), tutor.getCodi()));
                if (tutorAlumne == null) {
                    tutorAlumne = new TutorAlumne();
                    tutorAlumne.setTutor(tutor);
                    tutorAlumne.setAlumne(alumneTutor);
                }
                tutorAlumne.setRelacio(relacio);
                tutorAlumneManager.createOrUpdate(tutorAlumne);
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearSesionesProfesores() {
        try {
            long startTime = System.currentTimeMillis();
            //Buscamos todas las sesiones de profesores y las añadimos a la BBDD
            final String findHorariP = "CENTRE_EXPORT/HORARIP/SESSIO";
            final NodeList nodeListHorariP = (NodeList) this.xPath.compile(findHorariP).evaluate(this.xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListHorariP.getLength(); i++) {
                Element elementHorariP = (Element) nodeListHorariP.item(i);

                final String curs = elementHorariP.getAttribute("curs");
                Curs cursSessioP = null;
                if (curs != null && !curs.equals("")) {
                    cursSessioP = cursManager.findById(Long.parseLong(curs));
                }
                final String grup = elementHorariP.getAttribute("grup");
                Grup grupSessioP = null;
                if (grup != null && !grup.equals("")) {
                    grupSessioP = grupManager.findById(Long.parseLong(grup));
                }
                final String dia = elementHorariP.getAttribute("dia");
                Short diaSessioP = null;
                if (dia != null && !dia.equals("")) {
                    diaSessioP = Short.parseShort(dia);
                }
                final String hora = elementHorariP.getAttribute("hora");
                LocalTime horaSessioP = null;
                if (hora != null && !hora.equals("")) {
                    horaSessioP = DateParser.horaParser(hora);
                }
                final String durada = elementHorariP.getAttribute("durada");
                Integer duradaSessioP = null;
                if (durada != null && !durada.equals("")) {
                    duradaSessioP = Integer.parseInt(durada);
                }
                final String aulaCodi = elementHorariP.getAttribute("aula");
                Aula aulaSessioP = null;
                if (aulaCodi != null && !aulaCodi.equals("")) {
                    aulaSessioP = aulaManager.findById(Long.parseLong(aulaCodi));
                }
                final String submateria = elementHorariP.getAttribute("submateria");
                Submateria submateriaSessioP = null;
                if (submateria != null && !submateria.equals("")) {
                    submateriaSessioP = submateriaManager.findById(Long.parseLong(submateria));
                }

                final String activitatCodi = elementHorariP.getAttribute("activitat");
                Activitat activitatSessioP = null;
                if (activitatCodi != null && !activitatCodi.equals("")) {
                    activitatSessioP = activitatManager.findById(Long.parseLong(activitatCodi));
                }
                final String placa = elementHorariP.getAttribute("placa");
                Long placaSessioP = null;
                if (placa != null && !placa.equals("")) {
                    placaSessioP = Long.parseLong(placa);
                }

                final String codiProfessor = elementHorariP.getAttribute("professor");
                Professor professorSessioP = professorManager.findById(codiProfessor);

                Sessio sessio = new Sessio();
                sessio.setCurs(cursSessioP);
                sessio.setGrup(grupSessioP);
                sessio.setHora(horaSessioP);
                sessio.setDia(diaSessioP);
                sessio.setDurada(duradaSessioP);
                sessio.setAula(aulaSessioP);
                sessio.setSubmateria(submateriaSessioP);
                sessio.setActivitat(activitatSessioP);
                sessio.setPlaca(placaSessioP);
                sessio.setProfessor(professorSessioP);

                sessioManager.createOrUpdate(sessio);
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearSesionesAlumnos() {
        try {
            long startTime = System.currentTimeMillis();
            //Buscamos todas las sesiones de cada alumno y las añadimos a la BBDD
            final String findHorariA = "CENTRE_EXPORT/HORARIA/SESSIO";
            final NodeList nodeListHorariA = (NodeList) this.xPath.compile(findHorariA).evaluate(this.xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListHorariA.getLength(); i++) {
                Element elementHorariA = (Element) nodeListHorariA.item(i);

                final String dia = elementHorariA.getAttribute("dia");
                Short diaSessioA = null;
                if (dia != null && !dia.equals("")) {
                    diaSessioA = Short.parseShort(dia);
                }
                final String hora = elementHorariA.getAttribute("hora");
                LocalTime horaSessioA = null;
                if (hora != null && !hora.equals("")) {
                    horaSessioA = DateParser.horaParser(hora);
                }
                final String durada = elementHorariA.getAttribute("durada");
                Integer duradaSessioA = null;
                if (durada != null && !durada.equals("")) {
                    duradaSessioA = Integer.parseInt(durada);
                }
                final String aulaCodi = elementHorariA.getAttribute("aula");
                Aula aulaSessioA = null;
                if (aulaCodi != null && !aulaCodi.equals("")) {
                    aulaSessioA = aulaManager.findById(Long.parseLong(aulaCodi));
                }
                final String submateria = elementHorariA.getAttribute("submateria");
                Submateria submateriaSessioA = null;
                if (submateria != null && !submateria.equals("")) {
                    submateriaSessioA = submateriaManager.findById(Long.parseLong(submateria));
                }
                final String codiAlumne = elementHorariA.getAttribute("alumne");
                Alumne alumneSessioA = alumneManager.findById(codiAlumne);

                Sessio sessio = new Sessio();
                sessio.setHora(horaSessioA);
                sessio.setDia(diaSessioA);
                sessio.setDurada(duradaSessioA);
                sessio.setAula(aulaSessioA);
                sessio.setSubmateria(submateriaSessioA);
                sessio.setAlumne(alumneSessioA);

                sessioManager.createOrUpdate(sessio);
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearSesionesGrupos() {
        try {
            long startTime = System.currentTimeMillis();
            //Buscamos todas las sesiones de cada alumno y las añadimos a la BBDD
            final String findHorariG = "CENTRE_EXPORT/HORARIG/SESSIO";
            final NodeList nodeListHorariG = (NodeList) this.xPath.compile(findHorariG).evaluate(this.xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeListHorariG.getLength(); i++) {
                Element elementHorariG = (Element) nodeListHorariG.item(i);

                final String curs = elementHorariG.getAttribute("curs");
                Curs cursSessioG = null;
                if (curs != null && !curs.equals("")) {
                    cursSessioG = cursManager.findById(Long.parseLong(curs));
                }
                final String grup = elementHorariG.getAttribute("grup");
                Grup grupSessioG = grupManager.findById(Long.parseLong(grup));

                final String dia = elementHorariG.getAttribute("dia");
                Short diaSessioG = null;
                if (dia != null && !dia.equals("")) {
                    diaSessioG = Short.parseShort(dia);
                }
                final String hora = elementHorariG.getAttribute("hora");
                LocalTime horaSessioG = null;
                if (hora != null && !hora.equals("")) {
                    horaSessioG = DateParser.horaParser(hora);
                }
                final String durada = elementHorariG.getAttribute("durada");
                Integer duradaSessioG = null;
                if (durada != null && !durada.equals("")) {
                    duradaSessioG = Integer.parseInt(durada);
                }
                final String aulaCodi = elementHorariG.getAttribute("aula");
                Aula aulaSessioG = null;
                if (aulaCodi != null && !aulaCodi.equals("")) {
                    aulaSessioG = aulaManager.findById(Long.parseLong(aulaCodi));
                }
                final String submateria = elementHorariG.getAttribute("submateria");
                Submateria submateriaSessioG = null;
                if (submateria != null && !submateria.equals("")) {
                    submateriaSessioG = submateriaManager.findById(Long.parseLong(submateria));
                }
                final String activitatCodi = elementHorariG.getAttribute("activitat");
                Activitat activitatSessioG = null;
                if (activitatCodi != null && !activitatCodi.equals("")) {
                    activitatSessioG = activitatManager.findById(Long.parseLong(activitatCodi));
                }

                Sessio sessio = new Sessio();
                sessio.setGrup(grupSessioG);
                sessio.setCurs(cursSessioG);
                sessio.setHora(horaSessioG);
                sessio.setDia(diaSessioG);
                sessio.setDurada(duradaSessioG);
                sessio.setAula(aulaSessioG);
                sessio.setSubmateria(submateriaSessioG);
                sessio.setActivitat(activitatSessioG);

                sessioManager.createOrUpdate(sessio);
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setEliminitatAll() {
        List<Professor> professors = professorManager.findAll();
        List<Alumne> alumnes = alumneManager.findAll();
        for (Professor professor : professors) {
            professor.setEliminat(true);
            professorManager.createOrUpdate(professor);
        }
        for (Alumne alumne : alumnes) {
            alumne.setEliminat(true);
            alumneManager.createOrUpdate(alumne);
        }
    }

    public void purgeTaules() {
        System.out.println("Purgant taules:");
        //departamentManager.deleteAll();
        //aulaManager.deleteAll();
        //activitatManager.deleteAll();

        grupManager.deleteAll();
        avaluacioManager.deleteAll();
        cursManager.deleteAll();
        submateriaManager.deleteAll();
        tutorAlumneManager.deleteAll();
        tutorManager.deleteAll();

    }

    public void insertData(File file) {
        sessioManager.deleteAll();
        System.out.println("Eliminat true a todos los alumnos y profesores");
        setEliminitatAll();

        System.out.println("Insert Data");
        this.prepare(file);
        long startTime = System.currentTimeMillis();
        System.out.println("DEPARTAMENTS");
        crearDepartaments();
        System.out.println("AULES");
        crearAules();
        System.out.println("ACTIVITATS");
        crearActivitats();
        System.out.println("PROFESSORS");
        crearProfessors();
        System.out.println("CURSOS GRUPOS AVALUACIONES NOTAS");
        crearCursosGruposAvaluacionesNotas();
        System.out.println("SUBMATERIA");
        crearSubmateria();
        System.out.println("ALUMNO");
        crearAlumno();
        System.out.println("TUTORES");
        crearTutores();
        System.out.println("SESIONES PROFESORES");
        crearSesionesProfesores();
        System.out.println("SESSIONES ALUMNOS");
        crearSesionesAlumnos();
        System.out.println("SESSIONES GRUPOS");
        crearSesionesGrupos();
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println(endTime);
        System.out.println("Ha acabado");
    }
}
