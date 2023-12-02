package ca.mcgill.ecse.assetplus.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Home extends Application {

    /**
     * Initializes and displays the primary stage of the JavaFX application.
     * 
     * @author Luke Freund
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/ca/mcgill/ecse/assetplus/view/home.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Home");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches the JavaFX application.
     * 
     * @author Luke Freund
     */
    public static void main(String[] args) {
        launch(args);
    }
}
