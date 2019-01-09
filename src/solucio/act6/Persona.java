package solucio.act6;

import java.util.List;

/**
 *
 * @author julian
 */
public class Persona {
    
    private String id;
    private String nom;
    private int edat;
    private List<String> mitjans;
    
    public Persona(String nom, int edat) {
        this(null, nom, edat, null);
    }
    
    public Persona(String id, String nom, int edat, List<String> mitjans) {
        this.id = id;
        this.nom = nom;
        this.edat = edat;
        this.mitjans = mitjans;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEdat() {
        return edat;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMitjans() {
        return mitjans;
    }

    public void setMitjans(List<String> mitjans) {
        this.mitjans = mitjans;
    }

    @Override
    public String toString() {
        return "Persona{" + "id=" + id + ", nom=" + nom + ", edat=" + edat + ", mitjans=" + mitjans + '}';
    }
}
