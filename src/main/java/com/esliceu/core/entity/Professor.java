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
@Table(name = "professor")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codi")
public class Professor implements Serializable {

    @Id
    @Column(name = "codi", length = 100)
    private String codi;

    @Column(name = "nom", length = 100)
    private String nom;

    @Column(name = "ap1", length = 100)
    private String ap1;

    @Column(name = "ap2", length = 100)
    private String ap2;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "eliminat")
    private boolean eliminat;

    @Column(name = "loginldap")
    private String LoginLDAP;

    @Column(name = "isnew", columnDefinition = "bit default 1")
    private boolean isNew;

    @Column(name = "passwordldap")
    private String passwordLDAP;

    @Column(name = "uidnumberldap")
    private Long uidNumberLDAP;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private UsuariApp usuariApp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departament_codi")
    private Departament departament;

    @ManyToMany()
    @JsonIgnore
    @JoinTable(name = "grup_professor",
            joinColumns = @JoinColumn(name = "professor_codi"),
            inverseJoinColumns = @JoinColumn(name = "grup_codi"))
    private List<Grup> grups = new ArrayList<>();

    public Professor() {
    }

    public Professor(String codi, String nom, String ap1, String ap2) {
        this.codi = codi;
        this.nom = nom;
        this.ap1 = ap1;
        this.ap2 = ap2;
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

    public UsuariApp getUsuariApp() {
        return usuariApp;
    }

    public void setUsuariApp(UsuariApp usuariApp) {
        this.usuariApp = usuariApp;
    }

    public void setGrups(List<Grup> grups) {
        this.grups = grups;
    }

    public void addGrup(Grup grup) {
        grups.add(grup);
        grup.getProfessors().add(this);
    }

    public boolean isEliminat() {
        return eliminat;
    }

    public void setEliminat(boolean eliminat) {
        this.eliminat = eliminat;
    }

    public String getLoginLDAP() { return LoginLDAP; }

    public void setLoginLDAP(String loginLDAP) { LoginLDAP = loginLDAP; }

    public boolean isNew() { return isNew; }

    public void setNew(boolean aNew) { isNew = aNew; }

    public String getPasswordLDAP() { return passwordLDAP; }

    public void setPasswordLDAP(String passwordLDAP) { this.passwordLDAP = passwordLDAP; }

    public Long getUidNumberLDAP() { return uidNumberLDAP; }

    public void setUidNumberLDAP(Long uidNumberLDAP) { this.uidNumberLDAP = uidNumberLDAP; }
}
