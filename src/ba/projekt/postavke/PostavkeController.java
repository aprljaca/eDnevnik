package ba.projekt.postavke;

import ba.projekt.glavna.GlavnaController;
import ba.projekt.main.Main;
import ba.projekt.nastavnik.Nastavnik;
import ba.projekt.predmet.Predmet;
import ba.projekt.predmet.PredmetData;
import ba.projekt.prijava.PrijavaController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class PostavkeController {

    private Nastavnik nastavnik;

    public Label labelImeIPrezime;
    public Label labelObavijest;
    public TextField fieldIme;
    public TextField fieldPrezime;
    public TextField fieldKorisnickoIme;
    public PasswordField fieldStaraLozinka;
    public PasswordField fieldNovaLozinka;

    public Label fieldImeIPrezime;
    public Label fieldPredmet;
    public ChoiceBox choiceBox = new ChoiceBox();

    private PredmetData predmetData = new PredmetData();
    private Connection conncetion;

    private PostavkeData postavkeData = new PostavkeData();

    public PostavkeController(Nastavnik nastavnik){
        this.nastavnik = nastavnik;
        System.out.println(nastavnik.getLozinka());
    }

    @FXML
    public void initialize(){
        conncetion = predmetData.getConncetion();

        fieldIme.setText(nastavnik.getIme());
        fieldPrezime.setText(nastavnik.getPrezime());
        fieldImeIPrezime.setText(nastavnik.toString());
        fieldKorisnickoIme.setText(nastavnik.getKorisnickoIme());

        Predmet predmet = predmetData.dajPredmet(nastavnik.getPredmetId());
        fieldPredmet.setText(predmet.toString());
        labelObavijest.setText("");

        //choicebox
        choiceBox.getItems().addAll("Postavke", "Odjavi se");
        Platform.runLater(() -> {
            SkinBase<ChoiceBox<String>> skin = (SkinBase<ChoiceBox<String>>) choiceBox.getSkin();
            for (Node child : skin.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    if (label.getText().isEmpty()) {
                        label.setText(nastavnik.getIme() + " " + nastavnik.getPrezime());
                    }
                    return;
                }
            }
        });

        choiceBox.setOnAction((event) -> {
            int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = choiceBox.getSelectionModel().getSelectedItem();
            if(String.valueOf(selectedItem).equals("Postavke")){

            }
            else {
                closeWindow();
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/prijava.fxml"));
                PrijavaController ctrl = new PrijavaController();
                loader.setController(ctrl);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("Prijava");
                stage.setScene(new Scene(root, 525, 425));
                stage.show();
            }
        });
    }

    public void actionSpasiIzmjene(ActionEvent actionEvent){
        boolean sveOk = true;

        nastavnik.setIme(fieldIme.getText());
        nastavnik.setPrezime(fieldPrezime.getText());
        nastavnik.setKorisnickoIme(fieldKorisnickoIme.getText());

        if(!fieldStaraLozinka.getText().equals(nastavnik.getLozinka())){
            sveOk=false;
        }
        if(!sveOk) {
            labelObavijest.setText("Stara lozinka se ne poklapa!");
            return;
        }
        nastavnik.setLozinka(fieldNovaLozinka.getText());
        conncetion = postavkeData.getConncetion();
        postavkeData.izmijeniNastavnika(nastavnik);
        otvoriGlavnu();
    }

    public void actionOdustani(ActionEvent actionEvent){
        otvoriGlavnu();
    }

    private void closeWindow() {
        Stage stage = (Stage) choiceBox.getScene().getWindow();
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
}
