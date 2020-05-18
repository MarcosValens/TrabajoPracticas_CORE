package com.esliceu.core.manager;

import com.esliceu.core.entity.UsuariApp;
import com.esliceu.core.repository.UsuariAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuariAppManager {

    @Autowired
    private UsuariAppRepository usuariAppRepository;

    public UsuariApp findByEmail(String email) {

        UsuariApp usuariApp = usuariAppRepository.findByEmail(email);

        System.out.println(usuariApp);
        return usuariApp;

    }
}
