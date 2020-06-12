package com.esliceu.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "usuariapp_professor")
@IdClass(UsuariAppProfessorID.class)
public class UsuariAppProfessor implements Serializable {
    @Id
    @JoinColumn(name = "professor_codi")
    @ManyToOne
    private Professor professor;

    @JoinColumn(name = "usuariapp_email")
    @ManyToOne
    private UsuariApp usuariApp;

    @Id
    @Column(name = "data")
    @Type(type="date")
    LocalDate data;

    public UsuariAppProfessor() {
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public UsuariApp getUsuariApp() {
        return usuariApp;
    }

    public void setUsuariApp(UsuariApp usuariApp) {
        this.usuariApp = usuariApp;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}