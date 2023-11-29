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

  //private int ticketId;
  private String currentTicketStatus;
  private TOMaintenanceTicket ticket;
  

  @FXML
  public void initialize(int ticketID) {
    this.ticket = AssetPlusFeatureSet6Controller.getTicketWithId(ticketID);
    
    if (ticket.getFixedByEmail() == null){
      startedRadioButton.setDisable(true);
      completedRadioButton.setDisable(true);
      ticketStatusMessage.setText("ticket must be assigned before starting or completing");
    } else {
      
    }
  }

  public void manageCompleteAndStarted(ActionEvent event) {
   // while (startedRadioButton)
    // if (!startedRadioButton.isSelected() && !completedRadioButton.isSelected() && currentTicketStatus.equals("Open")) {
    //   startedRadioButton.setDisable(true);
    //   completedRadioButton.setDisable(true);
    //   ticketStatusMessage.setText("ticket must be assigned before starting or completing");
    // }

    // if (currentTicketStatus.equals("Assigned")) {
    //   completedRadioButton.setDisable(true);
    //   if (startedRadioButton.isSelected()) {
    //     AssetPlusStateController.startTicket(ticketId);
    //   }

    // }

    // if (currentTicketStatus.equals("InProgress")) {
    //   startedRadioButton.setDisable(true);
    //   if (completedRadioButton.isSelected()) {
    //     AssetPlusStateController.resolveTicket(ticketId);
    //   }
    // }

    // if(startedRadioButton.isSelected()) {
    //   //String error = AssetPlusStateController.assignTicket(ticketId, "bob@ap.com", TimeEstimate.LessThanADay, PriorityLevel.Normal, false); //TODO: am manually assigning ticket until luke does asssignstaff
    //   AssetPlusStateController.startTicket(ticketId);
    //   //System.out.println("error:"+error);
    //   System.out.print("maintenance tivcket status; " +MaintenanceTicket.getWithId(ticketId).getStatusFullName());
      
      
      
    // }

    // else if (completedRadioButton.isSelected()){
    //   AssetPlusStateController.resolveTicket(ticketId);
    //   System.out.print("maintenance tivcket status; " +MaintenanceTicket.getWithId(ticketId).getStatusFullName());
      
    // }

  }

  public void backButtonOnClick() {
    loadPage("update-ticket.fxml");
  }

  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/view/tickets/" + fxmlFile));
      Node page = loader.load();
      
      viewEditStatusContentArea.getChildren().setAll(page);
  } catch (IOException e) {
      e.printStackTrace();
  }}
}
