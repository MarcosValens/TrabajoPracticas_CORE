package com.esliceu.core.manager;

import com.esliceu.core.entity.UsuariApp;
import com.esliceu.core.repository.UsuariAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariAppManager {

    @Autowired
    private UsuariAppRepository usuariAppRepository;

    public UsuariApp findByEmail(String email) {

        UsuariApp usuariApp = usuariAppRepository.findByEmail(email);
        return usuariApp;
    }

    public List<UsuariApp> findAll() {
        return usuariAppRepository.findAll();
    }

    public void create(UsuariApp usuariApp) {

        usuariAppRepository.save(usuariApp);
    }

    public void delete(UsuariApp usuariApp) {
        usuariAppRepository.delete(usuariApp);
    }

    public boolean validarUsuari(String email, String contrasenya) {
        UsuariApp usuariApp = usuariAppRepository.findByEmail(email);
        return BCrypt.checkpw(contrasenya, usuariApp.getContrasenya());
    }
}
