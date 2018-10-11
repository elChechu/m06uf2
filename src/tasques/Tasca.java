package tasques;

import java.util.Date;

/**
 * 
 CREATE TABLE `tasques` (
  `id` INT NOT NULL,
  `descripcio` VARCHAR(300) NOT NULL,
  `data_inici` DATETIME NULL,
  `data_final` DATETIME NOT NULL,
  `finalitzada` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`));
 *
 * @author julian
 */
public class Tasca {
    
    private int id;
    private String descripcio;
    private Date dataInici;
    private Date dataFinal;
    private boolean finalitzada;
    
    public Tasca() {        
    }
    
    public Tasca(int id, String descripcio, Date dataInici, Date dataFinal, 
            boolean finalitzada) {
        
        this.id = id;
        this.descripcio = descripcio;
        this.dataInici = dataInici;
        this.dataFinal = dataFinal;
        this.finalitzada = finalitzada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Date getDataInici() {
        return dataInici;
    }

    public void setDataInici(Date dataInici) {
        this.dataInici = dataInici;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public boolean isFinalitzada() {
        return finalitzada;
    }

    public void setFinalitzada(boolean finalitzada) {
        this.finalitzada = finalitzada;
    }

    @Override
    public String toString() {
        return "Tasca{" + "id=" + id + ", descripcio=" + descripcio + 
                ", dataInici=" + dataInici + ", dataFinal=" + dataFinal + 
                ", finalitzada=" + finalitzada + '}';
    }
}
