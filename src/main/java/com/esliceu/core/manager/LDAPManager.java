package com.esliceu.core.manager;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.entity.Grup;
import com.sun.mail.util.BASE64EncoderStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.*;

import javax.naming.*;
import javax.naming.directory.*;
import javax.xml.bind.DatatypeConverter;

@Service
public class LDAPManager {
    private DirContext context;
    private String url;

    public LDAPManager(@Value("${LDAP_URL}") String urlLdap, @Value("${LDAP_ADMIN}") String admin, @Value("${LDAP_PASSWORD}") String password) throws NamingException {
        Hashtable<String, String> environment = new Hashtable<>();
        this.url = urlLdap;

        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL, this.url);
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        environment.put(Context.SECURITY_PRINCIPAL, admin);
        environment.put(Context.SECURITY_CREDENTIALS, password);
        this.context = new InitialDirContext(environment);
        System.out.println("Connected..");
        System.out.println(this.context.getEnvironment());
    }

    public void addUser(List<Alumne> alumnes) {
        try {
            BasicAttributes attrs = new BasicAttributes();

            Attribute classes = new BasicAttribute("objectclass");
            classes.add("person");
            classes.add("posixAccount");
            classes.add("inetOrgPerson");
            classes.add("organizationalPerson");
            classes.add("top");
            attrs.put(classes);

            Set<String> alumnesSet = new HashSet<>();
            for (Alumne alumne : alumnes) {

                String displayName = alumne.getNom() + " " + alumne.getAp1() + " " + alumne.getAp2();
                String gidNumber = alumne.getGrup().getCodi().toString();
                String username = createUserName(alumne.getNom(), alumne.getAp1(), alumne.getAp2()).toLowerCase();
                String mailLiceu = createEmailLiceu(alumne, alumnesSet).toLowerCase();
                String cognoms = alumne.getAp1() + " " + alumne.getAp2();
                Long uidNumberMod = alumne.getExpedient() + 10000;
                String uidNumber = uidNumberMod.toString();

                attrs.put("displayname", displayName);
                attrs.put("gidnumber", gidNumber);
                attrs.put("homedirectory", "/home/" + username);
                attrs.put("l", "Localitat");
                attrs.put("loginshell", "/bin/bash");
                attrs.put("mail", mailLiceu);
                attrs.put("sn", cognoms);
                attrs.put("uid", username);
                attrs.put("uidnumber", uidNumber);
                attrs.put("userpassword", cryptToMd5("esliceu2019"));

                this.context.createSubcontext(this.url + "/cn=" + username + ",ou=users,ou=accounts,dc=esliceu,dc=com", attrs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createUserName(String nom, String cognom1, String cognom2) {
        char primeraLletraNom = nom.charAt(0);
        char primeraLletraCognom2 = cognom2.charAt(0);
        return primeraLletraNom + cognom1 + primeraLletraCognom2;
    }

    private String createEmailLiceu(Alumne alumne, Set<String> alumnesSet) {
        String nom = alumne.getNom();
        String cognom1 = alumne.getAp1();
        String cognom2 = alumne.getAp2();

        String mailBase = nom + "." + cognom1 + "." + cognom2;
        if (alumnesSet.contains(mailBase)) {
            mailBase += alumne.getExpedient();
        } else {
            alumnesSet.add(mailBase);
        }
        return mailBase + "@esliceu.net";
    }

    public String cryptToMd5(String plainPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(plainPassword.getBytes());
            String base64 = Base64.getEncoder().encodeToString(digest.digest());
            return "{MD5}" + base64;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addGroup(List<Grup> grups) {
        try {
            BasicAttributes attrs = new BasicAttributes();

            Attribute classes = new BasicAttribute("objectclass");
            classes.add("posixGroup");
            classes.add("top");
            attrs.put(classes);
            for (Grup grup : grups) {
                attrs.put("gidnumber", grup.getCodi().toString());

                this.context.createSubcontext(this.url + "/cn=" + grup.getCurs().getDescripcio() + " " + grup.getNom() + ",ou=posixgroups,ou=accounts,dc=esliceu,dc=com", attrs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}