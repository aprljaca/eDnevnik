package ba.projekt.ocjena;

import java.time.LocalDate;
import java.util.Date;

public class Ocjena {
    private int id;
    private int ucenikId;
    private int predmetId;
    private int ocjena;
    private Date datum; //koristimo util pa cemo u ocjena data util konvertovati u sqldate
    private String komentar;

    public Ocjena(){}

    public Ocjena(int id, int ucenikId, int predmetId, int ocjena, Date datum, String komentar) {
        this.id = id;
        this.ucenikId = ucenikId;
        this.predmetId = predmetId;
        this.ocjena = ocjena;
        this.datum = datum;
        this.komentar = komentar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUcenikId() {
        return ucenikId;
    }

    public void setUcenikId(int ucenikId) {
        this.ucenikId = ucenikId;
    }

    public int getPredmetId() {
        return predmetId;
    }

    public void setPredmetId(int predmetId) {
        this.predmetId = predmetId;
    }

    public int getOcjena() {
        return ocjena;
    }

    public void setOcjena(int ocjena) {
        this.ocjena = ocjena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    /*@Override
    public String toString() {
        return "ocjena " + ocjena;
    }*/

    @Override
    public String toString() {
        return "Ocjena{" +
                "id=" + id +
                ", ucenikId=" + ucenikId +
                ", predmetId=" + predmetId +
                ", ocjena=" + ocjena +
                ", datum=" + datum +
                ", komentar='" + komentar + '\'' +
                '}';
    }
}
