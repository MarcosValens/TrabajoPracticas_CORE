package com.esliceu.core.entity;

import javax.persistence.*;

@Entity
@Table(name = "alumne")
public class Alumne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codi")
    private Long codi;

    @Column(name = "nom", length = 100)
    private String nom;

    @Column(name = "ap1", length = 100)
    private String ap1;

    @Column(name = "ap2", length = 100)
    private String ap2;

    @Column(name = "expedient")
    private Long expedient;

    public Alumne() {
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

    public Long getExpedient() {
        return expedient;
    }

    public void setExpedient(Long expedient) {
        this.expedient = expedient;
    }
}
