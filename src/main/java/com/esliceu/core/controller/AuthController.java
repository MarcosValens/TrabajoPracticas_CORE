package com.esliceu.core.controller;

import com.esliceu.core.entity.UsuariApp;
import com.esliceu.core.manager.MailingManager;
import com.esliceu.core.manager.OauthManager;
import com.esliceu.core.manager.TokenManager;
import com.esliceu.core.manager.UsuariAppManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private MailingManager mailingManager;

    @Autowired
    private OauthManager oauthManager;

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

        /*
         * POR AHORA COMO NO FUNCIONA EL OAUTH-FLUTTER BIEN EN ANDROID ESTO SE QUEDA COMENTADO
         * */
//        String idToken = jsonObject.get("idToken").getAsString();
//
//        if (!oauthManager.validateGoogleIdToken(idToken)){
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return null;
//        }

        UsuariApp usuariApp = usuariAppManager.findByEmail(emailJson);


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

    @PutMapping("/private/auth/password")
    public ResponseEntity<String> cambiarPassword(@RequestBody String json, HttpServletRequest request){
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String oldpasswd = jsonObject.get("oldpasswd").getAsString();
        String newpasswd = jsonObject.get("newpasswd").getAsString();
        String newpasswd2 = jsonObject.get("newpasswd2").getAsString();

        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        UsuariApp usuariApp = tokenManager.getUsuariFromToken(token);

        if (usuariAppManager.validarUsuari(usuariApp.getEmail(), oldpasswd)){
            if (newpasswd.equals(newpasswd2)){
                usuariApp.setContrasenya(BCrypt.hashpw(newpasswd, BCrypt.gensalt()));
                usuariAppManager.create(usuariApp);
                return new ResponseEntity<>("Contrasenya canviada correctament.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Les noves contrasenyes no coincideixen.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("La contrasenya antiga no es correcte.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/recovery")
    public ResponseEntity<String> revocerPassword(@RequestBody String json, HttpServletRequest request){
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String email = jsonObject.get("email").getAsString();

        /*
        * Validamos que el correo exista
        *
        * No posam que el correo no exista para no dar info de nuestra bbdd
        * */
        if (usuariAppManager.findByEmail(email) == null)
            return new ResponseEntity<>("MEEEC ERROR", HttpStatus.BAD_REQUEST);


        /*
         * Ya que tenemos mas de un frontend
         * recogemos la url de que frontend ha hecho la peticion
         * */

        final String FRONT_URL = request.getHeader("Origin");
        UsuariApp user = new UsuariApp();
        user.setEmail(email);
        final String token = this.tokenManager.generateGenericToken(user, (long) 3600 * 1000);


        /*
         * Esta es la URL si usamos el modo HISTORY en el front-end
         * */
        //final String RECOVERY_RUL = FRONT_URL+"/change/password?recovery_token="+token;

        /*
         * Esta es la URL si usamos el modo HASH en el front-end
         * */
        final String RECOVERY_RUL = FRONT_URL + "?recovery_token=" + token + "#/change/password";

        try {
            this.mailingManager.sendEmailRecoveryPasswd(email, RECOVERY_RUL);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ha habido un error a la hora de enviar el correo electronico", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PutMapping("/auth/recovery")
    public ResponseEntity<String> revocerPassword(@RequestBody String json) {
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        String newPasswd = jsonObject.get("newPasswd").getAsString();
        String newPasswd2 = jsonObject.get("newPasswd2").getAsString();
        String recoveryToken = jsonObject.get("recoveryToken").getAsString();

        if (!newPasswd.equals(newPasswd2))
            return new ResponseEntity<>("Las contrasenyas no coincideixen", HttpStatus.BAD_REQUEST);

        UsuariApp user = tokenManager.getUsuariFromToken(recoveryToken);
        if (user == null) return new ResponseEntity<>("Token no correcte", HttpStatus.UNAUTHORIZED);

        user.setContrasenya(BCrypt.hashpw(newPasswd, BCrypt.gensalt()));
        usuariAppManager.create(user);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
