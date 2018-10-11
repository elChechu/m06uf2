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
        
        TasquesDAO tdao = new TasquesDAO();
        
        Date dataInici = new GregorianCalendar(2018, Calendar.SEPTEMBER, 17).getTime();
        Date dataFinal = new GregorianCalendar(2019, Calendar.MAY, 31).getTime();        
        
        int id = new Random().nextInt();
        
        tdao.crearTasca(new Tasca(id, "estudiar java", dataInici, dataFinal, false));
        
        List<Tasca> tasques = tdao.trobarTotesLesTasques();
        for (Tasca tasca: tasques) {
            System.out.println(tasca);
        }
    }
}
