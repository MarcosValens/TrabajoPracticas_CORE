package com.esliceu.core.filter;

import com.esliceu.core.entity.UsuariApp;
import com.esliceu.core.manager.TokenManager;
import com.esliceu.core.manager.UsuariAppManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class AuthenticationSuccess extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UsuariAppManager usuariAppManager;

    @Autowired
    private Environment environment;

    @Autowired
    TokenManager tokenManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        if (response.isCommitted()) {
            return;
        }

        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        Map attributes = oidcUser.getAttributes();
        String email = (String) attributes.get("email");
        UsuariApp usuariApp = usuariAppManager.findByEmail(email);
        String acces_token = tokenManager.generateAcessToken(usuariApp);
        String refresh_token = tokenManager.generateRefreshToken(usuariApp);

        // Una manera mejor de hacerlo pero da problemas con el #

        /*
        String redirectionUrl = UriComponentsBuilder.fromUriString(environment.getProperty("FRONTEND_URL"))
                .queryParam("acces_token", acces_token)
                .queryParam("refresh_token", refresh_token)
                .build().toUriString();

         */


        /*
         * Sacamos los roles
         * */


        boolean admin = usuariApp.isAdmin();
        boolean cuiner = usuariApp.isCuiner();
        boolean monitor = usuariApp.isMonitor();

        // ESTA LINEA DE AQUI, FUNCIONARÁ BIEN SI TENEMOS EL QUASAR CON EL ROUTER EN MODO HISTORY.
//        String redirectionURL = environment.getProperty("FRONTEND_URL")
//                 + "#/login/oauth/callback" + "?access_token=" + acces_token + "&refresh_token=" + refresh_token
//                 + "&isAdmin="+admin+"&isCuiner="+cuiner+"&isMonitor="+monitor;

        // EN NUESTRO CASO, YA SEA PROD O DEV TENEMOS EL MODO HASH (ABAJO)

        // ESTA LINEA DE AQUI FUNCIONA SI ESTA EL ROUTER EN MODO HASH DE QUASAR
        String redirectionURL = environment.getProperty("FRONTEND_URL") + "?access_token=" + acces_token
                + "&refresh_token=" + refresh_token
                + "&isAdmin=" + admin + "&isCuiner=" + cuiner + "&isMonitor=" + monitor +
                "#/login/oauth/callback";

        getRedirectStrategy().sendRedirect(request, response, redirectionURL);
    }
}
