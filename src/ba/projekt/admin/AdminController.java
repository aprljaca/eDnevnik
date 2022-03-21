package ba.projekt.admin;

import ba.projekt.main.Main;
import ba.projekt.nastavnik.Nastavnik;
import ba.projekt.nastavnik.NastavnikData;
import ba.projekt.predmet.Predmet;
import ba.projekt.predmet.PredmetData;
import ba.projekt.prijava.PrijavaController;
import ba.projekt.razred.Razred;
import ba.projekt.razred.RazredData;
import ba.projekt.ucenik.Ucenik;
import ba.projekt.ucenik.UcenikData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;

public class AdminController {

    private Connection conncetion;
    private NastavnikData nastavnikData = new NastavnikData();
    private UcenikData ucenikData = new UcenikData();
    private RazredData razredData = new RazredData();
    private PredmetData predmetData = new PredmetData();

    public ChoiceBox choiceBox = new ChoiceBox();
    public ListView<String> listViewOdabir;
    public ObservableList<String> listOdabir;

    public TabPane tabPane;
    public Tab tabNastavnici;
    public Tab tabRazredi;
    public Tab tabUcenici;
    public Tab tabPredmeti;

    public TableView<Nastavnik> tableViewNastavnici;
    public TableColumn colNastavnikIme;
    public TableColumn colNastavnikPrezime;
    public TableColumn colNastavnikKorisnickoIme;
    public TableColumn colNastavnikLozinka;
    public TableColumn colNastavnikPredmet;
    public ObservableList<Nastavnik> listNastavinci;

    public TableView<Razred> tableViewRazredi;
    public TableColumn colRazredNaziv;
    public ObservableList<Razred> listRazredi;

    public TableView<Ucenik> tableViewUcenici;
    public TableColumn colUcenikIme;
    public TableColumn colUcenikPrezime;
    public TableColumn colUcenikRazred;
    public ObservableList<Ucenik> listUcenici;

    public TableView<Predmet> tableViewPredmeti;
    public TableColumn colPredmetNaziv;
    public ObservableList<Predmet> listPredmeti;

    SingleSelectionModel<Tab> selectionModel;

    @FXML
    public void initialize(){
        selectionModel = tabPane.getSelectionModel();
        ArrayList<String> stringovi = new ArrayList<>();
        stringovi.add("Nastavnici");
        stringovi.add("Razredi");
        stringovi.add("Učenici");
        stringovi.add("Predmeti");
        listOdabir = FXCollections.observableArrayList(stringovi);
        listViewOdabir.setItems(listOdabir);
        listViewOdabir.getSelectionModel().select(0);
        pomocnaFunkcija();

        //choicebox
        choiceBox.getItems().addAll("Odjavi se");
        Platform.runLater(() -> {
            SkinBase<ChoiceBox<String>> skin = (SkinBase<ChoiceBox<String>>) choiceBox.getSkin();
            for (Node child : skin.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    if (label.getText().isEmpty()) {
                        label.setText("Prijavljeni ste kao Admin");
                    }
                    return;
                }
            }
        });

        choiceBox.setOnAction((event) -> {
            int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = choiceBox.getSelectionModel().getSelectedItem();
            if(String.valueOf(selectedItem).equals("Odjavi se")){
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

    public void pomocnaFunkcija(){
        selectionModel.select(tabNastavnici);
        conncetion = nastavnikData.getConncetion();
        listNastavinci = FXCollections.observableArrayList(nastavnikData.sviNastavnici());
        tableViewNastavnici.setItems(listNastavinci);
        colNastavnikIme.setCellValueFactory(new PropertyValueFactory("ime"));
        colNastavnikPrezime.setCellValueFactory(new PropertyValueFactory("prezime"));
        colNastavnikKorisnickoIme.setCellValueFactory(new PropertyValueFactory("korisnickoIme"));
        colNastavnikLozinka.setCellValueFactory(new PropertyValueFactory("lozinka"));
        //colNastavnikPredmet.setCellValueFactory(new PropertyValueFactory<Nastavnik, Predmet>("predmet"));
    }

    @FXML
    private void izaberi(MouseEvent event) throws IOException {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(event.getClickCount() == 1) {

                if(listViewOdabir.getSelectionModel().getSelectedItem().equals("Nastavnici")){
                    pomocnaFunkcija();
                } else if(listViewOdabir.getSelectionModel().getSelectedItem().equals("Razredi")){
                    selectionModel.select(tabRazredi);
                    conncetion = razredData.getConncetion();
                    listRazredi = FXCollections.observableArrayList(razredData.sviRazredi());
                    tableViewRazredi.setItems(listRazredi);
                    colRazredNaziv.setCellValueFactory(new PropertyValueFactory("naziv"));

                } else if(listViewOdabir.getSelectionModel().getSelectedItem().equals("Učenici")){
                    selectionModel.select(tabUcenici);
                    conncetion = ucenikData.getConncetion();
                    listUcenici = FXCollections.observableArrayList(ucenikData.sviUcenici());
                    tableViewUcenici.setItems(listUcenici);
                    colUcenikIme.setCellValueFactory(new PropertyValueFactory("ime"));
                    colUcenikPrezime.setCellValueFactory(new PropertyValueFactory("prezime"));
                    //colUcenikRazred.setCellValueFactory(new PropertyValueFactory<>("razredId"));
                } else if(listViewOdabir.getSelectionModel().getSelectedItem().equals("Predmeti")){
                    selectionModel.select(tabPredmeti);
                    conncetion = predmetData.getConncetion();
                    listPredmeti = FXCollections.observableArrayList(predmetData.sviPredmeti());
                    tableViewPredmeti.setItems(listPredmeti);
                    colPredmetNaziv.setCellValueFactory(new PropertyValueFactory("naziv"));

                }
            }
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) choiceBox.getScene().getWindow();
        stage.close();
    }
}
