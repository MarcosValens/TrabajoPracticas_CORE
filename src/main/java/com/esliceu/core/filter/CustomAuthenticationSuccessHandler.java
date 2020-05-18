package com.esliceu.core.filter;

import com.esliceu.core.entity.UsuariApp;
import com.esliceu.core.manager.UsuariAppManager;
import com.esliceu.core.utils.JwTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private String homeUrl = "http://localhost:8080/";

    @Autowired
    private UsuariAppManager usuariAppManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            return;
        }
        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        Map attributes = oidcUser.getAttributes();
        String email = (String) attributes.get("email");
        UsuariApp usuariApp = usuariAppManager.findByEmail(email);
        String token = JwTokenUtil.generateToken(usuariApp);

        System.out.println(email);

        String redirectionUrl = UriComponentsBuilder.fromUriString(homeUrl)
                .queryParam("auth_token", token)
                .build().toUriString();

        System.out.println("Va a redirigir");
        System.out.println(redirectionUrl);
        getRedirectStrategy().sendRedirect(request, response, redirectionUrl);

    }
}
