package com.esliceu.core.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
public class Sessio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "horari")
    private String horari;

    @Column(name = "professor", length = 300)
    private String professor;

    @Column(name = "alumne", length = 100)
    private String alumne;

    @Column(name = "curs", length = 300)
    private String curs;

    @Column(name = "grup", length = 100)
    private String grup;

    @Column(name = "dia")
    private int dia;

    @Column(name = "hora")
    private Time hora;

    @Column(name = "durada")
    private int durada;

    @Column(name = "aula")
    private Long aula;

    @Column(name = "submateria")
    private Long submateria;

    @Column(name = "activitat")
    private Long activitat;

    @Column(name = "placa")
    private Long placa;

    public Sessio() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHorari() {
        return horari;
    }

    public void setHorari(String horari) {
        this.horari = horari;
    }

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

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getDurada() {
        return durada;
    }

    public void setDurada(int durada) {
        this.durada = durada;
    }

    public Long getAula() {
        return aula;
    }

    public void setAula(Long aula) {
        this.aula = aula;
    }

    public Long getSubmateria() {
        return submateria;
    }

    public void setSubmateria(Long submateria) {
        this.submateria = submateria;
    }

    public Long getActivitat() {
        return activitat;
    }

    public void setActivitat(Long activitat) {
        this.activitat = activitat;
    }

    public Long getPlaca() {
        return placa;
    }

    public void setPlaca(Long placa) {
        this.placa = placa;
    }
}
