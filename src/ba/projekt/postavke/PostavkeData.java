package ba.projekt.postavke;

import ba.projekt.nastavnik.Nastavnik;

import java.sql.*;

public class PostavkeData {
    public Connection connection;
    private PreparedStatement izmijeniNastavnikaUpit;

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
            izmijeniNastavnikaUpit = connection.prepareStatement("UPDATE nastavnik SET ime=?, prezime=?, korisnicko_ime=?, lozinka=?, predmet_id=? WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Nastavnik dajNastavnikaResultSet(ResultSet rs) throws SQLException {
        return new Nastavnik(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
    }

    public void izmijeniNastavnika(Nastavnik nastavnik){
        try {
            izmijeniNastavnikaUpit.setString(1, nastavnik.getIme());
            izmijeniNastavnikaUpit.setString(2, nastavnik.getPrezime());
            izmijeniNastavnikaUpit.setString(3, nastavnik.getKorisnickoIme());
            izmijeniNastavnikaUpit.setString(4, nastavnik.getLozinka());
            izmijeniNastavnikaUpit.setInt(5, nastavnik.getPredmetId());
            izmijeniNastavnikaUpit.setInt(6, nastavnik.getId());
            izmijeniNastavnikaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}