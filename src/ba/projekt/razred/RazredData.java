package ba.projekt.razred;

import java.sql.*;
import java.util.ArrayList;

public class RazredData {
    public Connection connection;
    private PreparedStatement dajSveRazredeUpit;

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
            dajSveRazredeUpit = connection.prepareStatement("SELECT * FROM razred");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Razred dajRazredResultSet(ResultSet rs) throws SQLException {
        return new Razred(rs.getInt(1), rs.getString(2));
    }

    public ArrayList<Razred> sviRazredi(){
        ArrayList<Razred> razredi = new ArrayList<>();
        try {
            ResultSet rs = dajSveRazredeUpit.executeQuery();
            while(rs.next()){
                razredi.add(dajRazredResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return razredi;
    }
}