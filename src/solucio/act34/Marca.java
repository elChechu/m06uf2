package solucio.act34;

/*
CREATE TABLE `marques` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `marca` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `marca` (`marca`)
);
*/

/**
 *
 * @author julian
 */
public class Marca {
    
    private int id;
    private String marca;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "Marca{" + "id=" + id + ", marca=" + marca + '}';
    }
}
