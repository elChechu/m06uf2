package tests;

/**
 *
 * @author julian
 */
public class DatabaseTest {
    
    public static void loadDriver() throws ClassNotFoundException {
        
        Class.forName("org.postgresql.Driver");
    }
    
    public static void main(String[] args) throws ClassNotFoundException {
        
        loadDriver();
    }
}
