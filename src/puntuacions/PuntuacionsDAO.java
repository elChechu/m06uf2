package puntuacions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author julian
 */
public class PuntuacionsDAO {
    
    private Connection getConnection() throws SQLException {
        
        throw new RuntimeException("no implementat");
    }
   
    public List<IdNom> getAllJugadors() throws SQLException {
        
        throw new RuntimeException("no implementat");
    }
    
    public List<IdNom> getAllJocs() throws SQLException {
        
        throw new RuntimeException("no implementat");
    }
    
    public List<Puntuacio> getTop10Joc(int idJoc) throws SQLException {
        
        throw new RuntimeException("no implementat");
    }
    
    public void addPuntuacio(int idJoc, int idJugador, int punts) throws SQLException {
        
        throw new RuntimeException("no implementat");
    }
    
    public int addJugador(String nickname) throws SQLException {
        
        throw new RuntimeException("no implementat");
    }
    
    public boolean deleteJugador(int idJugador) throws SQLException {
        
        throw new RuntimeException("no implementat");
    }
}
