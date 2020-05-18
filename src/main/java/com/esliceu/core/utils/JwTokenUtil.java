package com.esliceu.core.utils;

import com.esliceu.core.entity.UsuariApp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.Serializable;
import java.util.Date;

public class JwTokenUtil implements Serializable {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;
    public static final String SIGNING_KEY = "1234";

    public static String generateToken(UsuariApp usuariApp) {
        Claims claims = Jwts.claims().setSubject(usuariApp.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("https://esliceu.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }
}
