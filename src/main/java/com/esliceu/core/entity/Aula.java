package com.esliceu.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "aula")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codi")
public class Aula  implements Serializable {

    @Id
    @Column(name = "codi")
    private Long codi;

    @Column(name = "descripcio", length = 300)
    private String descripcio;

    public Aula() {
    }

    public Long getCodi() {
        return codi;
    }

    public void setCodi(Long codi) {
        this.codi = codi;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
}
