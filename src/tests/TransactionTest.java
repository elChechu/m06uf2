package tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author julian
 */
public class TransactionTest {

    /*
    MySQL:
    
    CREATE TABLE `testdb`.`idvalor` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `valor` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`));    
    
    PostgreSQL:
    
    CREATE TABLE testdb.idvalor (
        id SERIAL PRIMARY KEY,
        valor CHARACTER VARYING(45)
    );    
     */
    private static final String USER = "root";
    private static final String PASSWORD = "yourpassword";

    private static Connection getConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/testdb";
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        props.setProperty("useSSL", "false");
        props.setProperty("serverTimezone", "UTC");
        props.setProperty("allowPublicKeyRetrieval", "true");
        return DriverManager.getConnection(url, props);
    }

    private static int insert(Connection conn, String valor) throws SQLException {

        int key = 0;

        String sql = "INSERT INTO idvalor (valor) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, valor);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    key = rs.getInt(1);
                }
            }
        }

        return key;
    }

    // PROVES
    public static void main(String[] args) throws SQLException {

        try (Connection conn = getConnection()) {
        
            System.out.println("========== without(false) ==========");
            testWithout(conn, false);
            
            System.out.println("========== without(true) ==========");
            testWithout(conn, true);
            
            System.out.println("========== with(false) ==========");
            testWith(conn, false);
            
            System.out.println("========== with(true) ==========");
            testWith(conn, true);
        }
    }

    private static void testWithout(Connection conn, boolean error) {

        try {   
            int id1 = insert(conn, "WITHOUT 1");
            System.out.println("added " + id1);
            if (error) {
                throw new SQLException("error thrown!");
            }
            int id2 = insert(conn, "WITHOUT 2");
            System.out.println("added " + id2);
            
        } catch (SQLException ex) {
            System.out.println("catched: " + ex.getMessage());
        }
    }

    private static void testWith(Connection conn, boolean error) throws SQLException {

        try {
            conn.setAutoCommit(false);
            System.out.println("started transaction");

            int id1 = insert(conn, "WITH 1");
            System.out.println("added " + id1);
            if (error) {
                throw new SQLException("error thrown!");
            }
            int id2 = insert(conn, "WITH 2");
            System.out.println("added " + id2);

            conn.commit();
            System.out.println("committed");

        } catch (SQLException ex) {
            System.out.println("catched: " + ex.getMessage());
            conn.rollback();
            System.out.println("rolled back");

        } finally {
            conn.setAutoCommit(true);
        }
    }
}
