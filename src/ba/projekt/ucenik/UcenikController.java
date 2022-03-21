package ba.projekt.ucenik;

import ba.projekt.razred.Razred;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UcenikController {

    private Ucenik ucenik;
    private Razred razred;

    public Label labelImeIPrezime;
    public Label labelNazivRazreda;
    public Label labelOtac;
    public Label labelMajka;
    public Label labelDatumRodjenja;
    public Label labelAdresaStanovanja;
    public Label labelKontaktTelefon;


    public UcenikController(Ucenik ucenik, Razred razred) {
        this.ucenik = ucenik;
        this.razred = razred;
    }

    @FXML
    public void initialize(){
        labelImeIPrezime.setText(ucenik.toString());
        labelNazivRazreda.setText(razred.getNaziv());
        labelOtac.setText(ucenik.getOtac());
        labelMajka.setText(ucenik.getMajka());
        labelDatumRodjenja.setText(new SimpleDateFormat("dd-MM-yyyy").format(ucenik.getDatumRodjenja()));
        labelAdresaStanovanja.setText(ucenik.getAdresaStanovanja());
        labelKontaktTelefon.setText(ucenik.getKontaktTelefon());
    }

    public void actionIzlaz(ActionEvent actionEvent){
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) labelImeIPrezime.getScene().getWindow();
        stage.close();
    }
}
