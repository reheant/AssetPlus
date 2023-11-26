package ca.mcgill.ecse.assetplus.view.tickets;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class TicketsController {

    @FXML
    private AnchorPane maintenanceTicketContentArea;

    @FXML
    private TextField maintenanceTicketSearchBar;

    @FXML
    private Button searchButton;

    @FXML
    private ScrollPane maintenanceTicketScrollPane;

    @FXML
    private ListView<?> maintenanceTicketList;

    @FXML
    private Button clearButton;

    // Additional fields for the filter section
    @FXML
    private TextField staffEmailTextField;

    @FXML
    private TextField startDateTextField;

    @FXML
    private TextField endDateTextField;

    @FXML
    private Button clearFilterButton;

    @FXML
    private Button applyFilterButton;

    // Event handler for the search button
    @FXML
    private void onSearchButtonClicked() {
        // Implement search functionality
    }

    // Event handler for the clear button
    @FXML
    private void onClearButtonClicked() {
        // Implement clear functionality
    }

    // Event handler for the clear filter button
    @FXML
    private void onClearFilterClicked() {
        // Implement clear filter functionality
    }

    // Event handler for the apply filter button
    @FXML
    private void onApplyFilterClicked() {
        // Implement apply filter functionality
    }

    @FXML
    private void onAddTicketClicked(){
        loadPage("tickets/update-ticket.fxml");
    }

    // Initialize method if needed
    @FXML
    public void initialize() {
        // Initialization code
    }

    private void loadPage(String fxmlFile) {
        try {
            Node page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
            maintenanceTicketContentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
