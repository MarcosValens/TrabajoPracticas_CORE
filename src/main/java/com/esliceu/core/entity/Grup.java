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

    @Column(name = "avalucacio")
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Avaluacio avaluacio;

    @Column(name = "alumne")
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Alumne alumne;

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

    public Alumne getAlumne() {
        return alumne;
    }

    public void setAlumne(Alumne alumne) {
        this.alumne = alumne;
    }
}
