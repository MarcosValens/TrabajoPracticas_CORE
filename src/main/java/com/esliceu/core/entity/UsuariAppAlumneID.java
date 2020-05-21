package com.esliceu.core.entity;

import java.io.Serializable;

public class UsuariAppAlumneID implements Serializable {
    private String alumne;
    private String usuariApp;

    public UsuariAppAlumneID(String alumne, String usuariApp) {
        this.alumne = alumne;
        this.usuariApp = usuariApp;
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
}
