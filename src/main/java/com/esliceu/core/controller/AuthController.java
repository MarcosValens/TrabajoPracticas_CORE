package com.esliceu.core.controller;

import com.esliceu.core.entity.UsuariApp;
import com.esliceu.core.manager.TokenManager;
import com.esliceu.core.manager.UsuariAppManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private Gson gson;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UsuariAppManager usuariAppManager;

    @PostMapping("/auth/login")
    public Map<String, String> login(@RequestBody String json, HttpServletResponse response) {
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String emailJson = jsonObject.get("email").getAsString();
        String passwordJson = jsonObject.get("password").getAsString();

        boolean validaLogin = usuariAppManager.validarUsuari(emailJson, passwordJson);
        if (!validaLogin) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        response.setStatus(HttpServletResponse.SC_OK);

        UsuariApp user = usuariAppManager.findByEmail(emailJson);

        Map<String, String> map = new HashMap<>();
        map.put("access_token", tokenManager.generateAcessToken(user));
        map.put("refresh_token", tokenManager.generateRefreshToken(user));
        map.put("rol", "professor");
        return map;
    }
}
