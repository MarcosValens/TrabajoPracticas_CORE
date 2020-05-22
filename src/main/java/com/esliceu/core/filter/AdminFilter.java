/*
package com.esliceu.core.filter;

import com.esliceu.core.entity.UsuariApp;
import com.esliceu.core.manager.TokenManager;
import com.esliceu.core.manager.UsuariAppManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminFilter implements HandlerInterceptor {

    @Autowired
    TokenManager tokenManager;

    @Autowired
    UsuariAppManager usuariAppManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("OPTIONS")) return true;


        */
/*
 * Si no es un OPTIONS comprueba si la petición contiene el Token
 * y comprueba si es válido o si ha expirado.
 * *//*

        String auth = request.getHeader("Authorization");
        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            String validate = tokenManager.validateToken(token);
            */
/*UsuariApp usuariApp = usuariAppManager.findByEmail(tokenManager.)*//*


            if (validate.equals("ERROR")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no valido");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;

            } else if (validate.equals("EXPIRED")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token caducado");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            response.setStatus(HttpServletResponse.SC_OK);
            return true;

        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no recibido");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
*/
