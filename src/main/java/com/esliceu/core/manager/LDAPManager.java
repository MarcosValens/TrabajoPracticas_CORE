
package com.esliceu.core.manager;

import com.esliceu.core.entity.Grup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;

import javax.naming.*;
import javax.naming.directory.*;

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

    public void addUser() {
        try {
            BasicAttributes attrs = new BasicAttributes();

            Attribute classes = new BasicAttribute("objectclass");
            classes.add("person");
            classes.add("posixAccount");
            classes.add("inetOrgPerson");
            classes.add("organizationalPerson");
            classes.add("top");
            attrs.put(classes);

            attrs.put("displayname", "ALUMNE JAVA");
            attrs.put("gidnumber", "10001");
            attrs.put("homedirectory", "/home/userjava");
            attrs.put("l", "Localitat");
            attrs.put("loginshell", "/bin/bash");
            attrs.put("mail", "alumnejava@esliceu.net");
            attrs.put("sn", "COGNOMSJAVA");
            attrs.put("uid", "alumnejava");
            attrs.put("uidnumber", "11772");
            attrs.put("userpassword", "patata");

            this.context.createSubcontext(this.url + "/cn=userjava,ou=users,ou=accounts,dc=esliceu,dc=com", attrs);

            this.context.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
