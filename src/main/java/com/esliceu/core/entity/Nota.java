package com.esliceu.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nota")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "qualificacio")
public class Nota implements Serializable {

    @Id
    @Column(name = "qualificacio")
    private Long qualificacio;

    @Column(name = "descripcio", length = 50)
    private String descripcio;


    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "notes")
    private List<Curs> cursos = new ArrayList<>();

    public Nota() {
    }

    public Long getQualificacio() {
        return qualificacio;
    }

    public void setQualificacio(Long qualificacio) {
        this.qualificacio = qualificacio;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public List<Curs> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curs> cursos) {
        this.cursos = cursos;
    }

    public void addCurs(Curs curs){
        cursos.add(curs);
        curs.getNotes().add(this);
    }
}
