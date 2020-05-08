package com.esliceu.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "professor")
public class Professor implements Serializable {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "nom", length = 300)
    private String nom;

    @Column(name = "ap1", length = 300)
    private String ap1;

    @Column(name = "ap2", length = 300)
    private String ap2;

    @Column(name = "username", length = 300)
    private String username;

    @Column(name = "departament", length = 300)
    private Long departament;

    public Professor() {
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

    public String getAp1() {
        return ap1;
    }

    public void setAp1(String ap1) {
        this.ap1 = ap1;
    }

    public String getAp2() {
        return ap2;
    }

    public void setAp2(String ap2) {
        this.ap2 = ap2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getDepartament() {
        return departament;
    }

    public void setDepartament(Long departament) {
        this.departament = departament;
    }
}
