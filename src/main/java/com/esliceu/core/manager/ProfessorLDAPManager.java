package com.esliceu.core.manager;

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
import java.util.Base64;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProfessorLDAPManager {
    private DirContext context;
    private String url;
    private Hashtable<String, String> environment;

    @Autowired
    ProfessorManager professorManager;

    public ProfessorLDAPManager(@Value("${LDAP_URL}") String urlLdap, @Value("${LDAP_ADMIN}") String admin, @Value("${LDAP_PASSWORD}") String password) throws NamingException {
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

    public void actualitzarProfessorsLdap(List<Professor> professors) throws NamingException {
        List<String> professorsSet = new LinkedList<>();
        for (Professor professor : professors) {
            boolean eliminat = professor.isEliminat();
            boolean nou = professor.isNew();

            BasicAttributes attrs = createBasics();
            if (eliminat) {
                deleteProfessor(professor);
            } else if (nou) {
                addProfessor(professor, attrs, professorsSet);
            } else {
                String oldUserneme = searchProfessor(professor);
                editProfessor(oldUserneme, professor);
            }
        }
    }

    private void addProfessor(Professor professor, BasicAttributes attrs, List<String> professorSet) throws NamingException {
        String username = createUserName(professorSet, professor);
        Long lastUid = professorManager.getLastUidNumber();
        if (lastUid == null) {
            lastUid = 10000L;
        }
        Long uidNumber = lastUid + 1;

        attrs.put("employeenumber", professor.getCodi());
        attrs.put("uidnumber", uidNumber.toString());
        attrs.put("uid", username);
        attrs.put("sn", professor.getAp1() + " " + professor.getAp2());
        attrs.put("gidnumber", "10000");
        attrs.put("displayname", professor.getNom());
        attrs.put("loginshell", "/bin/bash");
        attrs.put("mail", username + "@esliceu.net");
        attrs.put("homedirectory", "/home/" + username);
        attrs.put("userpassword", cryptToMd5("esliceu2019"));
        attrs.put("description", " ");

        professor.setNew(false);
        professor.setLoginLDAP(username);
        professor.setPasswordLDAP("esliceu2019");
        professor.setUidNumberLDAP(uidNumber);
        professorManager.createOrUpdate(professor);


        this.context.createSubcontext(this.url + "/cn=" + username + ",ou=professors,ou=people,dc=esliceu,dc=com", attrs);
    }

    private String searchProfessor(Professor professor) throws NamingException {
        String base = "ou=professors,ou=people,dc=esliceu,dc=com";

        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

        String filter = "(uidNumber=" + professor.getUidNumberLDAP() + ")";

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

    private void editProfessor(String username, Professor professor) throws NamingException {
        String base = "ou=professors,ou=people,dc=esliceu,dc=com";
        ModificationItem[] mod = new ModificationItem[2];
        Attribute modUsername = new BasicAttribute("uid", professor.getLoginLDAP());
        Attribute modhome = new BasicAttribute("homedirectory", "/home/" + professor.getLoginLDAP());
        mod[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, modUsername);
        mod[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, modhome);
        context.modifyAttributes("cn=" + username + "," + base, mod);

    }

    private void deleteProfessor(Professor professor) throws NamingException {
        String base = "ou=professors,ou=people,dc=esliceu,dc=com";
        this.context.destroySubcontext("cn=" + professor.getLoginLDAP() + "," + base);
    }

    private String createUserName(List<String> professors, Professor professor) {
        char primeraLletraNom = professor.getNom().charAt(0);
        String username = primeraLletraNom + professor.getAp1();
        username = Normalizer.normalize(username, Normalizer.Form.NFD);
        username = username.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        username = username.replaceAll("\\s+", "");
        username = checkIfUserProfessorExistst(username.toLowerCase(), true, professors, professor);
        professors.add(username);
        return username;
    }

    private String checkIfUserProfessorExistst(String username, boolean firstTime, List<String> professors, Professor professor) {
        if (professors.contains(username) && firstTime) {
            username = checkIfUserProfessorExistst(username + professor.getAp2().toLowerCase().charAt(0), false, professors, professor);
        } else if (professors.contains(username) && !firstTime) {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(username);
            if (!matcher.find(0)) {
                username = checkIfUserProfessorExistst(username + "1", false, professors, professor);
            } else {
                Integer index = Integer.parseInt(matcher.group(0));
                username = checkIfUserProfessorExistst(username + (index + 1), false, professors, professor);
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
}
