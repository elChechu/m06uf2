package tests;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author julian
 */
public class DatabaseTest {
    
    public static void loadDriver() throws ClassNotFoundException {
        
        Class.forName("org.postgresql.Driver");
    }
    
    public static void checkServer() throws IOException {
        
        Socket s = new Socket("127.0.0.1", 5432);
        s.close();
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
    }
}
