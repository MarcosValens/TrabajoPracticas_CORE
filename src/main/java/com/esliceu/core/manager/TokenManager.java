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
    private Environment environment;

    @Autowired
    private UsuariAppManager usuariAppManager;

    public String generateAcessToken(UsuariApp usuariApp) {

        long ACCES_TOKEN_EXPIRE = Long.parseLong(Objects.requireNonNull(environment.getProperty("ACCES_TOKEN_EXPIRE")));

        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(usuariApp.getEmail()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer("https://esliceu.com")
                .setExpiration(new Date(System.currentTimeMillis() + ACCES_TOKEN_EXPIRE))
                .signWith(SignatureAlgorithm.HS256, Objects.requireNonNull(environment.getProperty("SIGNING_KEY_TOKEN")).getBytes())
                .compact();
    }

    public String generateRefreshToken(UsuariApp usuariApp) {

        long REFRESH_TOKEN_EXPIRE = Long.parseLong(Objects.requireNonNull(environment.getProperty("REFRESH_TOKEN_EXPIRE")));

        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(usuariApp.getEmail()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer("https://esliceu.com")
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE))
                .signWith(SignatureAlgorithm.HS256, Objects.requireNonNull(environment.getProperty("SIGNING_KEY_TOKEN")).getBytes())
                .compact();
    }

    public String generateGenericToken(UsuariApp usuariApp, Long TOKEN_EXPIRE) {

        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(usuariApp.getEmail()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer("https://esliceu.com")
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE))
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

    public Claims getBody(String token) {
        if (this.validateToken(token).equals("OK")) {
            return Jwts.parser()
                    .setSigningKey(Objects.requireNonNull(environment.getProperty("SIGNING_KEY_TOKEN")).getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        }
        return null;
    }

    public UsuariApp getUsuariFromToken(String token) {
        try {
            Claims claims = getBody(token);
            String email = claims.getSubject();
            return usuariAppManager.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }
}
