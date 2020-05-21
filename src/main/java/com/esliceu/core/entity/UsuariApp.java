package com.esliceu.core.entity;

import com.esliceu.core.entity.enums.RolEnum;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "usuariaApp_rol", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private List<Rol> rols;

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

    public List<UsuariApp> getUsuariAppList() {
        return usuariAppList;
    }

    public void setUsuariAppList(List<UsuariApp> usuariAppList) {
        this.usuariAppList = usuariAppList;
    }
}
