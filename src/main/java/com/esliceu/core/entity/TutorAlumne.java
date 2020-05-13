package com.esliceu.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "alumne_tutor")
@IdClass(TutorAlumneID.class)
public class TutorAlumne implements Serializable {

    @Id
    @JoinColumn(name = "alumne")
    @ManyToOne
    @JsonBackReference
    private Alumne alumne;

    @Id
    @JoinColumn(name = "tutor")
    @ManyToOne
    private Tutor tutor;

    @Column(name = "relacio", length = 100)
    private String relacio;

    public TutorAlumne() {
    }

    public Alumne getAlumne() {
        return alumne;
    }

    public void setAlumne(Alumne alumne) {
        this.alumne = alumne;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public String getRelacio() {
        return relacio;
    }

    public void setRelacio(String relacio) {
        this.relacio = relacio;
    }
}
