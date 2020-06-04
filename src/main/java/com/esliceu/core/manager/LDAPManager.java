package com.esliceu.core.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Hashtable;

import javax.naming.*;
import javax.naming.directory.*;

@Service
public class LDAPManager {
    private DirContext context;
    private String url;
/*
    public LDAPManager() throws NamingException {
        this.url = "ldap://localhost:389";
        Hashtable<String, String> environment = new Hashtable<>();

        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL, this.url);
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        environment.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=esliceu,dc=com");
        environment.put(Context.SECURITY_CREDENTIALS, "test");
        this.context = new InitialDirContext(environment);
        System.out.println("Connected..");
        System.out.println(this.context.getEnvironment());
    }*/

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
            attrs.put("gidnumber", "10000");
            attrs.put("homedirectory", "/home/userjava");
            attrs.put("l", "Localitat");
            attrs.put("loginshell", "/bin/bash");
            attrs.put("mail", "alumnejava@esliceu.net");
            attrs.put("sn", "COGNOMSJAVA");
            attrs.put("uid", "alumnejava");
            attrs.put("uidnumber", "11772");
            attrs.put("userpassword", "patata");

            this.context.createSubcontext(this.url+"/cn=userjava,ou=users,ou=accounts,dc=esliceu,dc=com", attrs);

            this.context.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}