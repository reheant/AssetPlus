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
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Disapprove Reason");
    dialog.setHeaderText("Disapprove Ticket");
    dialog.setContentText("Please enter the reason for disapproving the ticket:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(reason -> {
      Date currentDate = new Date(System.currentTimeMillis());
      error[0] += AssetPlusStateController.disapproveTicket(ticket.getId(), currentDate, reason);
    });
    if (!error[0].equals("")){
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
