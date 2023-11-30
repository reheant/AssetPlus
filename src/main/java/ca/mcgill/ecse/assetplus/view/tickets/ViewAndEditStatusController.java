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
  private Label ticketStatusMessage;

  @FXML
  private Label approvalStatusMessage;

  @FXML
  private Button actionButton;

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
    updateView();
    updateApprovalView();
  }

  public void backButtonOnClick() {
    loadPage("update-ticket.fxml");
  }

  private void updateView() {
    this.ticket = AssetPlusFeatureSet6Controller.getTicketWithId(ticketID);
    String status = ticket.getStatus();
    switch (status) {
      case "Open" -> {
        actionButton.setDisable(true);
        actionButton.setText("Start");
        ticketStatusMessage.setText("Ticket must be assigned before starting or completing.");
      }
      case "Assigned" -> {
        actionButton.setDisable(false);
        actionButton.setText("Start");
        ticketStatusMessage.setText("Ticket ready to be started.");
      }
      case "InProgress" -> {
        actionButton.setDisable(false);
        actionButton.setText("Complete");
        ticketStatusMessage.setText("Ticket has been started.");
      }
      case "Resolved", "Closed" -> {
        actionButton.setDisable(true);
        actionButton.setText("Complete");
        ticketStatusMessage.setText(status.equals("Resolved") ?
                "Ticket has been resolved, awaiting manager's approval." :
                "Ticket is closed.");
      }
      default -> {
      }
    }
  }

  private void updateApprovalView() {
    this.ticket = AssetPlusFeatureSet6Controller.getTicketWithId(ticketID);
    if (!ticket.getApprovalRequired()) {
      approveButton.setDisable(true);
      disapproveButton.setDisable(true);
      approvalStatusMessage.setText("Ticket does not require manager's approval.");
    } else {
      String status = ticket.getStatus();
      switch (status) {
        case "Open", "Assigned", "InProgress" -> {
          approveButton.setDisable(true);
          disapproveButton.setDisable(true);
          approvalStatusMessage.setText("Ticket must be completed before getting approved or disapproved by the manager.");
        }
        case "Resolved" -> {
          approveButton.setDisable(false);
          disapproveButton.setDisable(false);
          approvalStatusMessage.setText("Approve or disapprove ticket completion.");
        }
        case "Closed" -> {
          approveButton.setDisable(true);
          disapproveButton.setDisable(true);
          approvalStatusMessage.setText("Ticket approved and closed.");
        }
        default -> {
        }
      }
    }
  }

  @FXML
  private void handleActionButton() {
    if ("Start".equals(actionButton.getText())) {
      AssetPlusStateController.startTicket(ticket.getId());
    } else if ("Complete".equals(actionButton.getText())) {
      AssetPlusStateController.resolveTicket(ticket.getId());
    }
    updateView();
    updateApprovalView();
  }

  @FXML
  private void handleApproveButton() {
    AssetPlusStateController.approveTicket(ticket.getId());
    updateView();
    updateApprovalView();
  }

  @FXML
  private void handleDisapproveButton() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Disapprove Reason");
    dialog.setHeaderText("Disapprove Ticket");
    dialog.setContentText("Please enter the reason for disapproving the ticket:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(reason -> {
      Date currentDate = new Date(System.currentTimeMillis());
      AssetPlusStateController.disapproveTicket(ticket.getId(), currentDate, reason);
    });
    updateView();
    updateApprovalView();
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
