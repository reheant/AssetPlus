package ca.mcgill.ecse.assetplus.view.tickets;

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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class TicketsController {
    private int newTicketId;

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
    private TextField raisedByEmailTextField;

    @FXML
    private TextField raisedOnDateTextField;

    @FXML
    private Button clearFilterButton;

    @FXML
    private Button applyFilterButton;

    /**
     * Initializes the controller. Configures initial settings and populates the maintenance ticket
     * list.
     *
     * @author Liam Di Chiro
     */
    @FXML
    public void initialize() {
        maintenanceTicketSearchBar.setFocusTraversable(false);
        String[] ticketDisplayList = getTicketDisplayList();

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
        maintenanceTicketList.getItems().addAll(ticketDisplayList);

        maintenanceTicketList.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {

                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        newTicketId = Integer.parseInt(newValue.split(" - ")[0]);
                        loadPage("tickets/update-ticket.fxml");
                    }
                });
    }

    /**
     * Handles the event when the search button is clicked. Searches for maintenance tickets based
     * on the entered id.
     *
     * @author Liam Di Chiro
     */
    @FXML
    private void onSearchButtonClicked() {
        String searchedId = maintenanceTicketSearchBar.getText();
        List<String> filteredTickets = filterTicketIdList(searchedId);
        maintenanceTicketList.getItems().clear();

        if (filteredTickets.isEmpty()) {
            displayNoSearchResults();
        } else {
            maintenanceTicketList.getItems().addAll(filteredTickets);
            resetCellFactory();
        }
    }

    /**
     * Handles the event when the clear button is clicked. Clears the search bar and resets the
     * maintenance ticket list.
     *
     * @author: Liam Di Chiro
     */
    @FXML
    private void onClearButtonClicked() {
        maintenanceTicketSearchBar.setText("");
        resetEmployeeList();
        resetCellFactory();
    }

    /**
     * Handles the event when the clear filter button is clicked. Clears the filter criteria and
     * resets the maintenance ticket list.
     *
     * @author Liam Di Chiro
     */
    @FXML
    private void onClearFilterClicked() {
        staffEmailTextField.setText("");
        raisedByEmailTextField.setText("");
        raisedOnDateTextField.setText("");
        resetEmployeeList();
        resetCellFactory();
    }

    /**
     * Handles the event when the apply filter button is clicked. Applies the specified filters and
     * updates the maintenance ticket list accordingly.
     *
     * @author Liam Di Chiro
     */
    @FXML
    private void onApplyFilterClicked() {
        String staffEmail = staffEmailTextField.getText();
        String raisedByEmail = raisedByEmailTextField.getText();
        String raisedOnDate = raisedOnDateTextField.getText();
        List<TOMaintenanceTicket> filteredTickets = AssetPlusFeatureSet6Controller.getTickets();

        if (!staffEmail.equals("")) {
            filteredTickets = filterByEmail(filteredTickets, staffEmail);
        }

        if (!raisedByEmail.equals("")) {
            filteredTickets = filterByRaiserEmail(filteredTickets, raisedByEmail);
        }

        if (!raisedOnDate.equals("")) {
            filteredTickets = filterByDate(filteredTickets, raisedOnDate);
        }

        List<String> filteredTicketsDisplay = filteredTickets
                .stream().map(ticket -> Integer.toString(ticket.getId()) + " - "
                        + ticket.getDescription() + " (" + ticket.getStatus() + ")")
                .collect(Collectors.toList());

        maintenanceTicketList.getItems().clear();
        if (filteredTicketsDisplay.isEmpty()) {
            displayNoSearchResults();
        } else {
            maintenanceTicketList.getItems().addAll(filteredTicketsDisplay);
            resetCellFactory();
        }
    }

    /**
     * Handles the event when the add ticket button is clicked. Initializes the process of adding a
     * new maintenance ticket.
     *
     * @author Liam Di Chiro
     */
    @FXML
    private void onAddTicketClicked() {
        this.newTicketId = AssetPlusFeatureSet6Controller.getMaxTicketId() + 1;
        loadPage("tickets/add-ticket.fxml");
    }

    /**
     * Resets the maintenance ticket list by clearing and repopulating it with all available
     * tickets.
     *
     * @author Liam Di Chiro
     */
    private void resetEmployeeList() {
        maintenanceTicketList.getItems().clear();
        String[] ticketDisplayList = getTicketDisplayList();
        maintenanceTicketList.getItems().addAll(ticketDisplayList);
    }

    /**
     * Displays a message indicating no search results in the maintenance ticket list.
     *
     * @author Liam Di Chiro
     */
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

    /**
     * Resets the cell factory of the maintenance ticket list.
     *
     * @author: Liam Di Chiro
     */
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

    /**
     * Filters a list of maintenance tickets by staff email.
     *
     * @author Liam Di Chiro
     * @param tickets The list of maintenance tickets to filter.
     * @param staffEmail The staff email to filter by.
     * @return A filtered list of maintenance tickets.
     */
    private List<TOMaintenanceTicket> filterByEmail(List<TOMaintenanceTicket> tickets,
            String staffEmail) {
        return tickets.stream().filter(ticket -> ticket.getFixedByEmail() != null)
                .filter(ticket -> ticket.getFixedByEmail().equalsIgnoreCase(staffEmail))
                .collect(Collectors.toList());
    }

    /**
     * Filters a list of maintenance tickets by raiser email.
     *
     * @author: Liam Di Chiro
     * @param tickets The list of maintenance tickets to filter.
     * @param raisedByEmail The raiser email to filter by.
     * @return A filtered list of maintenance tickets.
     */
    private List<TOMaintenanceTicket> filterByRaiserEmail(List<TOMaintenanceTicket> tickets,
            String raisedByEmail) {
        return tickets.stream().filter(ticket -> ticket.getRaisedByEmail() != null)
                .filter(ticket -> ticket.getRaisedByEmail().equalsIgnoreCase(raisedByEmail))
                .collect(Collectors.toList());
    }

    /**
     * Filters a list of maintenance tickets by raised on date.
     *
     * @author: Liam Di Chiro
     * @param tickets The list of maintenance tickets to filter.
     * @param raisedOnDate The raised on date to filter by.
     * @return A filtered list of maintenance tickets.
     */
    private List<TOMaintenanceTicket> filterByDate(List<TOMaintenanceTicket> tickets,
            String raisedOnDate) {
        return tickets.stream().filter(
                ticket -> ticket.getRaisedOnDate().toString().equalsIgnoreCase(raisedOnDate))
                .collect(Collectors.toList());
    }

    /**
     * Filters the list of maintenance tickets based on the searched ticket ID.
     *
     * @author Liam Di Chiro
     * @param searchedTicketId The ticket ID to search for.
     * @return A list of filtered tickets.
     */
    private List<String> filterTicketIdList(String searchedTicketId) {
        String[] ticketDisplayList = getTicketDisplayList();
        return Arrays.stream(ticketDisplayList).filter(ticket -> ticket.contains(searchedTicketId))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the display ticket information from the current list of maintenance tickets.
     *
     * @author Liam Di Chiro
     * @return An array of tickets display information.
     */
    private String[] getTicketDisplayList() {
        List<TOMaintenanceTicket> ticketList = AssetPlusFeatureSet6Controller.getTickets();
        String[] ticketDisplayList = new String[ticketList.size()];

        for (int i = 0; i < ticketList.size(); i++) {
            ticketDisplayList[i] = Integer.toString(ticketList.get(i).getId()) + " - "
                    + ticketList.get(i).getDescription() + " (" + ticketList.get(i).getStatus() + ")";
        }
        return ticketDisplayList;
    }

    /**
     * Loads an FXML page based on the specified file path.
     *
     * @author Liam Di Chiro
     * @param fxmlFile The file path of the FXML page to load.
     */
    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                    getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
            Node page = loader.load();
            maintenanceTicketContentArea.getChildren().setAll(page);

            if (fxmlFile.equals("tickets/add-ticket.fxml")) {
                AddTicketController addTicketController = loader.getController();
                addTicketController.setTicketId(newTicketId);
                addTicketController.reinitialize();
            } else if (fxmlFile.equals("tickets/update-ticket.fxml")) {
                TicketUpdateController ticketUpdateController = loader.getController();
                ticketUpdateController.setTicketId(newTicketId);
                ticketUpdateController.reinitialize();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
