package com.esliceu.core.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "sessio")
public class Sessio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "horari")
    private String horari;

    @Column(name = "professor")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Professor professor;

    @Column(name = "alumne")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Alumne alumne;

    @Column(name = "curs")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Curs curs;

    @Column(name = "grup")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Grup grup;

    @Column(name = "dia")
    private int dia;

    @Column(name = "hora")
    private Time hora;

    @Column(name = "durada")
    private int durada;

    @Column(name = "aula")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Aula aula;

    @Column(name = "submateria")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Submateria submateria;

    @Column(name = "activitat")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Activitat activitat;

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

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Alumne getAlumne() {
        return alumne;
    }

    public void setAlumne(Alumne alumne) {
        this.alumne = alumne;
    }

    public Curs getCurs() {
        return curs;
    }

    public void setCurs(Curs curs) {
        this.curs = curs;
    }

    public Grup getGrup() {
        return grup;
    }

    public void setGrup(Grup grup) {
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

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Submateria getSubmateria() {
        return submateria;
    }

    public void setSubmateria(Submateria submateria) {
        this.submateria = submateria;
    }

    public Activitat getActivitat() {
        return activitat;
    }

    public void setActivitat(Activitat activitat) {
        this.activitat = activitat;
    }

    public Long getPlaca() {
        return placa;
    }

    public void setPlaca(Long placa) {
        this.placa = placa;
    }
}
