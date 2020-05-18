package com.esliceu.core.filter;

import com.esliceu.core.entity.UsuariApp;
import com.esliceu.core.manager.UsuariAppManager;
import com.esliceu.core.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class AuthenticationSuccess extends SimpleUrlAuthenticationSuccessHandler {

    private String homeUrl = "http://localhost:8080/";

    @Autowired
    private UsuariAppManager usuariAppManager;

    @Autowired
    private Environment environment;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        if (response.isCommitted()) {
            return;
        }

        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        Map attributes = oidcUser.getAttributes();
        String email = (String) attributes.get("email");
        UsuariApp usuariApp = usuariAppManager.findByEmail(email);
        String acces_token = TokenManager.generateAcessToken(usuariApp);
        String refresh_token = TokenManager.generateRefreshToken(usuariApp);

        String redirectionUrl = UriComponentsBuilder.fromUriString(environment.getProperty("REDIRECT_URL"))
                .queryParam("acces_token", acces_token)
                .queryParam("refresh_token", refresh_token)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectionUrl);

    }
}
