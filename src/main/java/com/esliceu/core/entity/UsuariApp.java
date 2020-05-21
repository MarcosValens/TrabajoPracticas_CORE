package com.esliceu.core.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usuariapp")
public class UsuariApp implements Serializable {

    @Id
    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @OneToOne(mappedBy = "usuariApp")
    private Professor professor;

    @Column(name = "contrasenya", length = 200, nullable = false)
    private String contrasenya;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
}
