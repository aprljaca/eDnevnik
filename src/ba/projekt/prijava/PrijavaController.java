package ba.projekt.prijava;

import ba.projekt.admin.AdminController;
import ba.projekt.glavna.GlavnaController;
import ba.projekt.nastavnik.Nastavnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class PrijavaController {

    public TextField fieldKorisnickoIme;
    public PasswordField fieldLozinka;
    public Label labelObavijest;

    PrijavaData prijavaData = new PrijavaData();
    Connection conncetion = prijavaData.getConncetion();

    @FXML
    public void initialize(){
        labelObavijest.setText("");
    }

    public void actionPrijava(ActionEvent actionEvent){
        if(fieldKorisnickoIme.getText().equals("admin") && fieldLozinka.getText().equals("admin")){
            Stage stage = new Stage();
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin.fxml"));
                AdminController adminController = new AdminController();
                loader.setController(adminController);
                root = loader.load();
                stage.setTitle("Admin panel");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.setResizable(false);
                stage.show();
                closeWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Nastavnik nastavnik = prijavaData.dajNastavnika(fieldKorisnickoIme.getText(), fieldLozinka.getText());
        if(nastavnik != null){
            Stage stage = new Stage();
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"));
                GlavnaController glavnaController = new GlavnaController(nastavnik);
                loader.setController(glavnaController);
                root = loader.load();
                stage.setTitle("Glavna");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.setResizable(false);
                stage.show();
                closeWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            labelObavijest.setText("Pogrešno korisničko ime ili lozinka!");
        }
    }

    public void actionIzlaz(ActionEvent actionEvent){
        Stage stage = (Stage) fieldKorisnickoIme.getScene().getWindow();
        stage.close();
    }

    private void closeWindow() {
        Stage stage = (Stage) fieldKorisnickoIme.getScene().getWindow();
        stage.close();
    }
}
