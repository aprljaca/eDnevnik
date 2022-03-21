package ba.projekt.cas;

import ba.projekt.nastavnik.Nastavnik;
import ba.projekt.predmet.Predmet;
import ba.projekt.razred.Razred;
import java.util.Date;

public class Cas {
    private int id;
    private Razred razred;
    private Predmet predmet;
    private Nastavnik nastavnik;
    private Date datum;
    private String tema;
    private String napomena;

    public Cas(){}

    public Cas(int id, Razred razred, Predmet predmet, Nastavnik nastavnik, Date datum, String tema, String napomena) {
        this.id = id;
        this.razred = razred;
        this.predmet = predmet;
        this.nastavnik = nastavnik;
        this.datum = datum;
        this.tema = tema;
        this.napomena = napomena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Razred getRazred() {
        return razred;
    }

    public void setRazred(Razred razred) {
        this.razred = razred;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public Nastavnik getNastavnik() {
        return nastavnik;
    }

    public void setNastavnik(Nastavnik nastavnik) {
        this.nastavnik = nastavnik;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    @Override
    public String toString() {
        return "Cas{" +
                "razred=" + razred +
                ", predmet=" + predmet +
                ", nastavnik=" + nastavnik +
                ", datum=" + datum +
                ", tema='" + tema + '\'' +
                ", napomena='" + napomena + '\'' +
                '}';
    }
}
