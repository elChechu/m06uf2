package tasques;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author julian
 */
public class TasquesDAO {
    
    private static final String USER = "root";
    private static final String PASSWORD = "seasonsend";
    
    private Connection getConnection() throws SQLException {        
        
        String url = "jdbc:mysql://localhost:3306/testdb";
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        props.setProperty("useSSL", "false");
        props.setProperty("serverTimezone", "UTC");
        props.setProperty("allowPublicKeyRetrieval", "true");
        return DriverManager.getConnection(url, props);
    }
    
    public void crearTasca(Tasca tasca) throws SQLException {        

        // implementa-ho!
    }
    
    public int ultimId() throws SQLException {
        
        // implementa-ho!
        return 0;
    }
    
    public List<Tasca> trobarTotesLesTasques() throws SQLException {
        
        List<Tasca> tasques = new ArrayList();

        Connection conn = getConnection();
        
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM tasques");
        while (result.next()) {

            Tasca tasca = new Tasca();
            tasca.setId(result.getInt("id"));
            tasca.setDescripcio(result.getString("descripcio"));
            tasca.setDataInici(result.getDate("data_inici"));
            tasca.setDataFinal(result.getDate("data_final"));
            tasca.setFinalitzada(result.getBoolean("finalitzada"));

            tasques.add(tasca);
        }
        
        conn.close();
        
        return tasques;
    }
}
