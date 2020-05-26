package com.esliceu.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "alumno")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codi")
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

    @JoinColumn(name = "grup_codi")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Grup grup;

    @OneToMany(mappedBy = "alumne")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<TutorAlumne> tutorsAlumnes;

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
}
