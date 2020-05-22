package com.esliceu.core.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class UsuariAppProfessorID implements Serializable {
    private String professor;
    private LocalDate data;

    public UsuariAppProfessorID(String professor, LocalDate data) {
        this.professor = professor;
        this.data = data;
    }

    public UsuariAppProfessorID() {
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
