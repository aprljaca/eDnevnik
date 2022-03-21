package ba.projekt.ucenik;

import ba.projekt.razred.Razred;

import java.sql.*;
import java.util.ArrayList;

public class UcenikData {
    public Connection connection;
    private PreparedStatement dajUcenikeRazredaUpit, dajSveUcenike;

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
            dajSveUcenike = connection.prepareStatement("SELECT * FROM ucenik");
            dajUcenikeRazredaUpit = connection.prepareStatement("SELECT * FROM ucenik WHERE razred_id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Ucenik dajUcenikaResultSet(ResultSet rs) throws SQLException {
        return new Ucenik(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getDate(8), rs.getString(9), rs.getString(10));
    }

    public ArrayList<Ucenik> uceniciRazreda(Razred razred){
        ArrayList<Ucenik> ucenici = new ArrayList<>();
        try {
            dajUcenikeRazredaUpit.setInt(1, razred.getId());
            ResultSet rs = dajUcenikeRazredaUpit.executeQuery();
            while(rs.next()){
                ucenici.add(dajUcenikaResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ucenici;
    }

    public ArrayList<Ucenik> sviUcenici(){
        ArrayList<Ucenik> ucenici = new ArrayList<>();
        try {
            ResultSet rs = dajSveUcenike.executeQuery();
            while(rs.next()){
                ucenici.add(dajUcenikaResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ucenici;
    }

}
