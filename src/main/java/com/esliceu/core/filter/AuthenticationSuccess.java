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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

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

        Cookie[] cookies = request.getCookies();
        Stream<Cookie> stream = Objects.nonNull(cookies) ? Arrays.stream(cookies) : Stream.empty();

        String origin = stream.filter(cookie -> "Origin".equals(cookie.getName()))
                .findFirst()
                .orElse(new Cookie("Origin", null))
                .getValue();


        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        Map attributes = oidcUser.getAttributes();
        String email = (String) attributes.get("email");
        UsuariApp usuariApp = usuariAppManager.findByEmail(email);
        String acces_token = tokenManager.generateAcessToken(usuariApp);
        String refresh_token = tokenManager.generateRefreshToken(usuariApp);

        /*
         * Sacamos los roles
         * */

        boolean admin = usuariApp.isAdmin();
        boolean cuiner = usuariApp.isCuiner();
        boolean monitor = usuariApp.isMonitor();

        String redirectionURL=null;

        if (origin.equals("admin")) {
            // Funciona Modo Hash
            redirectionURL = environment.getProperty("FRONTEND_URL_ADMIN") + "?access_token=" + acces_token
                    + "&refresh_token=" + refresh_token
                    + "&isAdmin=" + admin + "&isCuiner=" + cuiner + "&isMonitor=" + monitor +
                    "#/login/oauth/callback";

        }

        if (origin.equals("menjador") || origin==null){
            // Funciona Modo Hash
            redirectionURL = environment.getProperty("FRONTEND_URL_MENJADOR") + "?access_token=" + acces_token
                    + "&refresh_token=" + refresh_token
                    + "&isAdmin=" + admin + "&isCuiner=" + cuiner + "&isMonitor=" + monitor +
                    "#/login/oauth/callback";

        }

        getRedirectStrategy().sendRedirect(request, response, redirectionURL);
    }
}
