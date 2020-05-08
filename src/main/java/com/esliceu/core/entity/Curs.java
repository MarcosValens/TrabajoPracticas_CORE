package com.esliceu.core.entity;

import javax.persistence.*;

@Entity
@Table(name = "curs")
public class Curs {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "descripcio", length = 300)
    private String descripcio;

    @Column(name = "grup")
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Grup grup;

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

    public Grup getGrup() {
        return grup;
    }

    public void setGrup(Grup grup) {
        this.grup = grup;
    }
}
