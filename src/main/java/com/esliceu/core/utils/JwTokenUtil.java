package com.esliceu.core.utils;

import com.esliceu.core.entity.UsuariApp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.Serializable;
import java.util.Date;

public class JwTokenUtil implements Serializable {

    @Autowired
    private static Environment environment;

    public static String generateAcessToken(UsuariApp usuariApp) {
        Claims claims = Jwts.claims().setSubject(usuariApp.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("https://esliceu.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + environment.getProperty("ACCES_TOKEN_EXPIRE")))
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("SIGNING_KEY_TOKEN"))
                .compact();
    }

    public static String generateRefreshToken(UsuariApp usuariApp) {
        Claims claims = Jwts.claims().setSubject(usuariApp.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("https://esliceu.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + environment.getProperty("REFRESH_TOKEN_EXPIRE")))
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("SIGNING_KEY_TOKEN"))
                .compact();
    }

}
