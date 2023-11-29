package ca.mcgill.ecse.assetplus.view.tickets;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


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
    private ListView<String> maintenanceTicketList;

    @FXML
    private Button clearButton;

    @FXML
    private TextField staffEmailTextField;

    @FXML
    private TextField raisedOnDateTextField;

    @FXML
    private Button clearFilterButton;

    @FXML
    private Button applyFilterButton;

    private int newTicketId;

    @FXML
    private void onSearchButtonClicked() {
        String searchedEmail = maintenanceTicketSearchBar.getText();
        List<String> filteredTicketIds = filterTicketIdList(searchedEmail);
        maintenanceTicketList.getItems().clear();

        if (filteredTicketIds.isEmpty()) {
            displayNoSearchResults();
        } else {
            maintenanceTicketList.getItems().addAll(filteredTicketIds);
            resetCellFactory();
        }
    }

    // Event handler for the clear button
    @FXML
    private void onClearButtonClicked() {
        maintenanceTicketSearchBar.setText("");
        resetEmployeeList();
        resetCellFactory();
    }

    // Event handler for the clear filter button
    @FXML
    private void onClearFilterClicked() {
        staffEmailTextField.setText("");
        raisedOnDateTextField.setText("");
        resetEmployeeList();
        resetCellFactory();
    }

    // Event handler for the apply filter button
    @FXML
    private void onApplyFilterClicked() {
        String staffEmail = staffEmailTextField.getText();
        String raisedOnDate = raisedOnDateTextField.getText();

        System.out.println("Staff email: " + staffEmail);
        System.out.println("Raised on date: " + raisedOnDate);
        List<TOMaintenanceTicket> filteredTickets = AssetPlusFeatureSet6Controller.getTickets();

        if (!staffEmail.equals("")) {
            filteredTickets = filterByEmail(filteredTickets, staffEmail);
        }

        if (!raisedOnDate.equals("")) {
            filteredTickets = filterByDate(filteredTickets, raisedOnDate);
        }

        List<String> filteredTicketIds = filteredTickets.stream()
                .map(ticket -> Integer.toString(ticket.getId()))
                .collect(Collectors.toList());
        maintenanceTicketList.getItems().clear();

        if (filteredTicketIds.isEmpty()) {
            displayNoSearchResults();
        } else {
            maintenanceTicketList.getItems().addAll(filteredTicketIds);
            resetCellFactory();
        }
    }

    @FXML
    private void onAddTicketClicked() {
        this.newTicketId = AssetPlusFeatureSet6Controller.getMaxTicketId() + 1;
        Date currentDate = new Date(System.currentTimeMillis());
        String result = AssetPlusFeatureSet4Controller.addMaintenanceTicket(newTicketId,
                currentDate, "Add a description...", "luke.freund@ap.com", -1);

        if (!result.equals("")) {
            System.out.println(result);
            errorLabel.setText(result);
            return;
        } else {
            loadPage("tickets/update-ticket.fxml");
        }
    }

    @FXML
    public void initialize() {
        maintenanceTicketSearchBar.setFocusTraversable(false);
        String[] ticketIdKs = getTicketIds();

        maintenanceTicketList.setFixedCellSize(50.0);
        maintenanceTicketList.setCellFactory(lv -> new ListCell<String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-font-size: 16pt;");
                }
            }
        });
        maintenanceTicketList.setPrefHeight(10 * maintenanceTicketList.getFixedCellSize());
        maintenanceTicketList.getItems().addAll(ticketIdKs);

        // !!! THIS IS WHAT / WHERE THE LOGIC WOULD BE WHEN TICKET IS CLICKED !!!
        maintenanceTicketList.getSelectionModel().selectedItemProperty()
            .addListener(new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue,
        String newValue) {
            // newValuue = ticket id that has been selected as a string
            // do logic here to see ticket details
            System.out.println("Ticket id: " + newValue);
        }
        });
    }

    private void resetEmployeeList() {
        maintenanceTicketList.getItems().clear();
        String[] ticketIds = getTicketIds();
        maintenanceTicketList.getItems().addAll(ticketIds);
      }


    private void displayNoSearchResults() {
        maintenanceTicketList.getItems().add("No search results");
        maintenanceTicketList.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    if (item.equals("No search results")) {
                        setDisable(true);
                        setStyle("-fx-font-style: italic;");
                        setStyle("-fx-font-size: 16pt;");
                    }
                }
            }
        });
    }

    private void resetCellFactory() {
        maintenanceTicketList.setCellFactory(lv -> new ListCell<String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-font-size: 16pt;");
                }
            }
        });
    }

    private List<TOMaintenanceTicket> filterByEmail(List<TOMaintenanceTicket> tickets, String staffEmail) {
        return tickets.stream()
                .filter(ticket -> ticket.getRaisedByEmail().equalsIgnoreCase(staffEmail))
                .collect(Collectors.toList());
    }

    private List<TOMaintenanceTicket> filterByDate(List<TOMaintenanceTicket> tickets, String raisedOnDate) {
        return tickets.stream()
                .filter(ticket -> ticket.getRaisedOnDate().toString().equalsIgnoreCase(raisedOnDate))
                .collect(Collectors.toList());
    }

    private List<String> filterTicketIdList(String searchedTicketId) {
        String[] ticketIdKs = getTicketIds();
        return Arrays.stream(ticketIdKs)
                .filter(ticketIdK -> ticketIdK.equalsIgnoreCase(searchedTicketId))
                .collect(Collectors.toList());
    }

    private String[] getTicketIds() {
        List<TOMaintenanceTicket> ticketList = AssetPlusFeatureSet6Controller.getTickets();
        String[] ticketIdKs = new String[ticketList.size()];

        for (int i = 0; i < ticketList.size(); i++) {
            ticketIdKs[i] = Integer.toString(ticketList.get(i).getId());
        }
        return ticketIdKs;
    }

    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                    getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
            Node page = loader.load();
            maintenanceTicketContentArea.getChildren().setAll(page);

            if (fxmlFile.equals("tickets/update-ticket.fxml")) {
                TicketUpdateController ticketUpdateController = loader.getController();
                ticketUpdateController.setTicketId(newTicketId);
                ticketUpdateController.initialize();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


