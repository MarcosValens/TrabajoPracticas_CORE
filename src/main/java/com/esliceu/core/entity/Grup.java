package com.esliceu.core.entity;

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

    @Column(name = "nom", length = 100)
    private String nom;

    @JoinColumn(name = "curs")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Curs curs;

    @ManyToMany(mappedBy = "grups")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Professor> professors = new ArrayList<>();

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

    public void addProfessor(Professor professor){
        professors.add(professor);
        professor.getGrups().add(this);
    }
}
