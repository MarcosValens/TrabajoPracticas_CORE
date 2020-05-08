package com.esliceu.core.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "departament")
public class Departament implements Serializable {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "descripcio", length = 300)
    private String descripcio;

    @Column(name = "professor")
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Professor professor;

    public Departament() {
    }

    public Long getCodi() {
        return codi;
    }

    public void setCodi(Long codi) {
        this.codi = codi;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
