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

@Service
public class TokenManager implements Serializable {

    @Autowired
    private static Environment environment;

    public static final String SIGNING_KEY = "1234";

    public static String generateAcessToken(UsuariApp usuariApp) {
        Claims claims = Jwts.claims().setSubject(usuariApp.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("https://esliceu.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public static String generateRefreshToken(UsuariApp usuariApp) {
        Claims claims = Jwts.claims().setSubject(usuariApp.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("https://esliceu.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public String validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret").getBytes())
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
