package com.esliceu.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "usuariapp")
public class UsuariApp implements Serializable {

    @Id
    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @OneToOne(mappedBy = "usuariApp")
    private Professor professor;

    @JsonIgnore
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

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido1")
    private String apellido1;

    @Column(name = "apellido2")
    private String apellido2;

    public List<String> getRols() {
        List<String> rols = new ArrayList<>();
        Field[] attributes = UsuariApp.class.getDeclaredFields();
        for (Field attribute : attributes) {
            if (attribute.toString().contains("is")) {
                String rol[] = attribute.toString().split("is");
                rols.add(rol[1]);
            }
        }
        return rols;
    }

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

    public void setIsProfessor(boolean professor) {
        isProfessor = professor;
    }

    public boolean isMonitor() {
        return isMonitor;
    }

    public void setMonitor(boolean monitor) {
        isMonitor = monitor;
    }

    public void setProfessor(boolean professor) {
        isProfessor = professor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
}
