package com.esliceu.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "rol", nullable = false, columnDefinition = "tinyint")
    private Rol rol;

    @ManyToMany(mappedBy = "rols")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<UsuariApp> usuariAppList;

    public Rol() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<UsuariApp> getUsuariAppList() {
        return usuariAppList;
    }

    public void setUsuariAppList(List<UsuariApp> usuariAppList) {
        this.usuariAppList = usuariAppList;
    }
}
