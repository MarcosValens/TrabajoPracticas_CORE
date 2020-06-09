package com.esliceu.core.manager;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.entity.Grup;
import com.esliceu.core.entity.Professor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
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
    private Hashtable<String, String> environment;

    public LDAPManager(@Value("${LDAP_URL}") String urlLdap, @Value("${LDAP_ADMIN}") String admin, @Value("${LDAP_PASSWORD}") String password) throws NamingException {
        this.environment = new Hashtable<>();
        this.url = urlLdap;

        this.environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        this.environment.put(Context.PROVIDER_URL, this.url);
        this.environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        this.environment.put(Context.SECURITY_PRINCIPAL, admin);
        this.environment.put(Context.SECURITY_CREDENTIALS, password);
        this.context = new InitialDirContext(this.environment);
        System.out.println("Connected..");
        System.out.println(this.context.getEnvironment());
    }

    public void addUser(List<Alumne> alumnes) {
        try {
            BasicAttributes attrs = createBasics();

            //Afegir a la base de dades el uidNumber, username, password
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

    public void search(String username) throws NamingException {
        String base = "ou=alumnes,ou=people,dc=esliceu,dc=com";

        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

        String filter = "(uid="+username+")";

        NamingEnumeration results = this.context.search(base, filter, sc);

        while (results.hasMore()){
            SearchResult sr = (SearchResult) results.next();
            Attributes attributes = sr.getAttributes();
            Attribute attribute = attributes.get("mail");
            if (attribute != null){
                System.out.println("ALUMNE: "+attribute.get());
            }
        }
    }

    public void edit(String username, String newUsername) throws NamingException {
        String base = "ou=alumnes,ou=people,dc=esliceu,dc=com";
        ModificationItem[] mod = new ModificationItem[1];
        Attribute modUsername = new BasicAttribute("uid", newUsername);
        mod[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, modUsername);

        context.modifyAttributes("cn="+username+","+base, mod);

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

    private BasicAttributes createBasics(){
        BasicAttributes attrs = new BasicAttributes();
        Attribute classes = new BasicAttribute("objectclass");
        classes.add("person");
        classes.add("posixAccount");
        classes.add("inetOrgPerson");
        classes.add("organizationalPerson");
        classes.add("top");
        attrs.put(classes);
        return attrs;
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