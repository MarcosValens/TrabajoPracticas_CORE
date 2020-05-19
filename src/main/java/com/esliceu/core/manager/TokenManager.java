package com.esliceu.core.manager;

import com.esliceu.core.entity.UsuariApp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Service
public class TokenManager implements Serializable {

    @Autowired
    private static Environment environment;

    public static String generateAcessToken(UsuariApp usuariApp) {

        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(usuariApp.getEmail()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(environment.getProperty("ISSUER"))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("ACCES_TOKEN_EXPIRE"))))
                        .signWith(SignatureAlgorithm.HS256, Objects.requireNonNull(environment.getProperty("SIGNING_KEY_TOKEN")).getBytes())
                        .compact();
    }

    public static String generateRefreshToken(UsuariApp usuariApp) {

        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(usuariApp.getEmail()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(environment.getProperty("ISSUER"))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("REFRESH_TOKEN_EXPIRE"))))
                        .signWith(SignatureAlgorithm.HS256, Objects.requireNonNull(environment.getProperty("SIGNING_KEY_TOKEN")).getBytes())
                        .compact();
    }

    public String validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(Objects.requireNonNull(environment.getProperty("SIGNING_KEY_TOKEN")).getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            return "OK";
        } catch (ExpiredJwtException e) {
            return "EXPIRED";
        } catch (Exception e) {
            return "ERROR";
        }
    }

}
