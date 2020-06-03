package com.esliceu.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tutor_legal")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codi")
public class Tutor implements Serializable {

    @Id
    @Column(name = "codi")
    private String codi;

    @Column(name = "nom", length = 100)
    private String nom;

    @Column(name = "llinatge1", length = 100)
    private String llinatge1;

    @Column(name = "llinatge2", length = 100)
    private String llinatge2;

    @OneToMany(mappedBy = "tutor", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<TutorAlumne> tutorsAlumnes;

    public Tutor() {
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

    public List<TutorAlumne> getTutorsAlumnes() {
        return tutorsAlumnes;
    }

    public void setTutorsAlumnes(List<TutorAlumne> tutorsAlumnes) {
        this.tutorsAlumnes = tutorsAlumnes;
    }
}
