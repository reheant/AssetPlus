package ca.mcgill.ecse.assetplus.view.tickets;

import com.google.common.base.Objects;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusStateController;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.Status;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;


public class ViewAndEditStatusController {
  @FXML
  private Label ticketStatusMessage;

  @FXML
  private Label approvalStatusMessage;

  @FXML
  private Button backButton;
  @FXML 
  private AnchorPane viewEditStatusContentArea;
  @FXML
  private RadioButton startedRadioButton, completedRadioButton;

  @FXML
  private RadioButton approvedButton, disapprovedButton;

  private int ticketID;
  private TOMaintenanceTicket ticket;
  

  @FXML
  public void initialize(int ticketID) {
    this.ticket = AssetPlusFeatureSet6Controller.getTicketWithId(ticketID);
    this.ticketID = ticketID;
    if (ticket.getFixedByEmail() == null){
      startedRadioButton.setDisable(true);
      completedRadioButton.setDisable(true);
      ticketStatusMessage.setText("ticket must be assigned before starting or completing");
    } else if (ticket.getStatus().equals("Assigned")){
      completedRadioButton.setDisable(true);
      ticketStatusMessage.setText("ticket can only be started, once started it can be completed");
    }
    System.out.println(ticket.getStatus());
    if (!ticket.isApprovalRequired()){
      approvedButton.setDisable(true);
      disapprovedButton.setDisable(true);
      approvalStatusMessage.setText("Ticket does not require manager's approval upon completion");
    }
    else if (!ticket.getStatus().equals("Resolved")){
      approvedButton.setDisable(true);
      disapprovedButton.setDisable(true);
      approvalStatusMessage.setText("Ticket must be completed before getting approved or disapproved by the manager");
    }
  }


  public void manageCompleteAndStarted(ActionEvent event) {

    if (startedRadioButton.isSelected()){
      startedRadioButton.setDisable(true);
      completedRadioButton.setDisable(false);
      AssetPlusStateController.startTicket(ticketID);
      ticketStatusMessage.setText("ticket has been started, press complete to complete it");
    } else if (completedRadioButton.isSelected() && ticket.getApprovalRequired()){
      completedRadioButton.setDisable(true);
      AssetPlusStateController.resolveTicket(ticketID);
      ticketStatusMessage.setText("ticket has been resolved, awaiting manager's approval");
      if (approvedButton.isSelected()) {
        approvedButton.setDisable(true);
        disapprovedButton.setDisable(true);
        AssetPlusStateController.approveTicket(ticketID);
        approvalStatusMessage.setText("Ticket has been approved.");
      }
      else if (disapprovedButton.isSelected()) {
        approvedButton.setDisable(true);
        disapprovedButton.setDisable(true);
        AssetPlusStateController.approveTicket(ticketID);
        approvalStatusMessage.setText("Ticket has been disapproved.");
      }
    } else if (completedRadioButton.isSelected() && !ticket.getApprovalRequired()){
      completedRadioButton.setDisable(true);
      AssetPlusStateController.resolveTicket(ticketID);
      ticketStatusMessage.setText("ticket has been completed, good job");
    }
    System.out.println(ticket.getStatus());
    if (!ticket.isApprovalRequired()){
      approvedButton.setDisable(true);
      disapprovedButton.setDisable(true);
      approvalStatusMessage.setText("Ticket does not require manager's approval upon completion");
    }
    else if (!ticket.getStatus().equals("Resolved")){
      approvedButton.setDisable(true);
      disapprovedButton.setDisable(true);
      approvalStatusMessage.setText("Ticket must be completed before getting approved or disapproved by the manager");
    }


  }

  public void backButtonOnClick() {
    loadPage("update-ticket.fxml");
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
  }}
}
