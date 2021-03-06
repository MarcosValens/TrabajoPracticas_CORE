package com.esliceu.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "avaluacio")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codi")
public class Avaluacio implements Serializable {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "descripcio", length = 50)
    private String descripcio;

    @Column(name = "data_inici", columnDefinition = "DATE")
    private LocalDate dataInici;

    @Column(name = "data_fi", columnDefinition = "DATE")
    private LocalDate dataFi;

    @JoinColumn(name = "grup_codi")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Grup grup;

    public Avaluacio() {
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

    public LocalDate getDataInici() {
        return dataInici;
    }

    public void setDataInici(LocalDate dataInici) {
        this.dataInici = dataInici;
    }

    public LocalDate getDataFi() {
        return dataFi;
    }

    public void setDataFi(LocalDate dataFi) {
        this.dataFi = dataFi;
    }

    public Grup getGrup() {
        return grup;
    }

    public void setGrup(Grup grup) {
        this.grup = grup;
    }
}
