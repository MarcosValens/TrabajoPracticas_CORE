package com.esliceu.core.entity;

import java.io.Serializable;

public class UsuariAppProfessorID implements Serializable {
    private String professor;
    private String usuariApp;

    public UsuariAppProfessorID(String professor, String usuariApp) {
        this.professor = professor;
        this.usuariApp = usuariApp;
    }

    public UsuariAppProfessorID() {
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getUsuariApp() {
        return usuariApp;
    }

    public void setUsuariApp(String usuariApp) {
        this.usuariApp = usuariApp;
    }
}
