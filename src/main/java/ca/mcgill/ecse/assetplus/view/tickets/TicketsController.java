package ca.mcgill.ecse.assetplus.view.tickets;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

public class TicketsController {

    @FXML
    private AnchorPane maintenanceTicketContentArea;

    @FXML
    private Label errorLabel;

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

    private int new_ticket_id;

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
    private void onAddTicketClicked() {
        this.new_ticket_id = AssetPlusFeatureSet6Controller.getMaxTicketId()+1;
        Date current_date = new Date(System.currentTimeMillis());
        String result =
                AssetPlusFeatureSet4Controller.addMaintenanceTicket(new_ticket_id, current_date, "Add a description...", "luke.freund@ap.com", -1);

        if (!result.equals("")) {
            System.out.println(result);
            errorLabel.setText(result);
            return;
        } else {
            loadPage("tickets/update-ticket.fxml");
        }
    }

    // Initialize method if needed
    @FXML
    public void initialize() {
        // Initialization code
    }

    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
            Node page = loader.load();
            maintenanceTicketContentArea.getChildren().setAll(page);

            if (fxmlFile.equals("tickets/update-ticket.fxml")){
                TicketUpdateController ticketUpdateController = loader.getController();
                ticketUpdateController.setTicketId(new_ticket_id);
                ticketUpdateController.initialize();
            }
        } catch (IOException e) {
            System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            e.printStackTrace();
            System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        }
    }
}
