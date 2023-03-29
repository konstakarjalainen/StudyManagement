package fi.tuni.prog3.sisu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


public class Sisu extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(null);
        loader.setLocation(TabPane.class.getClassLoader().getResource("MainView.fxml"));
        TabPane main = loader.load();
        

        var scene = new Scene(main, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}