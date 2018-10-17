package tasques;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 *
 * @author julian
 */
public class ProvaTasques {
    
    public static void main(String[] args) throws SQLException {
        
        // crear("estudiar java");
        trobarTots();
    }
    
    private static void trobarTots() throws SQLException {
        
        TasquesDAO tdao = new TasquesDAO();
        
        List<Tasca> tasques = tdao.trobarTotesLesTasques();
        for (Tasca tasca: tasques) {
            System.out.println(tasca);
        }
    }
    
    private static void crear(String descripcio) throws SQLException {
        
        TasquesDAO tdao = new TasquesDAO();
        
        Date dataInici = new GregorianCalendar(2018, Calendar.SEPTEMBER, 17).getTime();
        Date dataFinal = new GregorianCalendar(2019, Calendar.MAY, 31).getTime();        
        
        int id = tdao.ultimId() + 1;
        
        tdao.crearTasca(new Tasca(id, descripcio, dataInici, dataFinal, false));
    }
}
