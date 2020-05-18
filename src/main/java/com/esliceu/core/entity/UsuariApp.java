package com.esliceu.core.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usuariapp")
public class UsuariApp implements Serializable {

    @Id
    @Column(name = "email", length = 30, nullable = false)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
