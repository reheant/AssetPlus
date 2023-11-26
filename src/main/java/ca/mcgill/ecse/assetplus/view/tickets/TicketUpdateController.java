package ca.mcgill.ecse.assetplus.view.tickets;

import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;


public class TicketUpdateController {
  @FXML
  private AnchorPane mainContentArea;
  
  private TOMaintenanceTicket currentMaintenanceTicket;

  private int viewedTicketId;

  @FXML
  private Label ticketIdLabel;


  public void setViewedTicketId(int newViewedTicketId){
    viewedTicketId = newViewedTicketId;
  }

  // Initialize method if needed
  @FXML
  public void initialize() {
    this.currentMaintenanceTicket = AssetPlusFeatureSet6Controller.getTicketWithId(viewedTicketId);

    this.ticketIdLabel.setText("Ticket ID: #" + String.format("%05d", currentMaintenanceTicket.getId()));
  }

  
  private void loadPage(String fxmlFile) {
      try {
          Node page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
          mainContentArea.getChildren().setAll(page);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
}
