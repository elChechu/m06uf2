package tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
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
    
    // CONNECTIONS

    private static Connection getMySQLConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/testdb";
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        props.setProperty("useSSL", "false");
        props.setProperty("serverTimezone", "UTC");
        props.setProperty("allowPublicKeyRetrieval", "true");
        return DriverManager.getConnection(url, props);
    }

    private static Connection getPostgresConnection() throws SQLException {
        
        String url = "jdbc:postgresql://localhost/testdb";
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        props.setProperty("ssl", "false");
        return DriverManager.getConnection(url, props);
    }
    
    // INSERT
    
    private static int insert(Connection conn, String valor) throws SQLException {

        String sql = "INSERT INTO idvalor (valor) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, valor);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                else {
                    throw new SQLException("key is missing");
                }
            }
        }
    }

    // TESTS
    
    public static void main(String[] args) throws SQLException {

        try (Connection conn = getMySQLConnection()) {
        
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
    
    // PRINT SQL EXCEPTIONS INFO
    
    public static void printSQLException(SQLException ex) {

        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {

                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                    System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                    System.err.println("Message: " + e.getMessage());

                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }

    public static boolean ignoreSQLException(String sqlState) {

        // add any checks here: if (sqlState.equalsIgnoreCase("X0Y32")) ...         
        return false;
    }
    
    // PRINT WARNINGS
    
    public static void getWarningsFromResultSet(ResultSet rs)
            throws SQLException {
        printWarnings(rs.getWarnings());
    }

    public static void getWarningsFromStatement(Statement stmt)
            throws SQLException {
        printWarnings(stmt.getWarnings());
    }

    public static void printWarnings(SQLWarning warning) {

        if (warning != null) {
            System.out.println("\n---Warning---\n");

            while (warning != null) {
                System.out.println("Message: " + warning.getMessage());
                System.out.println("SQLState: " + warning.getSQLState());
                System.out.print("Vendor error code: ");
                System.out.println(warning.getErrorCode());
                System.out.println("");
                warning = warning.getNextWarning();
            }
        }
    }
}
