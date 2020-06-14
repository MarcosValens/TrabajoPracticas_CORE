
package com.esliceu.core.manager;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.entity.Grup;
import com.esliceu.core.entity.Professor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AlumneLDAPManager {
    private DirContext context;
    private String url;
    private Hashtable<String, String> environment;

    @Autowired
    AlumneManager alumneManager;

    public AlumneLDAPManager(@Value("${LDAP_URL}") String urlLdap, @Value("${LDAP_ADMIN}") String admin, @Value("${LDAP_PASSWORD}") String password) throws NamingException {
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

    public int actualitzarAlumnesLdap(List<Alumne> alumnes) throws NamingException {
        Integer numActualitzacions = 0;  // contarem tant inserts com deletes
        List<String> alumnesSet = new LinkedList<>();
        for (Alumne alumne : alumnes) {
            boolean eliminat = alumne.isEliminat();
            boolean nou = alumne.isNew();

            BasicAttributes attrs = createBasics();
            if (eliminat) {
                deleteAlumne(alumne);
                numActualitzacions++;

            } else if (nou) {
                addAlumne(alumne, attrs, alumnesSet);
                numActualitzacions++;
            } else {
                String oldUsername = searchAlumne(alumne);
                if (oldUsername != null) {
                    editAlumne(oldUsername, alumne);
                } else {
                    System.out.println("actAlumnes.edit: " + alumne.getNom() + " " + alumne.getAp1() + " no trobat, el cream." );
                    addAlumne(alumne, attrs, alumnesSet);
                }
            }
        }
        return numActualitzacions;
    }

    // TODO: Aquesta funcio s'hauria de cridar despres de carregar un XML i abans de que l'administrador sincronitzi LDAP.
    // Que passa si es carreguen varies vegades un xml sense sincronitzar ldap?
    //   Per evitar-ho nomes es calcularan els camps si no tenen valors.
    private void calculateDefaultAlumneAttributes(Alumne alumne, List<String> alumnesSet) {
        if (alumne.getLoginLDAP() == null) {
            String username = createUserName(alumnesSet, alumne);
            alumne.setLoginLDAP(username);
        }
        if (alumne.getUidNumberLDAP() == null) {
            Long lastUid = alumneManager.getLastUidNumber();
            if (lastUid == null) {
                lastUid = 12000L;
            }
            Long uidNumber = lastUid +1;
            alumne.setUidNumberLDAP(uidNumber);
        }
        if (alumne.getPasswordLDAP() == null) {
            alumne.setPasswordLDAP("esliceu2019");   // TODO: Hauria de ser un random
        }
    }

    // TODO: Hem de xapar la funcionalitat d'aquesta funcio. Cal definir els atributs ldap d'alumne
    //  abans perque l'administrador ho pugui validar i llavors replicar damunt LDAP.
    // TODO: treure calculateDefaultAlumneAttributes i llevar la llista alumnesSet

    private void addAlumne(Alumne alumne, BasicAttributes attrs, List<String> alumnesSet) throws NamingException {

       /* String username = createUserName(alumnesSet, alumne);
        Long lastUid = alumneManager.getLastUidNumber();
        if (lastUid == null){
            lastUid = 12000L;
        }
        Long uidNumber = lastUid +1;
        */
        // provam que encara va be
        System.out.println("addAlumne: " + alumne.getNom() + " " + alumne.getAp1());
        calculateDefaultAlumneAttributes(alumne, alumnesSet);

        String username = alumne.getLoginLDAP();

        attrs.put("employeenumber", alumne.getExpedient().toString());
        //attrs.put("uidnumber", uidNumber.toString());
        attrs.put("uidnumber", alumne.getUidNumberLDAP().toString());
        attrs.put("uid", username);
        attrs.put("sn", alumne.getAp1() + " " + alumne.getAp2());
        attrs.put("gidnumber", "10000");
        attrs.put("displayname", alumne.getNom());
        attrs.put("loginshell", "/bin/bash");
        attrs.put("mail", username + "@esliceu.net");
        attrs.put("homedirectory", "/home/" + username);
        //attrs.put("userpassword", cryptToMd5("esliceu2019"));
        attrs.put("userpassword", cryptToMd5(alumne.getPasswordLDAP()));
        attrs.put("description", " ");

        alumne.setNew(false);
        //alumne.setLoginLDAP(username);
        //alumne.setPasswordLDAP("esliceu2019");
        // alumne.setUidNumberLDAP(uidNumber);
        alumneManager.createOrUpdate(alumne);


        this.context.createSubcontext(this.url + "/cn=" + username + ",ou=alumnes,ou=people,dc=esliceu,dc=com", attrs);
    }

    private String searchAlumne(Alumne alumne) throws NamingException {
        String base = "ou=alumnes,ou=people,dc=esliceu,dc=com";

        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

        String filter = "(uidNumber=" + alumne.getUidNumberLDAP() + ")";

        NamingEnumeration results = this.context.search(base, filter, sc);
        while (results.hasMore()) {
            SearchResult sr = (SearchResult) results.next();
            Attributes attributes = sr.getAttributes();
            Attribute attribute = attributes.get("cn");
            if (attribute != null) {
                return (String) attribute.get();
            }
        }
        return null;
    }

    private void editAlumne(String username, Alumne alumne) throws NamingException {
        String base = "ou=alumnes,ou=people,dc=esliceu,dc=com";
        ModificationItem[] mod = new ModificationItem[2];
        Attribute modUsername = new BasicAttribute("uid", alumne.getLoginLDAP());
        Attribute modhome = new BasicAttribute("homedirectory", "/home/"+alumne.getLoginLDAP());
        mod[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, modUsername);
        mod[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, modhome);
        //System.out.println("editAlumne: Alumne=" + username);
        if (username != null) {
            context.modifyAttributes("cn=" + username + "," + base, mod);
        }
    }

    private void deleteAlumne(Alumne alumne) throws NamingException {
        String base = "ou=alumnes,ou=people,dc=esliceu,dc=com";
        this.context.destroySubcontext("cn=" + alumne.getLoginLDAP() + "," + base);
    }

    //Professors començen per 10k i els alumnes per 12k i s'els hi suma incremental
    private String createUserName(List<String> alumnes, Alumne alumne) {
        char primeraLletraNom = alumne.getNom().charAt(0);
        String username = primeraLletraNom + alumne.getAp1();
        username = Normalizer.normalize(username, Normalizer.Form.NFD);
        username = username.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        username = username.replaceAll("\\s+", "");
        username = checkIfUserAlumneExists(username.toLowerCase(), true, alumnes, alumne);
        alumnes.add(username);
        return username;
    }

    private String checkIfUserAlumneExists(String username, boolean firstTime, List<String> alumnes, Alumne alumne) {
        if (alumnes.contains(username) && firstTime) {
            username = checkIfUserAlumneExists(username + alumne.getAp2().toLowerCase().charAt(0), false, alumnes, alumne);
        } else if (alumnes.contains(username) && !firstTime) {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(username);
            if (!matcher.find(0)) {
                username = checkIfUserAlumneExists(username + "1", false, alumnes, alumne);
            } else {
                Integer index = Integer.parseInt(matcher.group(0));
                username = checkIfUserAlumneExists(username + (index + 1), false, alumnes, alumne);
            }
        }
        System.out.println("checkIfUserAlumneExists: " +username);
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
}
