package com.esliceu.core.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class UsuariAppAlumneID implements Serializable {
    private String alumne;
    private LocalDate data;

    public UsuariAppAlumneID(String alumne, LocalDate data) {
        this.alumne = alumne;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
