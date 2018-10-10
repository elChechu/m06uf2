package tests;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author julian
 */
public class MySQLTest {
    
    public static void loadDriver() throws ClassNotFoundException {
        
        Class.forName("com.mysql.cj.jdbc.Driver");
    }
    
    public static void checkServer() throws IOException {
        
        Socket s = new Socket("127.0.0.1", 3306);
        s.close();
    }
    
    public static Connection getAConnection(String user, String password) throws SQLException {
        
        String url = "jdbc:mysql://localhost:3306/testdb";
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("useSSL", "false");
        props.setProperty("serverTimezone", "UTC");
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
            getAConnection("root", "yourpassword");
        } catch (SQLException ex) {
            System.out.println("connection failed");
            ex.printStackTrace();
            return;
        }
        
        System.out.println("life is good!");
    }
}
