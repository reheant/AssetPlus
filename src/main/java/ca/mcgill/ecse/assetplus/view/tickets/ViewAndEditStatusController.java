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
  private Button backButton;
  @FXML 
  private AnchorPane viewEditStatusContentArea;
  @FXML
  private RadioButton startedRadioButton, completedRadioButton;

  private int ticketID;
  private TOMaintenanceTicket ticket;
  

  @FXML
  public void initialize(int ticketID) {
    this.ticket = AssetPlusFeatureSet6Controller.getTicketWithId(ticketID);
    this.ticketID = ticketID;
    if (ticket.getFixedByEmail() == null){
      startedRadioButton.setDisable(true);
      completedRadioButton.setDisable(true);
      ticketStatusMessage.setText("Ticket must be assigned before starting or completing");
    } else if (ticket.getStatus().equals("Assigned")){
      completedRadioButton.setDisable(true);
      ticketStatusMessage.setText("Ticket can only be started, once started it can be completed");
    } else if (ticket.getStatus().equals("Closed")){
      startedRadioButton.setDisable(true);
      completedRadioButton.setDisable(true);
      ticketStatusMessage.setText("Ticket has been closed");
    } else {
      startedRadioButton.setDisable(true);
      completedRadioButton.setDisable(true);
    }
  }

  public void manageCompleteAndStarted(ActionEvent event) {

    if (startedRadioButton.isSelected()){
      startedRadioButton.setDisable(true);
      completedRadioButton.setDisable(false);
      AssetPlusStateController.startTicket(ticketID);
      ticketStatusMessage.setText("Ticket has been started, press complete to complete it");
    } else if (completedRadioButton.isSelected() && ticket.getApprovalRequired()){
      completedRadioButton.setDisable(true);
      AssetPlusStateController.resolveTicket(ticketID);
      ticketStatusMessage.setText("Ticket has been reolved, awaiting manager's approval");
    } else if (completedRadioButton.isSelected() && !ticket.getApprovalRequired()){
      completedRadioButton.setDisable(true);
      AssetPlusStateController.resolveTicket(ticketID);
      ticketStatusMessage.setText("Ticket has been completed, good job");
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
