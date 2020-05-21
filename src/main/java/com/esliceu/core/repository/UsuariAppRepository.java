package com.esliceu.core.repository;

import com.esliceu.core.entity.UsuariApp;
import org.springframework.data.repository.CrudRepository;

public interface UsuariAppRepository extends CrudRepository<UsuariApp, String> {

    UsuariApp findByEmail(String email);

    UsuariApp findByEmailAndContrasenya(String email, String contrasenya);

}
