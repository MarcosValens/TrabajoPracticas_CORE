package com.esliceu.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "curs")
public class Curs {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "descripcio", length = 300)
    private String descripcio;

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
}
