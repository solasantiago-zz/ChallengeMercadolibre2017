package challenge1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQL {
    
    private static Connection dbc;
    
    public Connection MySQL_Open_Connection(String user, String pass, String db_name) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbc = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name, user, pass);
            System.out.println("= Connected to MySQL =");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dbc;
    }
    
     public void MySQL_Close_Connection(Connection dbc) {
        try {
            dbc.close();
            System.out.println("= Closed MySQL connection =");
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public Connection createDBandTable(Connection dbc, String DB, String Table) {
        try {
            String Query = "CREATE DATABASE " + DB;
            Statement st = dbc.createStatement();
            st.executeUpdate(Query);
            MySQL_Close_Connection(dbc);
            dbc = MySQL_Open_Connection("root", "", DB);
            System.out.println("== Database " + DB + " successfully created ==");
            
            Query = "CREATE TABLE " + Table
                    + "(name CHAR (30) NOT NULL, surname CHAR (30) NOT NULL,"
                    + "mail CHAR (50) NOT NULL, username CHAR(45), changedpass BOOL );";
            st = dbc.createStatement();
            st.executeUpdate(Query);
            System.out.println("=== Table " + Table + " successfully created ===");
            
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dbc;
    }
    
    public void insertData(Connection dbc, String table_name, String name, String surname, String mail, String username) {
        try {
            String Query = "USE challenge1;";
            Statement st = dbc.createStatement();
            st.executeUpdate(Query);
            
            Query = "INSERT INTO `" + table_name + "` (`name`, `surname`, `mail`, `username`, `changedpass`) VALUES ("
                    + "'" + name + "', "
                    + "'" + surname + "', "
                    + "'" + mail + "', "
                    + "'" + username + "', "
                    + "'0');";
            st = dbc.createStatement();
            st.executeUpdate(Query);
            System.out.println("==== " + "MySQL status of " + username
                    + " user successfully entered ====");
            
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
