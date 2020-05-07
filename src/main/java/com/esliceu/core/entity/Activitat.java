package com.esliceu.core.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "activitat")
public class Activitat implements Serializable {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "descripcio", length = 300)
    private String descripcio;

    @Column(name = "curta", length = 100)
    private String curta;

    public Activitat() {
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
}
