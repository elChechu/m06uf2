package puntuacions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

/**
 *
 * @author julian
 */
public class ProvaPuntuacions2 {
    
    public static void main(String[] args) throws IOException, SQLException {
        
        Puntuacions2DAO dao = new Puntuacions2DAO();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean end = false;
        
        System.out.println("write a command (? for help)");
        while (!end) {
            System.out.print("> ");
            String line = br.readLine();
            if (line == null) {
                continue;
            }
            
            String[] parts = line.split(" ");            
            if (parts.length == 0) {
                continue;
            }
            
            switch (parts[0]) {
                case "?":
                    System.out.println(
                    "jocs|jugs|top IDJOC|new NICK|del IDJUG|punts IDJOC IDJUG PUNTS|tops IDJUG|millors|exit");
                    break;
                
                case "new":
                    try {
                        String nick = getStrParameter(parts, 1);
                        int newId = dao.addJugador(parts[1]);
                        System.out.println("added " + newId);
                    
                    } catch (DuplicatedException ex) {                         
                        System.out.println("nick already exists");
                        
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                                     
                    break;
                    
                case "del":
                    try {
                        int idJugador = getIntParameter(parts, 1);
                        boolean deleted = dao.deleteJugador(idJugador);
                        System.out.println("deleted? " + deleted);
                        
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                    
                case "jocs":
                    System.out.println(dao.getAllJocs());
                    break;
                
                case "jugs":
                    System.out.println(dao.getAllJugadors());
                    break;
                
                case "top":
                    try {
                        int idJoc = getIntParameter(parts, 1);
                        for (Puntuacio p: dao.getTop10Joc(idJoc)) {
                            System.out.println(p);
                        }   
                        
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    
                    break;
                    
                case "punts": 
                    try {
                        int idJoc = getIntParameter(parts, 1);
                        int idJugador = getIntParameter(parts, 2);
                        int punts = getIntParameter(parts, 3);
                        
                        dao.addPuntuacio(idJoc, idJugador, punts);
                        for (Puntuacio p: dao.getTop10Joc(idJoc)) {
                            System.out.println(p);
                        }  
                        break;
                        
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    
                    break;
                    
                case "tops":
                    try {
                        int idJugador = getIntParameter(parts, 1);
                        for (Puntuacio p: dao.getTopsJugador(idJugador)) {
                            System.out.println(p);
                        }   
                        
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    
                    break;  
                    
                case "millors":
                    System.out.println(dao.getMillorsJugadors());
                    break;                    
                    
                case "exit":
                    end = true;
                    break;
                default:
                    System.out.println("what? (? for help)");
                    break;
            }
        }
    }
    
    private static String getStrParameter(String[] parts, int pos) throws IOException {
        
        if (pos >= parts.length) {
            throw new IOException("missing parameter " + pos);
        }
        
        return parts[pos];
    }
    
    private static int getIntParameter(String[] parts, int pos) throws IOException {
        
        if (pos >= parts.length) {
            throw new IOException("missing parameter " + pos);
        }
        
        try {
            return Integer.parseInt(parts[pos]);
        } catch (NumberFormatException ex) {
            throw new IOException("wrong number " + parts[pos]);
        }
    }
}
