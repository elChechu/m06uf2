package puntuacions;

import java.util.Date;

/**
 *
 * @author julian
 */
public class Puntuacio {
    
    private long id;
    private String nickname;
    private String titol;
    private int punts;
    private Date data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public int getPunts() {
        return punts;
    }

    public void setPunts(int punts) {
        this.punts = punts;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Puntuacio{" + "id=" + id + ", nickname=" + nickname + 
                ", titol=" + titol + ", punts=" + punts + ", data=" + data + '}';
    }
}
