package com.esliceu.core.manager;

import org.springframework.stereotype.Service;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

@Service
public class LDAPManager {

    public void addUser() {
        String url = "ldap://localhost:389";
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=esliceu,dc=com");
        env.put(Context.SECURITY_CREDENTIALS, "test");

        try {

            DirContext ctx = new InitialDirContext(env);
            System.out.println("connected");
            System.out.println(ctx.getEnvironment());

            final Attributes container = new BasicAttributes();

            // Create the objectclass to add
            final Attribute objClasses = new BasicAttribute("objectClass");
            objClasses.add("inetOrgPerson");
            objClasses.add("person");
            objClasses.add("posixAccount");
            objClasses.add("top");
            objClasses.add("organizationalPerson");

            // Assign the username, first name, and last name
            final Attribute commonName = new BasicAttribute("cn", "TestUser");
            final Attribute email = new BasicAttribute("mail", "TestUser");
            final Attribute givenName = new BasicAttribute("givenName", "test1");
            final Attribute uid = new BasicAttribute("uid", "estudiantes");
            final Attribute surName = new BasicAttribute("sn", "test2");

            // Add password
            final Attribute userPassword = new BasicAttribute("userpassword", "test1");

            // Add these to the container
            container.put(objClasses);
            container.put(commonName);
            container.put(email);
            container.put(userPassword);

            ctx.createSubcontext(getUserDN("TestUser"), container);

            // do something useful with the context...

            ctx.close();

        } catch (AuthenticationNotSupportedException ex) {
            System.out.println("The authentication is not supported by the server");
        } catch (AuthenticationException ex) {
            System.out.println("incorrect password or username");
        } catch (NamingException ex) {
            System.out.println("error when trying to create the context");
        }
    }

    private static String getUserDN(final String userName) {
        String userDN = new StringBuffer().append("cn=").append(userName).append(",OU=users,OU=accounts,dc=esliceu,dc=com").toString();
        System.out.println(userDN);
        return userDN;
    }


}