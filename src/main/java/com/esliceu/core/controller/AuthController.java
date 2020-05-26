package com.esliceu.core.controller;

import com.esliceu.core.entity.UsuariApp;
import com.esliceu.core.manager.TokenManager;
import com.esliceu.core.manager.UsuariAppManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
        List<String> roles = new LinkedList<>();


        /*
         * Se envia estas dobles comillas para que javascript
         * lo pueda tratar como un array de strings y hacer un JSON.parse
         * */
        if (user.isCuiner()) roles.add("\"cuiner\"");
        if (user.isAdmin()) roles.add("\"admin\"");
        if (user.isMonitor()) roles.add("\"monitor\"");
        map.put("rol", Arrays.toString(roles.toArray()));
        return map;
    }

    @PostMapping("/auth/refresh")
    public Map<String, String> refresh(@RequestBody String json, HttpServletResponse response) {
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String refresh_token = jsonObject.get("refresh_token").getAsString();

        String validate = tokenManager.validateToken(refresh_token);

        if (!validate.equals("OK")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        UsuariApp user = tokenManager.getUsuariFromToken(refresh_token);

        Map<String, String> map = new HashMap<>();
        map.put("access_token", tokenManager.generateAcessToken(user));
        map.put("refresh_token", tokenManager.generateRefreshToken(user));

        return map;
    }

    @PostMapping("/auth/login/flutter")
    public Map<String, String> loginFlutter(@RequestBody String json, HttpServletResponse response) {

        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String emailJson = jsonObject.get("email").getAsString();

        UsuariApp usuariApp = usuariAppManager.findByEmail(emailJson);

        System.out.println(usuariApp);

        if (usuariApp == null) {
            UsuariApp usuariBD = new UsuariApp();
            usuariBD.setEmail(emailJson);
            usuariAppManager.create(usuariBD);
        }

        UsuariApp usuariGoogle = usuariAppManager.findByEmail(emailJson);

        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, String> map = new HashMap<>();
        map.put("id", usuariGoogle.getProfessor().getCodi());
        map.put("access_token", tokenManager.generateAcessToken(usuariGoogle));
        map.put("refresh_token", tokenManager.generateRefreshToken(usuariGoogle));
        map.put("rol", "professor");
        return map;
    }

    @PostMapping("/admin/auth/register")
    public String register(@RequestBody String json, HttpServletResponse response) {
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String nombre = jsonObject.get("nombre").getAsString();
        String apellido1 = jsonObject.get("apellido1").getAsString();
        String apellido2 = jsonObject.get("apellido2").getAsString();
        String email = jsonObject.get("email").getAsString();
        String contrasenya = jsonObject.get("contrasenya").getAsString();
        JsonArray roles = jsonObject.get("roles").getAsJsonArray();

        if (usuariAppManager.findByEmail(email) != null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        String contrasenyaHashed = BCrypt.hashpw(contrasenya, BCrypt.gensalt());

        UsuariApp usuariApp = new UsuariApp();
        usuariApp.setEmail(email);
        usuariApp.setNombre(nombre);
        usuariApp.setApellido1(apellido1);
        usuariApp.setApellido2(apellido2);
        usuariApp.setContrasenya(contrasenyaHashed);
        usuariApp.setCuiner(false);
        usuariApp.setAdmin(false);
        usuariApp.setMonitor(false);

        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).toString().equals("\"Cuiner\"")) {
                usuariApp.setCuiner(true);
            }
            if (roles.get(i).toString().equals("\"Admin\"")) {
                usuariApp.setAdmin(true);
            }
            if (roles.get(i).toString().equals("\"Monitor\"")) {
                usuariApp.setMonitor(true);
            }
        }

        usuariAppManager.create(usuariApp);
        response.setStatus(HttpServletResponse.SC_OK);
        return null;
    }
}
