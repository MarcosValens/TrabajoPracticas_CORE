package com.esliceu.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "grup")
public class Grup implements Serializable {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "nom", length = 20)
    private String nom;

    @JoinColumn(name = "curso_codi")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Curs curs;

    @ManyToMany(mappedBy = "grups")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Professor> professors = new ArrayList<>();

    @OneToMany(mappedBy = "grup")
    private List<Avaluacio> avaluacions;

    @JoinColumn(name = "tutor1")
    @ManyToOne()
    private Professor tutor1;

    @JoinColumn(name = "tutor2")
    @ManyToOne()
    private Professor tutor2;

    @JoinColumn(name = "tutor3")
    @ManyToOne()
    private Professor tutor3;


    public Grup() {
    }

    public Long getCodi() {
        return codi;
    }

    public void setCodi(Long codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Curs getCurs() {
        return curs;
    }

    public void setCurs(Curs curs) {
        this.curs = curs;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    public void addProfessor(Professor professor) {
        professors.add(professor);
        professor.getGrups().add(this);
    }

    public List<Avaluacio> getAvaluacions() {
        return avaluacions;
    }

    public void setAvaluacions(List<Avaluacio> avaluacions) {
        this.avaluacions = avaluacions;
    }

    public Professor getTutor1() {
        return tutor1;
    }

    public void setTutor1(Professor tutor1) {
        this.tutor1 = tutor1;
    }

    public Professor getTutor2() {
        return tutor2;
    }

    public void setTutor2(Professor tutor2) {
        this.tutor2 = tutor2;
    }

    public Professor getTutor3() {
        return tutor3;
    }

    public void setTutor3(Professor tutor3) {
        this.tutor3 = tutor3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grup grup = (Grup) o;
        return Objects.equals(codi, grup.codi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codi);
    }
}

