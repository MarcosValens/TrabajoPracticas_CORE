package com.esliceu.core.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "professor")
public class Professor implements Serializable {

    @Id
    @Column(name = "codi")
    private String codi;

    @Column(name = "nom", length = 300)
    private String nom;

    @Column(name = "ap1", length = 300)
    private String ap1;

    @Column(name = "ap2", length = 300)
    private String ap2;

    @Column(name = "username", length = 300)
    private String username;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departament")
    private Departament departament;

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "professor_grup",
    joinColumns = @JoinColumn(name = "professor_codi"),
    inverseJoinColumns = @JoinColumn(name = "grup_codi"))
    private List<Grup> grups = new ArrayList<>();

    public Professor() {
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
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

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    public List<Grup> getGrups() {
        return grups;
    }

    public void setGrups(List<Grup> grups) {
        this.grups = grups;
    }

    public void addGrup(Grup grup){
        grups.add(grup);
        grup.getProfessors().add(this);
    }
}
