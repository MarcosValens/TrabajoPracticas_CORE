package com.esliceu.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "sessio")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Sessio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "professor_codi")
    @ManyToOne
    private Professor professor;

    @JoinColumn(name = "alumno_codi")
    @ManyToOne
    private Alumne alumne;

    @JoinColumn(name = "curs_codi")
    @ManyToOne
    private Curs curs;

    @JoinColumn(name = "grup_codi")
    @ManyToOne
    private Grup grup;

    @Column(name = "dia")
    private Short dia;

    @Column(name = "hora")
    private LocalTime hora;

    @Column(name = "durada")
    private Integer durada;

    @JoinColumn(name = "aula_codi")
    @ManyToOne
    private Aula aula;

    @JoinColumn(name = "submateria_codi")
    @ManyToOne
    private Submateria submateria;

    @JoinColumn(name = "activitat_codi")
    @ManyToOne
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

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
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

    public Short getDia() {
        return dia;
    }

    public void setDia(Short dia) {
        this.dia = dia;
    }

    public Integer getDurada() {
        return durada;
    }

    public void setDurada(Integer durada) {
        this.durada = durada;
    }
}
