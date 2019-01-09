package solucio.act6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 *
 * @author julian
 */
public class ProvaPersones {
    
    public static void main(String[] args) throws IOException {
        
        PersonesDAO dao = new PersonesDAO();
        
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
            
            try {
                end = processCommand(parts, dao);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static boolean processCommand(String[] parts, PersonesDAO dao) throws IOException {
        
        String idStr;
        
        boolean end = false;
        switch (parts[0]) {
            case "?":
                System.out.println("new NOM EDAT|id ID|list|add ID MITJA|remove ID MITJA|exit");
                break;
            
            case "new":
                assertParameterCount(parts, 3);
                
                String nom = getStrParameter(parts, 1);
                int edat = getIntParameter(parts, 2);                
                Persona newp = new Persona(nom, edat);
                
                Object id = dao.createPersona(newp);
                System.out.println("created " + id);
                
                break;
                
            case "id":
                assertParameterCount(parts, 2);
                idStr = getStrParameter(parts, 1);
                
                Persona foundp = dao.findPersona(idStr);
                System.out.println("found " + foundp);
                
                break;                
                
            case "add":
            case "remove":
                assertParameterCount(parts, 3);
                idStr = getStrParameter(parts, 1);
                String mitja = getStrParameter(parts, 2);
               
                boolean result;
                if ("add".equals(parts[0])) {
                    result = dao.addMitja(idStr, mitja);
                }
                else {
                    result = dao.removeMitja(idStr, mitja);
                }
                System.out.println("changed is " + result);
                
                break;
                
            case "list":
                assertParameterCount(parts, 1);
                
                List<Persona> ps = dao.findAllPersones();
                for (Persona p: ps) {
                    System.out.println(p);
                }
                
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
    
    private static String getStrParameter(String[] parts, int pos) throws IOException {
        
        if (pos >= parts.length) {
            throw new IOException("missing part " + pos);
        }
        
        return parts[pos];
    }
    
    private static int getIntParameter(String[] parts, int pos) throws IOException {
        
        if (pos >= parts.length) {
            throw new IOException("missing part " + pos);
        }
        
        try {
            return Integer.parseInt(parts[pos]);
        } catch (NumberFormatException ex) {
            throw new IOException("wrong number " + parts[pos]);
        }
    }
    
    private static void assertParameterCount(String[] parts, int count) throws IOException {
        
        if (parts.length != count) {
            throw new IOException("wrong parameter count " + (parts.length - 1) + ": must be " + (count - 1));
        }
    }
}
