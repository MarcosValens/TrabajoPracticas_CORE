
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

    public void actualitzarAlumnesLdap(List<Alumne> alumnes) {
        for (Alumne alumne : alumnes) {
            boolean eliminat = alumne.isEliminat();
            boolean nou = alumne.isNew();

            BasicAttributes attrs = createBasics();
            if (eliminat) {
                //Eliminar
            } else if (nou) {
                addAlumne(alumne, attrs);
            } else {
                //Editar
            }
        }
    }

    public void addAlumne(Alumne alumne, BasicAttributes attrs) {
        try {
            List<String> alumnesSet = new LinkedList<>();

            String username = createUserName(alumnesSet, alumne);
            Long uidNumber = 12000L;

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchAlumne(String username) throws NamingException {
        String base = "ou=alumnes,ou=people,dc=esliceu,dc=com";

        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

        String filter = "(cn=" + username + ")";

        NamingEnumeration results = this.context.search(base, filter, sc);

        while (results.hasMore()) {
            SearchResult sr = (SearchResult) results.next();
            Attributes attributes = sr.getAttributes();
            Attribute attribute = attributes.get("mail");
            if (attribute != null) {
                System.out.println("ALUMNE: " + attribute.get());
            }
        }
    }

    public void editAlumne(String username, String newUsername) throws NamingException {
        String base = "ou=alumnes,ou=people,dc=esliceu,dc=com";
        ModificationItem[] mod = new ModificationItem[1];
        Attribute modUsername = new BasicAttribute("uid", newUsername);
        mod[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, modUsername);

        context.modifyAttributes("cn=" + username + "," + base, mod);

    }

    public void deleteAlumne(String username) throws NamingException {
        String base = "ou=alumnes,ou=people,dc=esliceu,dc=com";
        this.context.destroySubcontext("cn=" + username + "," + base);
    }

    //Professors començen per 10k i els alumnes per 12k i s'els hi suma incremental
    private String createUserName(List<String> alumnes, Alumne alumne) {
        char primeraLletraNom = alumne.getNom().charAt(0);
        String username = primeraLletraNom + alumne.getAp1();
        username = Normalizer.normalize(username, Normalizer.Form.NFD);
        username = username.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        username = username.replaceAll("\\s+", "");
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

    private BasicAttributes createBasics() {
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
