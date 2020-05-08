package com.esliceu.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "submateria")
public class Submateria implements Serializable {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "descripcio", length = 300)
    private String descripcio;

    @Column(name = "curta", length = 100)
    private String curta;

    @Column(name = "curs")
    private Long curs;

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

    public Long getCurs() {
        return curs;
    }

    public void setCurs(Long submateria) {
        this.curs = submateria;
    }
}
