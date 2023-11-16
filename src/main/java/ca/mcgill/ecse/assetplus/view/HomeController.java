package ca.mcgill.ecse.assetplus.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class HomeController {

    @FXML
    private VBox mainContentArea; // The fx:id of the main content area in your FXML

    @FXML
    private void onTicketsClicked() {
        loadPage("tickets.fxml");
    }

    @FXML
    private void onAssetsClicked() {
        loadPage("assets.fxml");
    }

    @FXML
    private void onEmployeesClicked() {
        loadPage("employees.fxml");
    }

    @FXML
    private void onGuestsClicked() {
        loadPage("guests.fxml");
    }

    @FXML
    private void onManagerClicked() {
        loadPage("manager.fxml");
    }

    private void loadPage(String fxmlFile) {
        try {
            // Assuming the fxml files are in the same directory as HomeController
            // Adjust the path if this is not the case
            Node page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
            mainContentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
