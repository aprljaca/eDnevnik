package ba.projekt.prijava;

import ba.projekt.nastavnik.Nastavnik;

import java.sql.*;

public class PrijavaData {
    public Connection connection;
    private PreparedStatement dajSveNastavnikeUpit;

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
            dajSveNastavnikeUpit = connection.prepareStatement("SELECT * FROM nastavnik");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Nastavnik dajNastavnikaResultSet(ResultSet rs) throws SQLException {
        return new Nastavnik(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
    }

    public Nastavnik dajNastavnika(String korisnickoIme, String lozinka) {
        try{
            ResultSet rs = dajSveNastavnikeUpit.executeQuery();
            while(rs.next()){
                if(korisnickoIme.equals(rs.getString(4)) && lozinka.equals(rs.getString(5))){
                    Nastavnik nastavnik = dajNastavnikaResultSet(rs);
                    return nastavnik;
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}