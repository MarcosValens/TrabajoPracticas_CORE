package com.esliceu.core.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "horarig")
public class Horarig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codi")
    private Long codi;

    @Column(name = "durada")
    private Long durada;

    @Column(name = "dia", columnDefinition = "DATE")
    private Date dia;

    @Column(name = "hora")
    private Time hora;

    public Horarig() {
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

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }
}
