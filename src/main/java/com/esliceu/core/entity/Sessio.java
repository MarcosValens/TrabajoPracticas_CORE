package com.esliceu.core.entity;

public class Sessio {
    private String professor;
    private String alumne;
    private String curs;
    private String grup;
    private int dia;
    private int hora;
    private int durada;
    private long aula;
    private long submateria;
    private long activitat;
    private long placa;

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getAlumne() {
        return alumne;
    }

    public void setAlumne(String alumne) {
        this.alumne = alumne;
    }

    public String getCurs() {
        return curs;
    }

    public void setCurs(String curs) {
        this.curs = curs;
    }

    public String getGrup() {
        return grup;
    }

    public void setGrup(String grup) {
        this.grup = grup;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getDurada() {
        return durada;
    }

    public void setDurada(int durada) {
        this.durada = durada;
    }

    public long getAula() {
        return aula;
    }

    public void setAula(long aula) {
        this.aula = aula;
    }

    public long getSubmateria() {
        return submateria;
    }

    public void setSubmateria(long submateria) {
        this.submateria = submateria;
    }

    public long getActivitat() {
        return activitat;
    }

    public void setActivitat(long activitat) {
        this.activitat = activitat;
    }

    public long getPlaca() {
        return placa;
    }

    public void setPlaca(long placa) {
        this.placa = placa;
    }
}
