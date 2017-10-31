package challenge1;

import com.novell.ldap.LDAPConnection;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSV_Reader {
    LDAP c = new LDAP();
    MySQL db = new MySQL();
    LDAPConnection lc = new LDAPConnection();
    Send_Mail sm = new Send_Mail();
    Connection dbc;
    
    void Data_Upload ( String getManager, String getPassword ) throws FileNotFoundException, IOException
    {
        String Line;
        FileReader File = new FileReader ( "prueba.csv" );                //  CSV file directory (Name, Surname, Mail)
        BufferedReader Buffer = new BufferedReader ( File );
        try {dbc = db.MySQL_Open_Connection ( "root", "", "" );} catch (Exception ex) 
                {Logger.getLogger(CSV_Reader.class.getName()).log(Level.SEVERE, null, ex);}
        lc = c.Connection_Manager ( getManager , getPassword );
        dbc = db.createDBandTable( dbc, "challenge1", "userstatus" );
        Buffer.readLine();  //  CSV header
        while ((Line = Buffer.readLine()) != null){
            String Data_Read [] = Line.split( ", " );
            String Username [] = Data_Read [2].split( "@" );        //  Username
            c.User_Data( lc, Data_Read [0], Data_Read [1], Data_Read [2], Username [0] );
            db.insertData( dbc, "userstatus", Data_Read [0], Data_Read [1], Data_Read [2], Username [0] );
            sm.sendmail(Username [0], "randompass", Data_Read [2]);
        }
        Buffer.close();
        c.Close_Connection( lc );
        db.MySQL_Close_Connection( dbc );
    }
}
