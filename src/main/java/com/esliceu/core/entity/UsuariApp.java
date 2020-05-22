package com.esliceu.core.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usuariapp")
public class UsuariApp implements Serializable {

    @Id
    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Professor professor;

    @Column(name = "contrasenya", length = 200)
    private String contrasenya;

    @Column(name = "isAdmin")
    private boolean isAdmin;

    @Column(name = "isCuiner")
    private boolean isCuiner;

    @Column(name = "isProfessor")
    private boolean isProfessor;

    @Column(name = "isMonitor")
    private boolean isMonitor;

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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isCuiner() {
        return isCuiner;
    }

    public void setCuiner(boolean cuiner) {
        isCuiner = cuiner;
    }

    public boolean isProfessor() {
        return isProfessor;
    }

    public void setProfessor(boolean professor) {
        isProfessor = professor;
    }
}
