package com.esliceu.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "grup")
public class Grup implements Serializable {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "nom", length = 100)
    private String nom;


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
}
