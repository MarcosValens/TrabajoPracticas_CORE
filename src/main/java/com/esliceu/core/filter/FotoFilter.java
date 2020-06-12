package com.esliceu.core.filter;

import com.esliceu.core.entity.UsuariApp;
import com.esliceu.core.manager.TokenManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class FotoFilter implements HandlerInterceptor {


    @Autowired
    private Environment environment;

    @Value("${DELETE.FOTOS.SECRET}")
    private String secret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("DELETE")) {
            String auth = request.getHeader("Authorization");
            if(auth == null) return false;
            if(auth.equals(secret)) return true;
            else {
                return false;
            }
        }
        else return false;

    }
}
