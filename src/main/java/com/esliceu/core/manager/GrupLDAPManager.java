package com.esliceu.core.manager;

import com.esliceu.core.entity.Alumne;
import com.esliceu.core.entity.Grup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;
import java.util.List;

@Service
public class GrupLDAPManager {
    private DirContext context;
    private String url;
    private Hashtable<String, String> environment;

    @Autowired
    private GrupManager grupManager;

    public GrupLDAPManager(@Value("${LDAP_URL}") String urlLdap, @Value("${LDAP_ADMIN}") String admin, @Value("${LDAP_PASSWORD}") String password) throws NamingException {
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

    public void addGroup(List<Grup> grups) {
        try {
            BasicAttributes attrs = new BasicAttributes();

            Attribute classes = new BasicAttribute("objectclass");
            classes.add("posixGroup");
            classes.add("top");
            attrs.put(classes);
            for (Grup grup : grups) {
                attrs.put("gidnumber", grup.getCodi().toString());
                this.context.createSubcontext(this.url + "/cn=" + grup.getCurs().getDescripcio() + " " + grup.getNom() + ",ou=groups,dc=esliceu,dc=com", attrs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMembers(List<Alumne> alumnes) throws NamingException {
        String base = "ou=groups,dc=esliceu,dc=com";
        cleanGroups();
        for (Alumne alumne : alumnes) {
            ModificationItem[] mod = new ModificationItem[1];
            Attribute memberUidAttr = new BasicAttribute("memberUid", alumne.getUidNumberLDAP().toString());
            mod[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, memberUidAttr);
            context.modifyAttributes("cn=" + alumne.getGrup().getCurs().getDescripcio() + " " + alumne.getGrup().getNom() + "," + base, mod);
        }
    }

    //TODO Intentar no eliminar tot per evitar duplicats i aplicar modificacions
    private void cleanGroups() throws NamingException {
        List<Grup> grups = grupManager.findAll();
        for (Grup grup : grups) {
            String base = "cn="+grup.getCurs().getDescripcio()+" "+grup.getNom()+",ou=groups,dc=esliceu,dc=com";

            SearchControls sc = new SearchControls();
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String filter = "(memberUid=*)";
            System.out.println(base);
            NamingEnumeration results = this.context.search(base, filter, sc);
            while (results.hasMore()) {
                SearchResult sr = (SearchResult) results.next();
                Attributes attributes = sr.getAttributes();
                Attribute attribute = attributes.get("memberUid");
                ModificationItem[] mod = new ModificationItem[1];
                mod[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attribute);
                context.modifyAttributes(base, mod);
            }
        }
    }
}
