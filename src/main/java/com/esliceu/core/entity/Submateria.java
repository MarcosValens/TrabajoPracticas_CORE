package com.esliceu.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "submateria")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codi")
public class Submateria implements Serializable {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "descripcio", length = 300)
    private String descripcio;

    @Column(name = "curta", length = 100)
    private String curta;

    @JoinColumn(name = "curso_codi")
    @ManyToOne(cascade = CascadeType.ALL)
    private Curs curs;

    public Submateria() {
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

    public String getCurta() {
        return curta;
    }

    public void setCurta(String curta) {
        this.curta = curta;
    }

    public Curs getCurs() {
        return curs;
    }

    public void setCurs(Curs curs) {
        this.curs = curs;
    }

    @Override
    public String toString() {
        return "Submateria{" +
                "codi=" + codi +
                ", descripcio='" + descripcio + '\'' +
                ", curta='" + curta + '\'' +
                ", curs=" + curs +
                '}';
    }
}
