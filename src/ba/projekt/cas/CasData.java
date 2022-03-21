package ba.projekt.cas;

import java.sql.*;

public class CasData {
    public Connection connection;
    private PreparedStatement odrediIdCasaUpit, unesiCasUpit, odrediNastavniBrojCasaUpit;

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
            unesiCasUpit = connection.prepareStatement("INSERT INTO cas VALUES(?,?,?,?,?,?,?)");
            odrediIdCasaUpit = connection.prepareStatement("SELECT MAX(id) FROM cas");
            odrediNastavniBrojCasaUpit = connection.prepareStatement("SELECT * FROM cas WHERE razred_id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public int dajNastavniBrojCasa(int idRazreda){
        int brojac=0;
        try{
            odrediNastavniBrojCasaUpit.setInt(1, idRazreda);
            ResultSet rs = odrediNastavniBrojCasaUpit.executeQuery();
            while (rs.next()) {
                brojac++;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return brojac+1;
    }

    //public Cas(int id, Razred razred, Predmet predmet, Nastavnik nastavnik, Date datum, String tema, String napomena) {
    public void unesiCas(Cas cas){
        try {
            ResultSet rs = odrediIdCasaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
            unesiCasUpit.setInt(1, id);
            unesiCasUpit.setInt(2, cas.getRazred().getId());
            unesiCasUpit.setInt(3, cas.getPredmet().getId());
            unesiCasUpit.setInt(4, cas.getNastavnik().getId());
            //date util to date sql
            java.sql.Date sqlDate = new java.sql.Date(cas.getDatum().getTime());
            unesiCasUpit.setDate(5, sqlDate);
            unesiCasUpit.setString(6, cas.getTema());
            unesiCasUpit.setString(7, cas.getNapomena());
            unesiCasUpit.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}