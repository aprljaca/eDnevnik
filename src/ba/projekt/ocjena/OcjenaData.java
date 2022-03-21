package ba.projekt.ocjena;

import java.sql.*;
import java.util.ArrayList;

public class OcjenaData {
    public Connection connection;
    private PreparedStatement dajOcjeneUpit, dodajOcjenuUpit, odrediIdOcjeneUpit, izmijeniOcjenuUpit, obrisiOcjenuUpit;

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
            dajOcjeneUpit = connection.prepareStatement("SELECT * FROM ocjena WHERE ucenik_id=? AND predmet_id=?");
            dodajOcjenuUpit = connection.prepareStatement("INSERT INTO ocjena VALUES(?,?,?,?,?,?)");
            odrediIdOcjeneUpit = connection.prepareStatement("SELECT MAX(id) FROM ocjena");
            izmijeniOcjenuUpit = connection.prepareStatement("UPDATE ocjena SET ocjena=?, datum=?, komentar=? WHERE id=?");
            obrisiOcjenuUpit = connection.prepareStatement("DELETE FROM ocjena WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Ocjena dajOcjeneResultSet(ResultSet rs) throws SQLException {
        return new Ocjena(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDate(5), rs.getString(6));
    }

    public ArrayList<Ocjena> dajOcjene(int ucenikId, int predmetId){
        ArrayList<Ocjena> ocjene = new ArrayList<>();
        try{
            dajOcjeneUpit.setInt(1, ucenikId);
            dajOcjeneUpit.setInt(2, predmetId);
            ResultSet rs = dajOcjeneUpit.executeQuery();
            while (rs.next()){
                ocjene.add(dajOcjeneResultSet(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ocjene;
    }

    public void unesiOcjenu(Ocjena ocjena){
        try{
            ResultSet rs = odrediIdOcjeneUpit.executeQuery();
            int id = 1;
            if(rs.next()){
                id = rs.getInt(1) + 1;
            }
            dodajOcjenuUpit.setInt(1, id);
            dodajOcjenuUpit.setInt(2, ocjena.getUcenikId());
            dodajOcjenuUpit.setInt(3, ocjena.getPredmetId());
            dodajOcjenuUpit.setInt(4, ocjena.getOcjena());
            //date util to date sql
            java.sql.Date sqlDate = new java.sql.Date(ocjena.getDatum().getTime());
            dodajOcjenuUpit.setDate(5, sqlDate);
            dodajOcjenuUpit.setString(6, ocjena.getKomentar());
            dodajOcjenuUpit.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void izmijeniOcjenu(Ocjena ocjena){
        try{
            izmijeniOcjenuUpit.setInt(1, ocjena.getOcjena());
            java.sql.Date sqlDate = new java.sql.Date(ocjena.getDatum().getTime());
            izmijeniOcjenuUpit.setDate(2, sqlDate);
            izmijeniOcjenuUpit.setString(3, ocjena.getKomentar());
            izmijeniOcjenuUpit.setInt(4, ocjena.getId());
            izmijeniOcjenuUpit.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void obrisiOcjenu(Ocjena ocjena){
        try{
            obrisiOcjenuUpit.setInt(1, ocjena.getId());
            obrisiOcjenuUpit.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}