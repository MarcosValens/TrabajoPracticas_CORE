package com.esliceu.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "activitat")
public class Tutor implements Serializable {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "nom", length = 300)
    private String nom;

    @Column(name = "llinatge1", length = 300)
    private String llinatge1;

    @Column(name = "llinatge2", length = 300)
    private String llinatge2;

    @Column(name = "relacio", length = 50)
    private String relacio;

    private String alumnoId;

    public Tutor() {
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

    public String getLlinatge1() {
        return llinatge1;
    }

    public void setLlinatge1(String llinatge1) {
        this.llinatge1 = llinatge1;
    }

    public String getLlinatge2() {
        return llinatge2;
    }

    public void setLlinatge2(String llinatge2) {
        this.llinatge2 = llinatge2;
    }

    public String getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(String alumnoId) {
        this.alumnoId = alumnoId;
    }

    public String getRelacio() {
        return relacio;
    }

    public void setRelacio(String relacio) {
        this.relacio = relacio;
    }
}
