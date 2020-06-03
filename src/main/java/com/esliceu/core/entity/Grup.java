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

    public Grup() {
    }

    /*@PreRemove
    public void preremove() {
        this.curs = null;
        this.avaluacions = null;
        if (this.professors == null) {
            return;
        }
        for (Professor professor : professors) {
            professor.getGrups().remove(this);
        }
        this.professors = null;
    }*/

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
}
