package tasques;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author julian
 */
public class ProvaTasques {
    
    public static void main(String[] args) throws SQLException {
        
        // crearITrobar();
        trobarTots();
    }
    
    private static void crearITrobar() throws SQLException {
        
        TasquesDAO tdao = new TasquesDAO();
        
        int id = crear("una prova");
        Tasca tasca = tdao.trobarTasca(id);
        System.out.println(tasca);
    }
    
    private static void trobarTots() throws SQLException {
        
        TasquesDAO tdao = new TasquesDAO();
        
        List<Tasca> tasques = tdao.trobarTotesLesTasques();
        for (Tasca tasca: tasques) {
            System.out.println(tasca);
        }
    }
    
    private static int crear(String descripcio) throws SQLException {
        
        TasquesDAO tdao = new TasquesDAO();
        
        Date dataInici = new GregorianCalendar(2018, Calendar.SEPTEMBER, 17).getTime();
        Date dataFinal = new GregorianCalendar(2019, Calendar.MAY, 31).getTime();        
        
        int id = tdao.ultimId() + 1;        
        tdao.crearTasca(new Tasca(id, descripcio, dataInici, dataFinal, false));
        return id;
    }
}
