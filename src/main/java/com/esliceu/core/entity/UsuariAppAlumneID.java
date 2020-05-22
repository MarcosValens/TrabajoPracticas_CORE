package com.esliceu.core.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class UsuariAppAlumneID implements Serializable {
    private String alumne;
    private String usuariApp;
    private LocalDate data;

    public UsuariAppAlumneID(String alumne, String usuariApp, LocalDate data) {
        this.alumne = alumne;
        this.usuariApp = usuariApp;
        this.data = data;
    }

    public UsuariAppAlumneID() {
    }

    public String getAlumne() {
        return alumne;
    }

    public void setAlumne(String alumne) {
        this.alumne = alumne;
    }

    public String getUsuariApp() {
        return usuariApp;
    }

    public void setUsuariApp(String usuariApp) {
        this.usuariApp = usuariApp;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
