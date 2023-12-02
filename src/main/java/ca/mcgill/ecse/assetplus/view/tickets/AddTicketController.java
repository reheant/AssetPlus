package ca.mcgill.ecse.assetplus.view.tickets;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

import ca.mcgill.ecse.assetplus.controller.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class AddTicketController {
  private int ticketId;

  @FXML
  private AnchorPane mainContentArea;

  @FXML
  private Label errorLabel;

  @FXML
  private TextField ticketIdTextField;

  @FXML
  private Label assetNameLabel;

  @FXML
  private TextField assetNumberTextField;

  @FXML
  private Label expectedLifespan;

  @FXML
  private Label purchaseDate;

  @FXML
  private Label floorNumber;

  @FXML
  private Label roomNumber;

  @FXML
  private Button assignStaffButton;

  @FXML
  private TextArea descriptionTextArea;

  @FXML
  private TextField ticketRaiserTextField;

  @FXML
  private DatePicker raisedOnDateDatePicker;

  /**
   * Cancels ticket creation
   *
   * @author Julien Audet
   */
  @FXML
  public void onCancelTicketClicked() {
    loadPage("tickets/tickets.fxml");
  }

  /**
   * Attempt to create a ticket with the information currently in the fields
   *
   * @author Julien Audet
   */
  @FXML
  public void onCreateTicketClicked() {
    String result;
    String newDescription = descriptionTextArea.getText();

    String newTicketIdString = ticketIdTextField.getText();
    if (newTicketIdString == null || newTicketIdString.isEmpty()) {
      showError("Missing ticket id.");
      return;
    }
    Integer newTicketId = Integer.parseInt(newTicketIdString);

    String newAssetNumberString = assetNumberTextField.getText();
    if (newAssetNumberString == null || newAssetNumberString.isEmpty()) {
      showError("Missing asset number. Put asset number -1 to avoid specifying an asset.");
      return;
    }
    Integer newAssetNumber = Integer.parseInt(newAssetNumberString);

    LocalDate localDate = raisedOnDateDatePicker.getValue();
    if (localDate == null) {
      showError("Missing date.");
      return;
    }
    Date newRaisedOnDate = Date.valueOf(localDate);

    String newTicketRaiserEmail = ticketRaiserTextField.getText();
    result = AssetPlusFeatureSet4Controller.addMaintenanceTicket(newTicketId, newRaisedOnDate,
        newDescription, newTicketRaiserEmail, newAssetNumber);


    if (result.equals("")) {
      loadPage("tickets/tickets.fxml");
    } else {
      showError(result);
    }
  }

  /**
   * Returns to the ticket search page
   *
   * @author Julien Audet
   */
  @FXML
  public void onBackToTicketsClicked() {
    loadPage("tickets/tickets.fxml");
  }

  /**
   * Reinitializes the information displayed on the page. Similar to initialize, but shouldn't be
   * called right when the page is loaded
   *
   * @author Julien Audet
   */
  @FXML
  public void reinitialize() {
    this.ticketIdTextField
        .setPromptText("Suggested New Ticket ID: " + String.format("%05d", ticketId));
    this.errorLabel.setText("");
  }

  /**
   * Keeps track of the id of the ticket to view
   *
   * @author Julien Audet
   * @param newViewedTicketId The id of the ticket to view
   */
  public void setTicketId(int newViewedTicketId) {
    ticketId = newViewedTicketId;
  }

  /**
   * Clears the error off the screen once clicked
   *
   * @author Julien Audet
   */
  @FXML
  private void onErrorClicked() {
    errorLabel.setText("");
  }

  /**
   * Loads a new page with the desired fxml file
   *
   * @author Julien Audet
   * @param fxmlFile A path to the fxml file to load.
   * @return An FXMLLoader to allow access to that scene's view controller
   */
  private FXMLLoader loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(Objects
          .requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
      Node page = loader.load();
      mainContentArea.getChildren().setAll(page);
      return loader;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Displays an error message on the page
   *
   * @author Julien Audet
   * @param content The error message to display
   */
  private void showError(String content) {
    errorLabel.setText(content);
  }
}
