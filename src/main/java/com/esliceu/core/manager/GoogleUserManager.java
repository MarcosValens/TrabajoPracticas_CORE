package com.esliceu.core.manager;

import com.esliceu.core.entity.UsuariApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GoogleUserManager extends OidcUserService {

    @Autowired
    UsuariAppManager usuariAppManager;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        // Cogemos el mail de los valores que nos devuelve google
        // Creamos un usuario con el correo siempre y cuando sea @esliceu.com

        OidcUser oidcUser = super.loadUser(userRequest);
        Map attributes = oidcUser.getAttributes();
        UsuariApp usuariApp = new UsuariApp();
        usuariApp.setEmail((String) attributes.get("email"));
        createIfNotExist(usuariApp);

        return oidcUser;
    }

    private boolean createIfNotExist(UsuariApp usuariApp) {
        UsuariApp usuariAppBD = usuariAppManager.findByEmail(usuariApp.getEmail());

        if (usuariAppBD == null) {
            usuariAppManager.create(usuariApp);
        }

        return true;
    }
}
