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

    /**
     * Loads the tickets page view.
     * 
     * @author Luke Freund
     */
    @FXML
    private void onTicketsClicked() {
        loadPage("tickets/tickets.fxml");
    }

    /**
     * Loads the assets page view.
     * 
     * @author Luke Freund
     */
    @FXML
    private void onAssetsClicked() {
        loadPage("assetTypes/assets.fxml");
    }

    /**
     * Loads the employees page view.
     * 
     * @author Luke Freund
     */
    @FXML
    private void onEmployeesClicked() {
        loadPage("employees/employees.fxml");
    }

    /**
     * Loads the guests page view.
     * 
     * @author Luke Freund
     */
    @FXML
    private void onGuestsClicked() {
        loadPage("guests/guests.fxml");
    }

    /**
     * Loads the manager page view.
     * 
     * @author Luke Freund
     */
    @FXML
    private void onManagerClicked() {
        loadPage("manager/manager.fxml");
    }

    /**
     * Loads a specified FXML file into the main content area.
     * 
     * @author Luke Freund
     * @param fxmlFile The name of the FXML file to load.
     */
    private void loadPage(String fxmlFile) {
        try {
            Node page = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
            mainContentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
