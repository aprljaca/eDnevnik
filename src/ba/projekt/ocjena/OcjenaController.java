package ba.projekt.ocjena;

import ba.projekt.glavna.GlavnaController;
import ba.projekt.nastavnik.Nastavnik;
import ba.projekt.predmet.Predmet;
import ba.projekt.razred.Razred;
import ba.projekt.ucenik.Ucenik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class OcjenaController {

    OcjenaData ocjenaData = new OcjenaData();
    Connection conncetion = ocjenaData.getConncetion();

    private Ucenik ucenik;
    private Predmet predmet;
    private Nastavnik nastavnik;
    private Razred razred;
    private Ocjena ocjena;

    public Label labelImeIPrezime;
    public Label labelNazivRazreda;
    public DatePicker datumPicker;
    public TextArea areaKomentar;

    public RadioButton radioButton1;
    public RadioButton radioButton2;
    public RadioButton radioButton3;
    public RadioButton radioButton4;
    public RadioButton radioButton5;
    ToggleGroup radioGroup = new ToggleGroup();

    public OcjenaController(Ocjena ocjena, Ucenik ucenik, Predmet predmet, Nastavnik nastavnik, Razred razred) {
        this.ocjena = ocjena;
        this.ucenik = ucenik;
        this.predmet = predmet;
        this.nastavnik = nastavnik;
        this.razred = razred;
    }

    @FXML
    public void initialize(){

        if(ocjena != null) {
            if(ocjena.getOcjena()==1){
                radioButton1.setSelected(true);
            } else if(ocjena.getOcjena()==2){
                radioButton2.setSelected(true);
            } else if(ocjena.getOcjena()==3){
                radioButton3.setSelected(true);
            } else if(ocjena.getOcjena()==4){
                radioButton4.setSelected(true);
            } else {
                radioButton5.setSelected(true);
            }
            areaKomentar.setText(ocjena.getKomentar());
        }

        radioButton1.setToggleGroup(radioGroup);
        radioButton2.setToggleGroup(radioGroup);
        radioButton3.setToggleGroup(radioGroup);
        radioButton4.setToggleGroup(radioGroup);
        radioButton5.setToggleGroup(radioGroup);

        labelImeIPrezime.setText(ucenik.toString());
        labelNazivRazreda.setText(razred.toString());
    }

    public void actionUnesiOcjenu(ActionEvent actionEvent){
        int izabranaOcjena;
        if(radioButton1.isSelected()){
            izabranaOcjena=1;
        }
        else if (radioButton2.isSelected()){
            izabranaOcjena=2;
        }
        else if (radioButton3.isSelected()){
            izabranaOcjena=3;
        }
        else if (radioButton4.isSelected()){
            izabranaOcjena=4;
        }
        else {
            izabranaOcjena=5;
        }

        if(ocjena == null) ocjena = new Ocjena();
        ocjena.setUcenikId(ucenik.getId());
        ocjena.setPredmetId(predmet.getId());
        ocjena.setOcjena(izabranaOcjena);
        //localdate to date
        LocalDate localDate = datumPicker.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        ocjena.setDatum(date);
        ocjena.setKomentar(areaKomentar.getText());
        otvoriGlavnu();
    }

    public void actionOdustani(ActionEvent actionEvent){
        otvoriGlavnu();
    }

    private void closeWindow() {
        Stage stage = (Stage) labelImeIPrezime.getScene().getWindow();
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

    public Ocjena getOcjena() {
        return ocjena;
    }
}
