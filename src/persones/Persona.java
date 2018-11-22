package persones;

/**
 *
 * @author julian
 */
public class Persona {
    
    private String id;
    private String nom;
    private int edat;
    
    public Persona(String nom, int edat) {
        this(null, nom, edat);
    }
    
    public Persona(String id, String nom, int edat) {
        this.id = id;
        this.nom = nom;
        this.edat = edat;
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

    @Override
    public String toString() {
        return "Persona{" + "id=" + id + ", nom=" + nom + ", edat=" + edat + '}';
    }
}
