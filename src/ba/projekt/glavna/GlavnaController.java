package ba.projekt.glavna;

import ba.projekt.cas.Cas;
import ba.projekt.cas.CasData;
import ba.projekt.main.Main;
import ba.projekt.nastavnik.Nastavnik;
import ba.projekt.ocjena.Ocjena;
import ba.projekt.ocjena.OcjenaController;
import ba.projekt.ocjena.OcjenaData;
import ba.projekt.pismena.PismenaZadaca;
import ba.projekt.pismena.PismenaZadacaController;
import ba.projekt.pismena.PismenaZadacaData;
import ba.projekt.postavke.PostavkeController;
import ba.projekt.predmet.Predmet;
import ba.projekt.predmet.PredmetData;
import ba.projekt.prijava.PrijavaController;
import ba.projekt.razred.Razred;
import ba.projekt.razred.RazredData;
import ba.projekt.ucenik.Ucenik;
import ba.projekt.ucenik.UcenikController;
import ba.projekt.ucenik.UcenikData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class GlavnaController {

    private Nastavnik nastavnik;
    private Razred razred;
    private Ucenik ucenik;
    private Predmet predmet;
    private PredmetData predmetData = new PredmetData();
    private Connection conncetion;

    private RazredData razredData = new RazredData();
    public TableView<Razred> tableViewRazredi;
    public TableColumn colRazredNaziv;
    private ObservableList<Razred> listRazredi;

    private UcenikData ucenikData = new UcenikData();
    public ListView<Ucenik> listViewUcenici;
    public ListView<Ucenik> listViewPrisustvoUcenici;
    public ObservableList<Ucenik> listUcenici;

    public TableView<Ucenik> tableViewUcenici;
    public TableColumn colUcenikIme;
    public TableColumn colUcenikPrezime;
    public TableColumn colUcenikZakljucnaOcjena;
    public TableColumn colUcenikvladanje;

    private OcjenaData ocjenaData = new OcjenaData();
    public TableView<Ocjena> tableViewOcjene;
    public TableColumn colOcjenaOcjena;
    public TableColumn colOcjenaDatum;
    public TableColumn colOcjenaKomentar;
    public ObservableList<Ocjena> listOcjene;

    public ChoiceBox choiceBox = new ChoiceBox();

    private CasData casData = new CasData();

    public Label labelNastavniRadCasa;

    public Label labelNazivRazreda;
    public Label labelNazivPredmeta;
    public Label labelImeIPrezimeNastavnika;
    public Label labelDatum;
    public Label labelObavijest;
    public TextArea areaTema;
    public TextArea areaNapomena;

    public Label labelNastavniRadCasa1;

    public Label labelNazivRazreda1;
    public Label labelNazivPredmeta1;
    public Label labelImeIPrezimeNastavnika1;
    public Label labelDatum1;
    public Label labelObavijest1;
    public Label labelObavjestenje;

    private PismenaZadacaData pismenaZadacaData = new PismenaZadacaData();
    public  ObservableList<PismenaZadaca> listPismeneZadace;

    public TableView<PismenaZadaca> tableViewPismene;
    public TableColumn colPismenaRedniBroj;
    public TableColumn colPismenaNaziv;
    public TableColumn colPismenaDatumPisanja;
    public TableColumn colPismenaDatumIspravke;
    public TableColumn colPismenaPredmet;

    public GlavnaController(Nastavnik nastavnik) {
        this.nastavnik = nastavnik;
        //dobijamo predmet od trenutnog nastavnika
        int idPredmeta = nastavnik.getPredmetId();
        conncetion = predmetData.getConncetion();
        predmet = predmetData.dajPredmet(idPredmeta);

        conncetion = razredData.getConncetion();
        listRazredi = FXCollections.observableArrayList(razredData.sviRazredi());
    }

    @FXML
    public void initialize(){
        if(razred == null) {
            labelObavjestenje.setText("Odaberite razred!");
        }
        tableViewRazredi.setItems(listRazredi);
        colRazredNaziv.setCellValueFactory(new PropertyValueFactory("naziv"));

        //choicebox
        choiceBox.getItems().addAll("Postavke", "Odjavi se");
        Platform.runLater(() -> {
            SkinBase<ChoiceBox<String>> skin = (SkinBase<ChoiceBox<String>>) choiceBox.getSkin();
            // children contain only "Label label" and "StackPane openButton"
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
                closeWindow();
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/postavke.fxml"));
                PostavkeController ctrl = new PostavkeController(nastavnik);
                loader.setController(ctrl);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("Postavke");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.show();
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
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.show();
            }
        });


        labelNazivPredmeta.setText(predmet.getNaziv());
        labelImeIPrezimeNastavnika.setText(nastavnik.toString());
        labelDatum.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        labelObavijest.setText("");

        labelNazivPredmeta1.setText(predmet.getNaziv());
        labelImeIPrezimeNastavnika1.setText(nastavnik.toString());
        labelDatum1.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        labelObavijest1.setText("");
    }

    @FXML
    private void izaberiRazred(MouseEvent event) throws IOException {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(event.getClickCount() == 1) {
                labelObavjestenje.setText("");
                //dobavljamo razred na koji se kliknulo
                razred = tableViewRazredi.getSelectionModel().getSelectedItem();

                conncetion = ucenikData.getConncetion();
                //postavlja spisak ucenika tog razreda
                listUcenici = FXCollections.observableArrayList(ucenikData.uceniciRazreda(razred));
                listViewUcenici.setItems(listUcenici);
                listViewPrisustvoUcenici.setItems(listUcenici);
                //postavljamo ime razreda
                labelNazivRazreda.setText(razred.getNaziv());
                labelNazivRazreda1.setText(razred.getNaziv());

                conncetion = casData.getConncetion();
                int nastavniBrojCasa = casData.dajNastavniBrojCasa(razred.getId());
                labelNastavniRadCasa.setText("Nastavni rad časa broj: " + Integer.toString(nastavniBrojCasa));
                labelNastavniRadCasa1.setText("Nastavni rad časa broj: " + Integer.toString(nastavniBrojCasa));
                tableViewUcenici.setItems(listUcenici);
                colUcenikIme.setCellValueFactory(new PropertyValueFactory("ime"));
                colUcenikPrezime.setCellValueFactory(new PropertyValueFactory("prezime"));
                //colUcenikZakljucnaOcjena.setCellValueFactory(new PropertyValueFactory("zakljucna"));
                colUcenikvladanje.setCellValueFactory(new PropertyValueFactory<Ucenik, Integer>("vladanje"));

                //postavljamo tabelu pismeneZadace
                conncetion = pismenaZadacaData.getConncetion();
                ArrayList<PismenaZadaca> pismene = pismenaZadacaData.pismeneZadaceRazreda(razred);
                listPismeneZadace = FXCollections.observableArrayList(pismene);
                tableViewPismene.setItems(listPismeneZadace);
                colPismenaRedniBroj.setCellValueFactory(new PropertyValueFactory("redniBroj"));
                colPismenaNaziv.setCellValueFactory(new PropertyValueFactory("naziv"));
                colPismenaDatumPisanja.setCellValueFactory(new PropertyValueFactory("datumPisanja"));
                colPismenaDatumIspravke.setCellValueFactory(new PropertyValueFactory("datumIspravke"));
                colPismenaPredmet.setCellValueFactory(new PropertyValueFactory("predmet"));
            }
        }
    }

    @FXML
    private void izaberiUcenika(MouseEvent event) throws IOException {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(event.getClickCount() == 1) {
                ucenik = listViewUcenici.getSelectionModel().getSelectedItem();
                conncetion = ocjenaData.getConncetion();
                //prikazujemo ocjene izabranog ucenika
                ArrayList<Ocjena> ocjeneUcenika = ocjenaData.dajOcjene(ucenik.getId(), predmet.getId());
                listOcjene = FXCollections.observableArrayList(ocjeneUcenika);
                tableViewOcjene.setItems(listOcjene);
                colOcjenaOcjena.setCellValueFactory(new PropertyValueFactory("ocjena"));
                colOcjenaDatum.setCellValueFactory(new PropertyValueFactory("datum"));
                colOcjenaKomentar.setCellValueFactory(new PropertyValueFactory("komentar"));
            }
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) tableViewRazredi.getScene().getWindow();
        stage.close();
    }

    public void actionUnesiOcjenu(ActionEvent actionEvent){
        if(ucenik == null) return;
        closeWindow();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/ocjena.fxml"));
        OcjenaController ctrl = new OcjenaController(null, ucenik, predmet, nastavnik, razred);
        loader.setController(ctrl);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Ocjena");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();

        stage.setOnHiding( event -> {
            Ocjena novaOcjena = ctrl.getOcjena();
            if (novaOcjena != null) {
                ocjenaData.unesiOcjenu(novaOcjena);
                listOcjene.setAll(ocjenaData.dajOcjene(ucenik.getId(), predmet.getId()));
            }
        } );
    }

    public void actionIzmijeniOcjenu(ActionEvent actionEvent){
        Ocjena ocjena = tableViewOcjene.getSelectionModel().getSelectedItem();
        //ako je ocjena izabrana
        if(ocjena != null) {
            closeWindow();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/ocjena.fxml"));
            OcjenaController ctrl = new OcjenaController(ocjena, ucenik, predmet, nastavnik, razred);
            loader.setController(ctrl);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("Ocjena");
            stage.setScene(new Scene(root, 440, 350));
            stage.show();

            stage.setOnHiding( event -> {
                Ocjena novaOcjena = ctrl.getOcjena();
                if (novaOcjena != null) {
                    ocjenaData.izmijeniOcjenu(novaOcjena);
                    listOcjene.setAll(ocjenaData.dajOcjene(ucenik.getId(), predmet.getId()));
                }
            } );
        }
    }

    public void actionObrisiOcjenu(ActionEvent actionEvent){
        Ocjena ocjena = tableViewOcjene.getSelectionModel().getSelectedItem();
        ocjenaData.obrisiOcjenu(ocjena);
        listOcjene.setAll(ocjenaData.dajOcjene(ucenik.getId(), predmet.getId()));
    }

    public void actionUnesiCas(ActionEvent actionEvent){
        if(razred == null) {
            labelObavijest.setText("Odaberite razred!");
            return;
        }
        Cas cas = new Cas();
        cas.setRazred(razred);
        cas.setPredmet(predmet);
        cas.setNastavnik(nastavnik);
        cas.setDatum(new Date());
        cas.setTema(areaTema.getText());
        cas.setNapomena(areaNapomena.getText());
        conncetion = casData.getConncetion();
        casData.unesiCas(cas);
        labelObavijest.setText("Uspješno ste unijeli čas!");
    }

    public void actionUnesiPrisustvo(ActionEvent actionEvent){
        //kako da znamo dal je unesen cas
    }

    public void actionDodajPismenuZadacu(ActionEvent actionEvent){
        if(razred == null) return;
        closeWindow();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/pismena.fxml"));
        PismenaZadacaController ctrl = new PismenaZadacaController(razred, predmet, nastavnik);
        loader.setController(ctrl);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Pismena zadaca");
        stage.setScene(new Scene(root, 440, 350));
        stage.show();

        stage.setOnHiding( event -> {
            PismenaZadaca pismenaZadaca = ctrl.getPismenaZadaca();
            if (pismenaZadaca != null) {
                conncetion = pismenaZadacaData.getConncetion();
                pismenaZadacaData.dodajPismenuZadacu(pismenaZadaca);
            }
        } );
    }

    public void actionDetaljneInformacije(ActionEvent actionEvent){
        ucenik = tableViewUcenici.getSelectionModel().getSelectedItem();
        if(ucenik == null) return;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/ucenik.fxml"));
        UcenikController ctrl = new UcenikController(ucenik, razred);
        loader.setController(ctrl);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Informacije o učeniku");
        stage.setScene(new Scene(root, 440, 350));
        stage.show();
    }

}
