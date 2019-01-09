package solucio.act34;

/*
CREATE TABLE `variants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `variant` varchar(30) NOT NULL,
  `cotxe_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `variant_idx` (`cotxe_id`,`variant`),
  KEY `cotxe_fk_idx` (`cotxe_id`),
  CONSTRAINT `cotxe_fk` FOREIGN KEY (`cotxe_id`) REFERENCES `cotxes` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
);
*/

/**
 *
 * @author julian
 */
public class Variant {
    
    private int id;
    private String variant;
    private Cotxe cotxe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public Cotxe getCotxe() {
        return cotxe;
    }

    public void setCotxe(Cotxe cotxe) {
        this.cotxe = cotxe;
    }

    @Override
    public String toString() {
        return "Variant{" + "id=" + id + ", variant=" + variant /* + ", cotxe=" + cotxe */ + '}';
    }
}
