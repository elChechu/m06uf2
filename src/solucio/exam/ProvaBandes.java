package solucio.exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author julian
 */
public class ProvaBandes {
    
    public static void main(String[] args) throws IOException {

        BandesDAO dao = new BandesDAO();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean end = false;

        System.out.println("write a command (? for help)");
        while (!end) {
            System.out.print("> ");
            String line = br.readLine();
            if (line == null) {
                continue;
            }

            String command;
            String[] params;
            
            int idxParams = line.indexOf(' ');
            if (idxParams != -1) {
                command = line.substring(0, idxParams);
                params = line.substring(idxParams + 1).split(",");
            }
            else {
                command = line;
                params = new String[]{};
            }
            
            try {
                end = processCommand(command, params, dao);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } 
        }
    }

    private static boolean processCommand(String command, String[] params, BandesDAO dao) throws IOException {

        boolean end = false;
        switch (command) {
            case "?":
                System.out.println("+banda NOM | -banda ID | banda ID | bandes | "
                        + "+music NOM,INSTRUMENT,IDBANDA | -music ID | exit");
                break;

            case "+banda":
                assertCount(params, 1);
                Banda newbanda = new Banda(getString(params, 0));
                System.out.println("+banda: " + dao.createBanda(newbanda));
                break;
                
            case "-banda":
                assertCount(params, 1);
                System.out.println("-banda: " + dao.deleteBanda(getInt(params, 0)));
                break;                
            
            case "banda":
                assertCount(params, 1);
                Banda foundbanda = dao.findBanda(getInt(params, 0));
                if (foundbanda != null) {
                    System.out.println(foundbanda);
                    for (Music music: dao.findMusics(foundbanda.getId())) {
                        System.out.println(music);
                    }
                }
                else {
                    System.out.println("no existeix la banda");
                }
                break;
                
            case "bandes":
                assertCount(params, 0);
                for (Banda banda: dao.findBandes()) {
                    System.out.println(banda);    
                }
                break;
           
            case "+music":
                assertCount(params, 3);
                foundbanda = dao.findBanda(getInt(params,2));
                if (foundbanda != null) {
                    Music newmusic = new Music(getString(params, 0), getString(params, 1));
                    System.out.println("+music: " + dao.createMusic(newmusic, foundbanda));
                }
                else {
                    System.out.println("no existeix la banda");
                }
                break;
            
            case "-music":
                assertCount(params, 1);
                System.out.println("-music: " + dao.deleteMusic(getInt(params, 0)));
                break;
                
            case "exit":
                end = true;
                break;
            default:
                System.out.println("what? (? for help)");
                break;
        }
        return end;
    }

    // UTILS
    
    private static String getString(String[] params, int pos) throws IOException {

        if (pos >= params.length) {
            throw new IOException("missing part " + pos);
        }

        return params[pos];
    }
    
    private static int getInt(String[] params, int pos) throws IOException {
        
        if (pos >= params.length) {
            throw new IOException("missing part " + pos);
        }
        
        try {
            return Integer.parseInt(params[pos]);
        } catch (NumberFormatException ex) {
            throw new IOException("wrong number " + params[pos]);
        }
    }

    private static void assertCount(String[] params, int count) throws IOException {

        if (params.length != count) {
            throw new IOException("wrong parameter count " + params.length + ": must be " + count);
        }
    }
    
}
