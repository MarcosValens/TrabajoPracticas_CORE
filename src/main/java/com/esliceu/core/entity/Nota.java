package com.esliceu.core.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "nota")
public class Nota implements Serializable {

    @Id
    @Column(name = "qualificacio")
    private Long qualificacio;

    @Column(name = "descripcio", length = 300)
    private String descripcio;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Curs> curs;

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

    public List<Curs> getCurs() {
        return curs;
    }

    public void setCurs(List<Curs> curs) {
        this.curs = curs;
    }
}
