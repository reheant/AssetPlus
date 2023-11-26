package ca.mcgill.ecse.assetplus.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class HomeController {

    @FXML
    private VBox mainContentArea;

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
        loadPage("employees/employees.fxml");
    }

    @FXML
    private void onGuestsClicked() {
        loadPage("guests/guests.fxml");
    }

    @FXML
    private void onManagerClicked() {
        loadPage("manager/manager.fxml");
    }

    private void loadPage(String fxmlFile) {
        try {
            Node page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
            mainContentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
