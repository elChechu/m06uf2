package solucio.act1;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author julian
 */
public class PuntuacionsDAO {
    
    private static final String USER = "root";
    private static final String PASSWORD = "yourpassword";
    
    private Connection getConnection() throws SQLException {
        
        String url = "jdbc:mysql://localhost:3306/testdb";
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        props.setProperty("useSSL", "false");
        props.setProperty("serverTimezone", "UTC");
        props.setProperty("allowPublicKeyRetrieval", "true");
        props.setProperty("dumpQueriesOnException", "true");
        return DriverManager.getConnection(url, props);
    }
   
    public List<IdNom> getAllJugadors() throws SQLException {
        
        return getAllIdNoms("id_jugador", "nickname", "jugador");
    }
    
    public List<IdNom> getAllJocs() throws SQLException {
        
        return getAllIdNoms("id_joc", "titol", "joc");
    }
    
    public List<Puntuacio> getTop10Joc(int idJoc) throws SQLException {
        
        String sql = "SELECT puntuacio_id, nickname, titol, punts, data FROM puntuacio, joc, jugador "
                + "WHERE joc.id_joc = puntuacio.joc_id AND jugador.id_jugador = puntuacio.jugador_id AND joc.id_joc = ? ORDER BY punts DESC LIMIT 10";
        List<Puntuacio> puntuacions = new ArrayList<>();
        
        try (Connection conn = getConnection()) {            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {     
                ps.setInt(1, idJoc);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Puntuacio p = buildPuntuacio(rs);
                        puntuacions.add(p);
                    }
                }
            }            
        }
        
        return puntuacions;
    }

    public void addPuntuacio(int idJoc, int jdJugador, int punts) throws SQLException {
        
        String sql = "INSERT INTO puntuacio (joc_id, jugador_id, punts, data) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection()) {            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {                
                ps.setInt(1, idJoc);
                ps.setInt(2, jdJugador);
                ps.setInt(3, punts);
                ps.setDate(4, new java.sql.Date(new Date().getTime()));
                ps.executeUpdate();
            }
        }
    }
    
    public int addJugador(String nickname) throws SQLException, DuplicatedException {
        
        int key = 0;
        
        String sql = "INSERT INTO jugador (nickname) VALUES (?)";
        try (Connection conn = getConnection()) {            
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {                
                ps.setString(1, nickname);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        key = rs.getInt(1);
                    }
                }
            } catch (MySQLIntegrityConstraintViolationException e) {
                throw new DuplicatedException();
            }
        }
        
        return key;
    }
    
    public boolean deleteJugador(int idJugador) throws SQLException {
        
        String sql1 = "DELETE FROM puntuacio WHERE jugador_id = ?";
        String sql2 = "DELETE FROM jugador WHERE id_jugador = ?";
        
        boolean deleted = false;
        
        try (Connection conn = getConnection()) {
            
            conn.setAutoCommit(false);
            
            try {
                try (PreparedStatement ps = conn.prepareStatement(sql1)) {                
                    ps.setInt(1, idJugador);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(sql2)) {                
                    ps.setInt(1, idJugador);
                    deleted = ps.executeUpdate() == 1;
                }

                conn.commit();
                
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
                
            } finally {
                conn.setAutoCommit(true);
            }
        } 
        
        return deleted;
    }
    
    // NEW METHODS
    
    public List<Puntuacio> getTopsJugador(int idJugador) throws SQLException {
        
        String sql = "SELECT p.puntuacio_id, nickname, titol, p.punts, data FROM puntuacio p, joc, jugador, " +
            "(SELECT MAX(punts) punts, puntuacio_id FROM puntuacio WHERE jugador_id = ? GROUP BY joc_id) AS pi " +
            "WHERE p.punts = pi.punts AND p.jugador_id = ? AND joc.id_joc = p.joc_id AND jugador.id_jugador = p.jugador_id " + 
            "ORDER BY p.joc_id ASC";                
        List<Puntuacio> puntuacions = new ArrayList<>();
        
        try (Connection conn = getConnection()) {            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {     
                ps.setInt(1, idJugador);
                ps.setInt(2, idJugador);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Puntuacio p = buildPuntuacio(rs);
                        puntuacions.add(p);
                    }
                }
            }            
        }
        
        return puntuacions;
    }
    
    public List<IdNom> getMillorsJugadors() throws SQLException {
        
        String sql = "SELECT p.joc_id, nickname FROM puntuacio p, joc, jugador, " +
            "(SELECT MAX(punts) punts, puntuacio_id FROM puntuacio GROUP BY joc_id) AS pi " +
            "WHERE p.punts = pi.punts AND joc.id_joc = p.joc_id AND jugador.id_jugador = p.jugador_id " +
            "ORDER BY p.joc_id ASC";                
        List<IdNom> idNoms = new ArrayList<>();
        
        try (Connection conn = getConnection()) {            
            try (Statement st = conn.createStatement()) {            
                try (ResultSet rs = st.executeQuery(sql)) {
                    while (rs.next()) {
                        idNoms.add(new IdNom(rs.getInt(1), rs.getString(2)));
                    }
                }
            }            
        }
        
        return idNoms;
    }    
    
    // PRIVATE
        
    private List<IdNom> getAllIdNoms(String idLabel, String nomLabel, String tableName) throws SQLException {
        
        String sql = "SELECT " + idLabel + ", " + nomLabel + " FROM " + tableName;
        List<IdNom> idNoms = new ArrayList<>();
        
        try (Connection conn = getConnection()) {            
            try (Statement st = conn.createStatement()) {            
                try (ResultSet rs = st.executeQuery(sql)) {
                    while (rs.next()) {
                        idNoms.add(new IdNom(rs.getInt(idLabel), rs.getString(nomLabel)));
                    }
                }
            }            
        }
        
        return idNoms;
    }
    
    private Puntuacio buildPuntuacio(final ResultSet rs) throws SQLException {
        Puntuacio p = new Puntuacio();
        p.setId(rs.getInt("puntuacio_id"));
        p.setNickname(rs.getString("nickname"));
        p.setTitol(rs.getString("titol"));
        p.setPunts(rs.getInt("punts"));
        p.setData(rs.getDate("data"));
        return p;
    }    

}
