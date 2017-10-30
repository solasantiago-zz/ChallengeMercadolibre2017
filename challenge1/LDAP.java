package challenge1;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LDAP {
    int ldap_version = LDAPConnection.LDAP_V3 , ldap_port = 
            LDAPConnection.DEFAULT_PORT;    //  ldap_port = LDAPConnection.DEFAULT_SSL_PORT
    LDAPConnection lc;
    String login, ldap_host = "127.0.0.1";
    int Counter = 0;

    LDAPConnection Connection_Manager ( String getManager , String getPassword ) {
            login = "cn=" + getManager + ",dc=challenge1,dc=ml";
            try {
                    lc = new LDAPConnection();
                    lc.connect(ldap_host, ldap_port);
                    System.out.println("= Connected to the LDAP server =");
                    lc.bind(ldap_version, login, getPassword.getBytes("UTF8"));
                    System.out.println("= Authenticated on the server =");
            } catch (UnsupportedEncodingException | LDAPException ex) {
                    Logger.getLogger(LDAP.class.getName()).log(Level.SEVERE,null, ex);
            }

            return lc;		//	==Returns new LDAPConnection()==
    }

    LDAPConnection Connection_User ( String getUser , String getPassword ) {
            login = "iud=" + getUser + ",ou=group,dc=challenge1,dc=ml";

            try {
                    lc = new LDAPConnection();
                    lc.connect(ldap_host, ldap_port);
                    System.out.println("= Connected to the LDAP server =");
                    lc.bind(ldap_version, login, getPassword.getBytes("UTF8"));
                    System.out.println("= Authenticated on the server =");
            } catch ( UnsupportedEncodingException | LDAPException ex ) {
                    Logger.getLogger(LDAP.class.getName()).log(Level.SEVERE,null, ex);
            }

            return lc;		//	==Returns new LDAPConnection()==
    }
    
    void User_Data ( LDAPConnection lc, String getName, String getSurname, String getMail, String username )
    {
        try {
            LDAPAttributeSet Set = new LDAPAttributeSet();
            String name="l", surname="o", mail="ou", pass="host", changedpassword="description";
            
            Set.add(new LDAPAttribute("objectClass", "account"));   // inetOrgPerson throws error
            Set.add(new LDAPAttribute(name, getName));
            Set.add(new LDAPAttribute(surname, getSurname));
            Set.add(new LDAPAttribute(mail, getMail));
            Set.add(new LDAPAttribute(pass, "randompass"));         // generate encrypted password
            Set.add(new LDAPAttribute(changedpassword, "0"));       // boolean value, determines the user's status

            String dn = "uid=" + username + ",ou=group,dc=challenge1,dc=ml";
            LDAPEntry New_User = new LDAPEntry (dn, Set);

            lc.add ( New_User );
            Counter ++;
            System.out.println("==== " + Counter + ". LDAP user " + username
                    + " successfully entered ====");
        } catch ( LDAPException ex ) {
                    Logger.getLogger(LDAP.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    void Close_Connection ( LDAPConnection lc ) {
            try {
                    lc.disconnect();
                    System.out.println("== Closed LDAP connection ==");
            } catch ( LDAPException ex ) {
                    Logger.getLogger(LDAP.class.getName()).log(Level.SEVERE,null, ex);
            }
    }
}
