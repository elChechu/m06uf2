package solucio.act34;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author julian
 */
public class ProvaCotxes {
    
    public static void main(String[] args) {
        
        try {
            prova();            
            
        } catch (IOException | RuntimeException ex) {
            ex.printStackTrace(System.out);
            
        } finally {
            CotxesDAO.exit();
        }
    }
    
    public static void prova() throws IOException {
        
        CotxesDAO dao = new CotxesDAO();
        
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
            } catch (NoResultException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    private static boolean processCommand(String[] parts, CotxesDAO dao) throws IOException {
        
        boolean end = false;
        switch (parts[0]) {
            case "?":
                System.out.println(
                        "mlist|mnew MAR|mfind MAR|mdel MAR|clist MAR|cnew MAR COT|cfind MAR COT|cdel MAR COT|vlist MAR COT|vnew MAR COT VAR|exit");
                break;
                
            case "mlist":
                assetParameterCount(parts, 1);
                List<Marca> marques = dao.findMarques();
                for (Marca marca: marques) {
                    System.out.println(marca);
                }
                break;
                
            case "mnew":
                assetParameterCount(parts, 2);
                String newName = getStrParameter(parts, 1);
                Marca marca = new Marca();
                marca.setMarca(newName);
                dao.createMarca(marca);
                System.out.println(marca);
                break;
                
            case "mdel":
                assetParameterCount(parts, 2);
                String delName = getStrParameter(parts, 1);
                boolean deleted = dao.deleteMarca(delName);
                System.out.println("deleted=" + deleted);
                break;
                
            case "mfind":
                assetParameterCount(parts, 2);
                String findName = getStrParameter(parts, 1);
                Marca foundMarca = dao.findMarca(findName);
                System.out.println(foundMarca);
                break;
                
            case "clist":
                assetParameterCount(parts, 2);
                String findMarca = getStrParameter(parts, 1);
                List<Cotxe> cotxes = dao.findCotxesMarca(findMarca);
                for (Cotxe cotxe: cotxes) {
                    System.out.println(cotxe);
                }
                break;
                
            case "cnew":
                assetParameterCount(parts, 3);
                findMarca = getStrParameter(parts, 1);
                String newModel = getStrParameter(parts, 2);
                Cotxe cotxe = dao.createCotxe(findMarca, newModel);
                System.out.println(cotxe);
                break;
                
            case "cdel":
                assetParameterCount(parts, 3);
                findMarca = getStrParameter(parts, 1);
                String findModel = getStrParameter(parts, 2);
                System.out.println("deleted=" + dao.deleteCotxe(findMarca, findModel));
                break;
                
            case "cfind":
                assetParameterCount(parts, 3);
                findMarca = getStrParameter(parts, 1);
                findModel = getStrParameter(parts, 2);
                System.out.println(dao.findCotxe(findMarca, findModel));
                break;                
                
            case "vlist":
                assetParameterCount(parts, 3);
                findMarca = getStrParameter(parts, 1);
                findModel = getStrParameter(parts, 2);
                List<Variant> variants = dao.findVariantsCotxe(findMarca, findModel);
                for (Variant variant: variants) {
                    System.out.println(variant);
                }
                break;
                
            case "vnew":
                assetParameterCount(parts, 4);
                findMarca = getStrParameter(parts, 1);
                findModel = getStrParameter(parts, 2);
                String newVariant = getStrParameter(parts, 3);
                Variant variant = dao.createVariant(findMarca, findModel, newVariant);
                System.out.println(variant);
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
    
    private static void assetParameterCount(String[] parts, int count) throws IOException {
        
        if (parts.length != count) {
            throw new IOException("wrong parameter count " + (parts.length - 1) + ": must be " + (count - 1));
        }
    }
}
