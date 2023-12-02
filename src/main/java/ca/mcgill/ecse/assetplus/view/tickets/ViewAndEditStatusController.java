package ca.mcgill.ecse.assetplus.view.tickets;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusStateController;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;


public class ViewAndEditStatusController {
  private int ticketID;
  private TOMaintenanceTicket ticket;

  @FXML
  private Label currentStatus;

  @FXML
  private Label errorLabelLeft;

  @FXML
  private Label errorLabelRight;

  @FXML
  private Button startButton;

  @FXML
  private Button completeButton;

  @FXML
  private AnchorPane viewEditStatusContentArea;

  @FXML
  private Button approveButton;

  @FXML
  private Button disapproveButton;

  /**
   * Initializes the view and edit status view.
   *
   * @author Rehean Thillainathalingam
   * @param ticketID The ID of the maintenance ticket.
   */
  @FXML
  public void initialize(int ticketID) {
    this.ticketID = ticketID;
    this.ticket = AssetPlusFeatureSet6Controller.getTicketWithId(ticketID);
    currentStatus.setText(ticket.getStatus());
  }

  /**
   * Loads the update-ticket view when the back button is clicked.
   *
   * @author Luke Freund
   */
  @FXML
  public void backButtonOnClick() {
    loadPage("update-ticket.fxml");
  }

  /**
   * Starts the work on the ticket.
   *
   * @author Rehean Thillainathalingam
   */
  @FXML
  private void startButtonOnClick() {
    String error = "";
    error += AssetPlusStateController.startTicket(ticket.getId());
    if (!error.equals("")) {
      errorLabelLeft.setText(error);
    }
    initialize(ticketID);
  }

  /**
   * Completes the work on a ticket.
   *
   * @author Luke Freund
   */
  @FXML
  private void completeButtonOnClick() {
    String error = "";
    error += AssetPlusStateController.resolveTicket(ticket.getId());
    if (!error.equals("")) {
      errorLabelLeft.setText(error);
    }
    initialize(ticketID);
  }

  /**
   * If manager approval required, approves the ticket.
   *
   * @author Rehean Thillainathalingam
   */
  @FXML
  private void handleApproveButton() {
    String error = "";
    error += AssetPlusStateController.approveTicket(ticket.getId());
    if (!error.equals("")) {
      errorLabelRight.setText(error);
    }
    initialize(ticketID);
  }

  /**
   * Disapproves the ticket.
   *
   * @author Luke Freund
   */
  @FXML
  private void handleDisapproveButton() {
    String[] error = {""};

    Dialog<Pair<String, Date>> dialog = new Dialog<>();
    dialog.setTitle("Disapprove Ticket");
    dialog.setHeaderText("Enter the reason and select the date for disapproving the ticket");

    ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField reasonTextField = new TextField();
    reasonTextField.setPromptText("Reason");
    DatePicker datePicker = new DatePicker(LocalDate.now());

    grid.add(new Label("Reason:"), 0, 0);
    grid.add(reasonTextField, 1, 0);
    grid.add(new Label("Date:"), 0, 1);
    grid.add(datePicker, 1, 1);

    Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
    okButton.setDisable(true);

    reasonTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      okButton.setDisable(newValue.trim().isEmpty());
    });

    dialog.getDialogPane().setContent(grid);
    Platform.runLater(reasonTextField::requestFocus);

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == okButtonType) {
        return new Pair<>(reasonTextField.getText(), java.sql.Date.valueOf(datePicker.getValue()));
      }
      return null;
    });

    Optional<Pair<String, java.sql.Date>> result = dialog.showAndWait();

    result.ifPresent(reasonAndDate -> {
      error[0] += AssetPlusStateController.disapproveTicket(ticket.getId(), reasonAndDate.getValue(), reasonAndDate.getKey());
    });

    if (!error[0].equals("")) {
      errorLabelRight.setText(error[0]);
    } else {
      errorLabelRight.setText("");
    }
    initialize(ticketID);
  }

  /**
   * Loads the specified view.
   *
   * @author Rehean Thillainathalingam
   * @param fxmlFile The file to be loaded.
   */
  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/ca/mcgill/ecse/assetplus/view/tickets/" + fxmlFile));
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
