package com.esliceu.core.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "horarip")
public class Horarip implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codi")
    private Long codi;

    @Column(name = "durada")
    private Long durada;

    @Column(name = "hora")
    private  Time hora;

    @Column(name = "dia", columnDefinition = "DATE")
    private Date dia;

    @Column(name = "placa")
    private Long placa;

    public Horarip() {
    }

    public Long getCodi() {
        return codi;
    }

    public void setCodi(Long codi) {
        this.codi = codi;
    }

    public Long getDurada() {
        return durada;
    }

    public void setDurada(Long durada) {
        this.durada = durada;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Long getPlaca() {
        return placa;
    }

    public void setPlaca(Long placa) {
        this.placa = placa;
    }
}
