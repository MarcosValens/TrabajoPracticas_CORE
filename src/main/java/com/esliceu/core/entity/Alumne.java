package com.esliceu.core.entity;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "alumno")
public class Alumne implements Serializable {

    @Id
    @Column(name = "codi", length = 100)
    private String codi;

    @Column(name = "nom", length = 100)
    private String nom;

    @Column(name = "ap1", length = 100)
    private String ap1;

    @Column(name = "ap2", length = 100)
    private String ap2;

    @Column(name = "expedient")
    private Long expedient;

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

    @JoinColumn(name = "grup_codi")
    @ManyToOne(cascade = CascadeType.ALL)
    private Grup grup;

    @OneToMany(mappedBy = "alumne", orphanRemoval = true)
    @JsonIgnore
    private List<TutorAlumne> tutorsAlumnes;

    @Transient
    private String imatge;

    public Alumne() {
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

    public Long getExpedient() {
        return expedient;
    }

    public void setExpedient(Long expedient) {
        this.expedient = expedient;
    }

    public Grup getGrup() {
        return grup;
    }

    public void setGrup(Grup grup) {
        this.grup = grup;
    }

    public List<TutorAlumne> getTutorsAlumnes() {
        return tutorsAlumnes;
    }

    public void setTutorsAlumnes(List<TutorAlumne> tutorsAlumnes) {
        this.tutorsAlumnes = tutorsAlumnes;
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

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    public String getImatge() {
        return imatge;
    }
}
