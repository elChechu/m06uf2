package solucio.exam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 
 CREATE TABLE `bandes` (
  `banda_id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) NOT NULL,
  PRIMARY KEY (`banda_id`)
);
CREATE TABLE `musics` (
  `music_id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) NOT NULL,
  `instrument` varchar(45) NOT NULL,
  `banda_id` int(11) NOT NULL,
  PRIMARY KEY (`music_id`),
  KEY `music_banda_fk_idx` (`banda_id`),
  CONSTRAINT `music_banda_fk` FOREIGN KEY (`banda_id`) REFERENCES `bandes` (`banda_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

 *
 * @author julian
 */
public class BandesDAO {
    
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
        return DriverManager.getConnection(url, props);
    }
    
    public Integer createBanda(Banda banda) {
        
        Integer key = null;
        String sql = "INSERT INTO bandes (nom) VALUES (?)";
        
        try (Connection conn = getConnection()) {            
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {                
                ps.setString(1, banda.getNom());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        key = rs.getInt(1);
                    }
                }
            }
            return key;
            
        } catch (SQLException e) {
            throw new RuntimeException("creant banda", e);
        }
    }
    
    public boolean deleteBanda(Integer bandaId) {
        
        String sql1 = "DELETE FROM musics WHERE banda_id = ?";
        String sql2 = "DELETE FROM bandes WHERE banda_id = ?";
        
        try (Connection conn = getConnection()) {
            
            boolean deleted;
            conn.setAutoCommit(false);
            
            try {
                try (PreparedStatement ps = conn.prepareStatement(sql1)) {                
                    ps.setInt(1, bandaId);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(sql2)) {                
                    ps.setInt(1, bandaId);
                    deleted = ps.executeUpdate() == 1;
                }

                conn.commit();
                return deleted;
                
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
                
            } finally {
                conn.setAutoCommit(true);
            }
        }  catch (SQLException e) {
            throw new RuntimeException("esborrant banda", e);
        }
        
    }
    
    public Banda findBanda(Integer bandaId) {
        
        String sql = "SELECT banda_id,nom FROM bandes WHERE banda_id = ?";
        
        try (Connection conn = getConnection()) { 
            try (PreparedStatement ps = conn.prepareStatement(sql)) { 
                ps.setInt(1, bandaId);                
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Banda(rs.getInt(1), rs.getString(2));
                    }
                    else {
                        return null;
                    }
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("cercant banda", e);
        }
        
    }
    
    public List<Banda> findBandes() {
        
        List<Banda> list = new ArrayList<>();
        
        try (Connection conn = getConnection()) {            
            try (Statement st = conn.createStatement()) {            
                try (ResultSet rs = st.executeQuery("SELECT banda_id,nom FROM bandes")) {
                    while (rs.next()) {
                        Banda banda = new Banda(rs.getInt(1), rs.getString(2));
                        list.add(banda);
                    }
                }
            }            
            return list;
            
        } catch (SQLException e) {
            throw new RuntimeException("cercant bandes", e);
        }
    }
    
    public List<Music> findMusics(Integer bandaId) {
        
        List<Music> list = new ArrayList<>();
        
        try (Connection conn = getConnection()) {            
            try (PreparedStatement ps = conn.prepareStatement("SELECT music_id,nom,instrument FROM musics WHERE banda_id = ?")) {
                ps.setInt(1, bandaId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Music music = new Music(rs.getInt(1), rs.getString(2), rs.getString(3));                        
                        list.add(music);
                    }
                }
            }            
            return list;
            
        } catch (SQLException e) {
            throw new RuntimeException("cercant musics", e);
        }
    }
    
    public Integer createMusic(Music music, Banda banda) {
        
        Integer key = null;
        String sql = "INSERT INTO musics (nom,instrument,banda_id) VALUES (?,?,?)";
        
        try (Connection conn = getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {                
                ps.setString(1, music.getNom());
                ps.setString(2, music.getInstrument());
                ps.setInt(3, banda.getId());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        key = rs.getInt(1);
                    }
                }
            }
            return key;
            
        } catch (SQLException e) {
            throw new RuntimeException("creant music", e);
        }
    }
    
    public boolean deleteMusic(Integer musicId) {
        
        String sql = "DELETE FROM musics WHERE music_id = ?";
        
        try (Connection conn = getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, musicId);
                return ps.executeUpdate() == 1;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("esborrant music", e);
        }
    }
}
