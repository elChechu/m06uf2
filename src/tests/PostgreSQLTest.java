package tests;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author julian
 */
public class PostgreSQLTest {
    
    public static void loadDriver() throws ClassNotFoundException {
        
        Class.forName("org.postgresql.Driver");
    }
    
    public static void checkServer() throws IOException {
        
        Socket s = new Socket("127.0.0.1", 5432);
        s.close();
    }
    
    public static Connection getAConnection(String user, String password) throws SQLException {
        
        String url = "jdbc:postgresql://localhost/testdb";
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("ssl", "false");
        return DriverManager.getConnection(url, props);
    }
    
    public static void main(String[] args) {
        
        try {
            loadDriver();
        } catch (ClassNotFoundException ex) {
            System.out.println("driver is not available");
            ex.printStackTrace();
            return;
        }
        
        try {
            checkServer();
        } catch (IOException ex) {
            System.out.println("server is not running");
            ex.printStackTrace();
            return;
        }
        
        try {
            getAConnection("postgres", "yourpassword");
        } catch (SQLException ex) {
            System.out.println("connection failed");
            ex.printStackTrace();
            return;
        }
        
        System.out.println("life is good!");
    }
}
