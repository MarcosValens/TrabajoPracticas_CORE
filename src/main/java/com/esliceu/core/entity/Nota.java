package com.esliceu.core.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "nota")
public class Nota implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codi")
    private Long codi;

    @Column(name = "qualificacio")
    private Long qualificacio;

    @Column(name = "descripcio", length = 300)
    private String descripcio;

    public Nota() {
    }

    public Long getCodi() {
        return codi;
    }

    public void setCodi(Long codi) {
        this.codi = codi;
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
}
