package ba.projekt.pismena;

import ba.projekt.predmet.Predmet;
import ba.projekt.predmet.PredmetData;
import ba.projekt.razred.Razred;

import java.sql.*;
import java.util.ArrayList;

public class PismenaZadacaData {
    public Connection connection;
    private PreparedStatement pismeneZadaceRazredaUpit, dodajPismenuZadacuUpit, odrediIdPismeneZadaceUpit, odrediRedniBrojUpit;

    public Connection getConncetion() {
        String dbName = "ednevnik";
        String userName = "root";
        String password = "admin";
        String url = "jdbc:mysql://localhost/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            pismeneZadaceRazredaUpit = connection.prepareStatement("SELECT * FROM pismena WHERE razred_id=?");
            dodajPismenuZadacuUpit = connection.prepareStatement("INSERT INTO pismena VALUES(?,?,?,?,?,?,?)");
            odrediIdPismeneZadaceUpit = connection.prepareStatement("SELECT MAX(id) FROM pismena");
            odrediRedniBrojUpit = connection.prepareStatement("SELECT MAX(redni_broj) FROM pismena WHERE razred_id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public PismenaZadaca dajPismenuZadacuResultSet(ResultSet rs) throws SQLException {
        PredmetData predmetData = new PredmetData();
        connection = predmetData.getConncetion();
        Predmet predmet  = predmetData.dajPredmet(rs.getInt(3));
        connection = getConncetion();
        return new PismenaZadaca(rs.getInt(1), rs.getInt(2), predmet, rs.getInt(4), rs.getString(5), rs.getDate(6), rs.getDate(7));

    }

    public ArrayList<PismenaZadaca> pismeneZadaceRazreda(Razred razred){
        ArrayList<PismenaZadaca> pismene = new ArrayList<>();
        try {
            pismeneZadaceRazredaUpit.setInt(1, razred.getId());
            ResultSet rs = pismeneZadaceRazredaUpit.executeQuery();
            while(rs.next()){
                pismene.add(dajPismenuZadacuResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pismene;
    }

    public void dodajPismenuZadacu(PismenaZadaca pismenaZadaca){
        try {
            ResultSet rs = odrediIdPismeneZadaceUpit.executeQuery();
            int id = 1;
            if(rs.next()){
                id = rs.getInt(1) +1;
            }
            dodajPismenuZadacuUpit.setInt(1, id);
            dodajPismenuZadacuUpit.setInt(2, pismenaZadaca.getRazredId());
            dodajPismenuZadacuUpit.setInt(3, pismenaZadaca.getPredmet().getId());

            odrediRedniBrojUpit.setInt(1, pismenaZadaca.getRazredId());
            ResultSet rs1 = odrediRedniBrojUpit.executeQuery();
            int rb = 1;
            if(rs1.next()){
                rb = rs1.getInt(1) +1;
            }
            System.out.println(rb);
            dodajPismenuZadacuUpit.setInt(4, rb);
            dodajPismenuZadacuUpit.setString(5, pismenaZadaca.getNaziv());
            java.sql.Date sqlDatumPisanja = new java.sql.Date(pismenaZadaca.getDatumPisanja().getTime());
            dodajPismenuZadacuUpit.setDate(6, sqlDatumPisanja);
            java.sql.Date sqlDatumIspravke = new java.sql.Date(pismenaZadaca.getDatumIspravke().getTime());
            dodajPismenuZadacuUpit.setDate(7, sqlDatumIspravke);
            dodajPismenuZadacuUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

