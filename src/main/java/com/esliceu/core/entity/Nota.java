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

    @Column(name = "curs")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Curs curs;

    @Column(name = "submateria")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Submateria submateria;

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

    public Curs getCurs() {
        return curs;
    }

    public void setCurs(Curs curs) { this.curs = curs; }

    public Submateria getSubmateria() {
        return submateria;
    }

    public void setSubmateria(Submateria submateria) { this.submateria = submateria; }
}
