package solucio.exam;

/**
 *
 * @author julian
 */
public class Music {
    
    private Integer id;
    private String nom;
    private String instrument;
    
    public Music(String nom, String instrument) {
        this(null, nom, instrument);
    }
    
    public Music(Integer id, String nom, String instrument) {
        this.id = id;
        this.nom = nom;
        this.instrument = instrument;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    @Override
    public String toString() {
        return "Music{" + "id=" + id + ", nom=" + nom + ", instrument=" + instrument + '}';
    }
}
