package tasques;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julian
 */
public class TasquesDAO {
    
    private Connection getConnection() throws SQLException {        
        
        // implementa-ho!
        return null;
    }
    
    public void crearTasca(Tasca tasca) throws SQLException {        

        // implementa-ho!
    }
    
    public List<Tasca> trobarTotesLesTasques() throws SQLException {
        
        List<Tasca> tasques = new ArrayList();

        // implementa-ho!
        
        return tasques;
    }
}
