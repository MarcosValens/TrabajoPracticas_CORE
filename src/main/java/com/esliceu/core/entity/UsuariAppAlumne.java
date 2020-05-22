package com.esliceu.core.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "usuariapp_alumno")
@IdClass(UsuariAppAlumneID.class)
public class UsuariAppAlumne implements Serializable {
    @Id
    @JoinColumn(name = "alumno_codi")
    @ManyToOne
    private Alumne alumne;

    @JoinColumn(name = "usuariapp_email")
    @ManyToOne
    private UsuariApp usuariApp;

    @Id
    @Column(name = "data")
    LocalDate data;

    public UsuariAppAlumne() {
    }

    public Alumne getAlumne() {
        return alumne;
    }

    public void setAlumne(Alumne alumne) {
        this.alumne = alumne;
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
