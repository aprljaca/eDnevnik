package ba.projekt.pismena;

import ba.projekt.predmet.Predmet;

import java.util.Date;

public class PismenaZadaca {
    private int id;
    private int razredId;
    private Predmet predmet;
    private int redniBroj;
    private String naziv;
    private Date datumPisanja;
    private Date datumIspravke;

    public PismenaZadaca(){}

    public PismenaZadaca(int id, int razredId, Predmet predmet, int redniBroj, String naziv, Date datumPisanja, Date datumIspravke) {
        this.id = id;
        this.razredId = razredId;
        this.predmet = predmet;
        this.redniBroj = redniBroj;
        this.naziv = naziv;
        this.datumPisanja = datumPisanja;
        this.datumIspravke = datumIspravke;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRazredId() {
        return razredId;
    }

    public void setRazredId(int razredId) {
        this.razredId = razredId;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public int getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(int redniBroj) {
        this.redniBroj = redniBroj;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Date getDatumPisanja() {
        return datumPisanja;
    }

    public void setDatumPisanja(Date datumPisanja) {
        this.datumPisanja = datumPisanja;
    }

    public Date getDatumIspravke() {
        return datumIspravke;
    }

    public void setDatumIspravke(Date datumIspravke) {
        this.datumIspravke = datumIspravke;
    }

    @Override
    public String toString() {
        return "PismenaZadaca{" +
                "id=" + id +
                ", razredId=" + razredId +
                ", predmet=" + predmet +
                ", redniBroj=" + redniBroj +
                ", naziv='" + naziv + '\'' +
                ", datumPisanja=" + datumPisanja +
                ", datumIspravke=" + datumIspravke +
                '}';
    }
}
