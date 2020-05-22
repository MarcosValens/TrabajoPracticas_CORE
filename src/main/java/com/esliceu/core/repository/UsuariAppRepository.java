package com.esliceu.core.repository;

import com.esliceu.core.entity.UsuariApp;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuariAppRepository extends CrudRepository<UsuariApp, String> {

    List<UsuariApp> findAll();

    UsuariApp findByEmail(String email);

    UsuariApp findByEmailAndContrasenya(String email, String contrasenya);

}
