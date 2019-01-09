package solucio.act1;

/**
 *
 * @author julian
 */
public class IdNom {
    
    private int id;
    private String nom;
    
    public IdNom(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "{" + id + "=" + nom + '}';
    }    
}
