package ca.mcgill.ecse.assetplus.view.tickets;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusStateController;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;


public class ViewAndEditStatusController {

  @FXML
  private Label currentStatus, errorLabelLeft, errorLabelRight;

  @FXML
  private Button startButton, completeButton;

  @FXML 
  private AnchorPane viewEditStatusContentArea;

  @FXML
  private Button approveButton, disapproveButton;

  private int ticketID;

  private TOMaintenanceTicket ticket;

  @FXML
  public void initialize(int ticketID) {
    this.ticketID = ticketID;
    this.ticket = AssetPlusFeatureSet6Controller.getTicketWithId(ticketID);
    currentStatus.setText(ticket.getStatus());
  }

  public void backButtonOnClick() {
    loadPage("update-ticket.fxml");
  }

  @FXML 
  private void startButtonOnClick() {
    String error = "";
    error += AssetPlusStateController.startTicket(ticket.getId());
    if (!error.equals("")) {
      errorLabelLeft.setText(error);
    } 
    initialize(ticketID);
  }

  @FXML 
  private void completeButtonOnClick() {
    String error = "";
    error += AssetPlusStateController.resolveTicket(ticket.getId());
    if (!error.equals("")) {
      errorLabelLeft.setText(error);
    } 
    initialize(ticketID);
  }

  @FXML
  private void handleApproveButton() {
    String error = "";
    error += AssetPlusStateController.approveTicket(ticket.getId());
    if (!error.equals("")) {
      errorLabelRight.setText(error);
    } 
    initialize(ticketID);
  }

  @FXML
  private void handleDisapproveButton() {
    String[] error = {""};

    // First dialog for the reason
    TextInputDialog reasonDialog = new TextInputDialog();
    reasonDialog.setTitle("Disapprove Reason");
    reasonDialog.setHeaderText("Disapprove Ticket");
    reasonDialog.setContentText("Please enter the reason for disapproving the ticket:");

    Optional<String> reasonResult = reasonDialog.showAndWait();
    reasonResult.ifPresent(reason -> {
      // Second dialog for the date
      Dialog<java.sql.Date> dateDialog = new Dialog<>();
      dateDialog.setTitle("Disapproval Date");
      dateDialog.setHeaderText("Select the Date");

      DatePicker datePicker = new DatePicker();
      datePicker.setValue(LocalDate.now());

      dateDialog.getDialogPane().setContent(datePicker);
      dateDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

      dateDialog.setResultConverter(dialogButton -> {
        if (dialogButton == ButtonType.OK) {
          LocalDate localDate = datePicker.getValue();
          return java.sql.Date.valueOf(localDate);
        }
        return null;
      });

      Optional<java.sql.Date> dateResult = dateDialog.showAndWait();
      dateResult.ifPresent(date -> {
        error[0] += AssetPlusStateController.disapproveTicket(ticket.getId(), date, reason);
      });
    });

    if (!error[0].equals("")) {
      errorLabelRight.setText(error[0]);
    }
    initialize(ticketID);
  }

  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/view/tickets/" + fxmlFile));
      Node page = loader.load();
      if (fxmlFile.equals("update-ticket.fxml")) {
        TicketUpdateController ticketUpdateController = loader.getController();
        ticketUpdateController.setTicketId(ticketID);
        ticketUpdateController.reinitialize();
    }
      
      viewEditStatusContentArea.getChildren().setAll(page);
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}
