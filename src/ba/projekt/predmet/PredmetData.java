package ba.projekt.predmet;

import ba.projekt.razred.Razred;

import java.sql.*;
import java.util.ArrayList;

public class PredmetData {
    public Connection connection;
    private PreparedStatement dajPredmetUpit, dajSvePredmeteUpit;

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
            dajPredmetUpit = connection.prepareStatement("SELECT * FROM predmet WHERE id=?");
            dajSvePredmeteUpit = connection.prepareStatement("SELECT * FROM predmet");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Predmet dajPredmetResultSet(ResultSet rs) throws SQLException {
        return new Predmet(rs.getInt(1), rs.getString(2));
    }

    public Predmet dajPredmet(int id){
        try{
            dajPredmetUpit.setInt(1, id);
            ResultSet rs = dajPredmetUpit.executeQuery();
            if(!rs.next()) return null;
            return dajPredmetResultSet(rs);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Predmet> sviPredmeti(){
        ArrayList<Predmet> predmeti = new ArrayList<>();
        try {
            ResultSet rs = dajSvePredmeteUpit.executeQuery();
            while(rs.next()){
                predmeti.add(dajPredmetResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return predmeti;
    }


}