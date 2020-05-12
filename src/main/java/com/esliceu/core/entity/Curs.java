package com.esliceu.core.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "curs")
public class Curs implements Serializable {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "descripcio", length = 300)
    private String descripcio;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "notes_cursos",
    joinColumns = @JoinColumn(name = "curs_codi"),
    inverseJoinColumns = @JoinColumn(name = "nota_qualificacio"))
    private List<Nota> notes = new ArrayList<>();

    public Curs() {
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

    public List<Nota> getNotes() {
        return notes;
    }

    public void setNotes(List<Nota> notes) {
        this.notes = notes;
    }

    public void addNota(Nota nota){
        notes.add(nota);
        nota.getCursos().add(this);
    }
}
