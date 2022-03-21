package ba.projekt.pismena;

import ba.projekt.glavna.GlavnaController;
import ba.projekt.nastavnik.Nastavnik;
import ba.projekt.predmet.Predmet;
import ba.projekt.razred.Razred;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class PismenaZadacaController {

    private  PismenaZadaca pismenaZadaca;

    private Razred razred;
    private Predmet predmet;
    private Nastavnik nastavnik;

    public Label labelNazivPredmeta;
    public Label labelNazivRazreda;
    public TextField fieldNaziv;
    public DatePicker datumPisanjaPicker;
    public DatePicker datumIspravkePicker;

    public PismenaZadacaController(Razred razred, Predmet predmet, Nastavnik nastavnik){
        this.razred = razred;
        this.predmet = predmet;
        this.nastavnik = nastavnik;
    }

    @FXML
    public void initialize(){
        labelNazivPredmeta.setText(predmet.getNaziv());
        labelNazivRazreda.setText(razred.getNaziv());
    }

    public void actionUnesiPismenuZadacu(ActionEvent actionEvent){
        if(pismenaZadaca == null){
            pismenaZadaca = new PismenaZadaca();
        }
        pismenaZadaca.setRazredId(razred.getId());
        pismenaZadaca.setPredmet(predmet);
        pismenaZadaca.setNaziv(fieldNaziv.getText());

        //localdate to date
        LocalDate localDate = datumPisanjaPicker.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        pismenaZadaca.setDatumPisanja(date);

        //localdate to date
        LocalDate localDate1 = datumIspravkePicker.getValue();
        Date date1 = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        pismenaZadaca.setDatumIspravke(date1);

        otvoriGlavnu();
    }

    public void actionOdustani(javafx.event.ActionEvent actionEvent){
        otvoriGlavnu();
    }

    private void closeWindow() {
        Stage stage = (Stage) labelNazivPredmeta.getScene().getWindow();
        stage.close();
    }

    public void otvoriGlavnu(){
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

    public PismenaZadaca getPismenaZadaca() {
        return pismenaZadaca;
    }
}
