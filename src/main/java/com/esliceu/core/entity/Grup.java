package com.esliceu.core.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "grup")
public class Grup implements Serializable {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "nom", length = 100)
    private String nom;

    @Column(name = "curs")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Curs curs;

    @Column(name = "avalucacio")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Avaluacio avaluacio;

    @ManyToMany()
    private List<Professor> professors;

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

    public Avaluacio getAvaluacio() {
        return avaluacio;
    }

    public void setAvaluacio(Avaluacio avaluacio) {
        this.avaluacio = avaluacio;
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
}
