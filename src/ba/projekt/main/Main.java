package ba.projekt.main;

import ba.projekt.prijava.PrijavaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/prijava.fxml"));
        PrijavaController ctrl = new PrijavaController();
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Prijava");
        stage.setScene(new Scene(root, 525, 425));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}