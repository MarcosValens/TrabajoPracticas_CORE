package com.esliceu.core.manager;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.entity.Grup;
import com.esliceu.core.entity.Professor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.security.MessageDigest;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

            List<String> alumnesSet = new LinkedList<>();
            long contador = 1;
            for (Alumne alumne : alumnes) {

                String username = createUserName(alumnesSet, alumne);
                Long uidNumber = 12000 + contador;

                attrs.put("employeenumber", alumne.getExpedient().toString());
                attrs.put("uidnumber", uidNumber.toString());
                attrs.put("uid", username);
                attrs.put("sn", alumne.getAp1() + " " + alumne.getAp2());
                attrs.put("gidnumber", "10000");
                attrs.put("displayname", alumne.getNom());
                attrs.put("loginshell", "/bin/bash");
                attrs.put("mail", username + "@esliceu.net");
                attrs.put("homedirectory", "/home/" + username);
                attrs.put("userpassword", cryptToMd5("esliceu2019"));
                attrs.put("description", " ");


                this.context.createSubcontext(this.url + "/cn=" + username + ",ou=alumnes,ou=people,dc=esliceu,dc=com", attrs);
                contador++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Professors començen per 10k i els alumnes per 12k i s'els hi suma incremental
    private String createUserName(List<String> alumnes, Alumne alumne) {
        char primeraLletraNom = alumne.getNom().charAt(0);
        String username = primeraLletraNom + alumne.getAp1();
        username = Normalizer.normalize(username, Normalizer.Form.NFD);
        username = username.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        username = username.replaceAll("\\s+","");
        username = checkIfUserAlumneExistst(username.toLowerCase(), true, alumnes, alumne);
        alumnes.add(username);
        return username;
    }

    private String checkIfUserAlumneExistst(String username, boolean firstTime, List<String> alumnes, Alumne alumne) {
        if (alumnes.contains(username) && firstTime) {
            username = checkIfUserAlumneExistst(username + alumne.getAp2().toLowerCase().charAt(0), false, alumnes, alumne);
        } else if (alumnes.contains(username) && !firstTime) {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(username);
            if (!matcher.find(0)) {
                username = checkIfUserAlumneExistst(username + "1", false, alumnes, alumne);
            } else {
                Integer index = Integer.parseInt(matcher.group(0));
                username = checkIfUserAlumneExistst(username + (index + 1), false, alumnes, alumne);
            }
        }
        System.out.println(username);
        return username;
    }

    private String cryptToMd5(String plainPassword) {
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

    /*public void addProfessors(List<Professor> professors){
        try {
            BasicAttributes attrs = new BasicAttributes();

            Attribute classes = new BasicAttribute("objectclass");
            classes.add("person");
            classes.add("posixAccount");
            classes.add("inetOrgPerson");
            classes.add("organizationalPerson");
            classes.add("top");
            attrs.put(classes);


            Set<String> professorSet = new HashSet<>();
            int contador = 1;
            for (Professor professor : professors) {
                String displayName = professor.getNom() + " " + professor.getAp1() + " " + professor.getAp2();
                String username = createUserName(professor.getNom(), professor.getAp1(), professor.getAp2()).toLowerCase();
                String mailLiceu = createEmailProfessors(professor, professorSet).toLowerCase();
                String cognoms = professor.getAp1() + " " + professor.getAp2();

                attrs.put("displayname", displayName);
                attrs.put("gidnumber", 10000);
                attrs.put("homedirectory", "/home/" + username);
                attrs.put("l", "Localitat");
                attrs.put("loginshell", "/bin/bash");
                attrs.put("mail", mailLiceu);
                attrs.put("sn", cognoms);
                attrs.put("uid", 10000+contador);
                contador++;
                attrs.put("uidnumber", 10000);
                attrs.put("userpassword", cryptToMd5("esliceu2019"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String createEmailProfessors(Professor professor, Set<String> alumnesSet) {
        String nom = professor.getNom();
        String cognom1 = professor.getAp1();
        String cognom2 = professor.getAp2();

        String mailBase = nom + "." + cognom1;
        if (cognom2 != null){
            mailBase += '.'+cognom2;
        }

        return mailBase + "@esliceu.com";
    }*/

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