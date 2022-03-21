package ba.projekt.ucenik;

import java.util.Date;

public class Ucenik {
    private int id;
    private String ime;
    private String prezime;
    private int razredId;
    private int vladanje;
    private String otac;
    private String majka;
    private Date datumRodjenja;
    private String adresaStanovanja;
    private String kontaktTelefon;

    public Ucenik(int id, String ime, String prezime, int razredId, int vladanje, String otac, String majka, Date datumRodjenja, String adresaStanovanja, String kontaktTelefon) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.razredId = razredId;
        this.vladanje = vladanje;
        this.otac = otac;
        this.majka = majka;
        this.datumRodjenja = datumRodjenja;
        this.adresaStanovanja = adresaStanovanja;
        this.kontaktTelefon = kontaktTelefon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public int getRazredId() {
        return razredId;
    }

    public void setRazredId(int razredId) {
        this.razredId = razredId;
    }

    public int getVladanje() {
        return vladanje;
    }

    public void setVladanje(int vladanje) {
        this.vladanje = vladanje;
    }

    public String getOtac() {
        return otac;
    }

    public void setOtac(String otac) {
        this.otac = otac;
    }

    public String getMajka() {
        return majka;
    }

    public void setMajka(String majka) {
        this.majka = majka;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getAdresaStanovanja() {
        return adresaStanovanja;
    }

    public void setAdresaStanovanja(String adresaStanovanja) {
        this.adresaStanovanja = adresaStanovanja;
    }

    public String getKontaktTelefon() {
        return kontaktTelefon;
    }

    public void setKontaktTelefon(String kontaktTelefon) {
        this.kontaktTelefon = kontaktTelefon;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }
}
