package ca.mcgill.ecse.assetplus.view.tickets;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ca.mcgill.ecse.assetplus.controller.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class TicketUpdateController {
  private TOMaintenanceTicket currentMaintenanceTicket;
  private int ticketId;
  private int assetNumber;

  @FXML
  private AnchorPane mainContentArea;

  @FXML
  private Label errorLabel;

  @FXML
  private ListView<String> imageListView;

  @FXML
  private TextField staffTextField;

  @FXML
  private Label ticketIdLabel;

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
  private Label priorityLabel;

  @FXML
  private Label resolveTimeLabel;

  @FXML
  private Label approvalRequiredLabel;

  @FXML
  private TextArea descriptionTextArea;

  @FXML
  private TextField ticketRaiserTextField;

  @FXML
  private DatePicker raisedOnDateDatePicker;

  @FXML
  private Button viewAndEditStatus;

  @FXML
  private Label assetNameLabel;

  @FXML
  private TextField assetNumberTextField;

  /**
   * Opens view to edit ticket status
   *
   * @author Rehean Thillainathalingam
   */
  @FXML
  public void viewAndEditStatusOnClick() {
    loadPage("tickets/view-edit-status.fxml");
  }

  /**
   * Deletes the current ticket from the application
   *
   * @author Julien Audet
   */
  @FXML
  public void onDeleteTicketClicked() {
    AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(ticketId);
    loadPage("tickets/tickets.fxml");
  }

  /**
   * Saves the modifications on the current ticket into the application
   *
   * @author Julien Audet
   */
  @FXML
  public void onSaveTicketClicked() {
    String newDescription = descriptionTextArea.getText();
    Date newRaisedOnDate = Date.valueOf(raisedOnDateDatePicker.getValue());
    String newTicketRaiserEmail = ticketRaiserTextField.getText();
    String newAssetNumberString = assetNumberTextField.getText();
    int newAssetNumber;
    if (newAssetNumberString.isEmpty()) {
      newAssetNumber = assetNumber;
    } else {
      newAssetNumber = Integer.parseInt(newAssetNumberString);
    }

    String result = AssetPlusFeatureSet4Controller.updateMaintenanceTicket(ticketId,
        newRaisedOnDate, newDescription, newTicketRaiserEmail, newAssetNumber);
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
     * Reinitializes the information on the page
     *
     * @author Julien Audet
     */
    @FXML
    public void reinitialize() {
        errorLabel.setText("");
        assignStaffButton.disableProperty().bind(
                staffTextField.textProperty().isNotEmpty()
        );
        setTicketDetails();
        setAssetDetails();
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
   * Deletes the currently selected image
   *
   * @author Luke Freund
   */
  @FXML
  private void onDeleteImageButtonClicked() {
    String selectedUrl = imageListView.getSelectionModel().getSelectedItem();
    if (selectedUrl != null) {
      AssetPlusFeatureSet5Controller.deleteImageFromMaintenanceTicket(selectedUrl, ticketId);
      imageListView.getItems().remove(selectedUrl);
    } else {
      showError("Please select an image URL to delete.");
    }
  }

  /**
   * Opens a view to see the ticket notes
   *
   * @author Luke Freund
   */
  @FXML
  private void onViewTicketNotesClicked() {
    loadPage("tickets/view-ticket-notes.fxml");
  }

  /**
   * Opens a dialogue window prompting the user to add the necessary information to assign a user to
   * the current ticket
   *
   * @author Luke Freund
   */
  @FXML
  private void onAssignStaffClicked() {
      Dialog<Void> dialog = new Dialog<>();
      dialog.setTitle("Assign Staff");
      dialog.setHeaderText("Enter assignment details");

      ButtonType assignButtonType = new ButtonType("Assign", ButtonBar.ButtonData.OK_DONE);
      dialog.getDialogPane().getButtonTypes().addAll(assignButtonType, ButtonType.CANCEL);

      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(20, 150, 10, 10));

      TextField emailTextField = new TextField();
      emailTextField.setPromptText("Staff's Email");
      ComboBox<String> priorityComboBox = new ComboBox<>(FXCollections.observableArrayList("Urgent", "Normal", "Low"));
      priorityComboBox.setValue("Normal");
      ComboBox<String> timeComboBox = new ComboBox<>(FXCollections.observableArrayList("LessThanADay", "OneToThreeDays", "ThreeToSevenDays", "OneToThreeWeeks", "ThreeOrMoreWeeks"));
      timeComboBox.setValue("OneToThreeDays");
      ComboBox<String> approvalComboBox = new ComboBox<>(FXCollections.observableArrayList("Yes", "No"));
      approvalComboBox.setValue("No");

      grid.add(new Label("Email:"), 0, 0);
      grid.add(emailTextField, 1, 0);
      grid.add(new Label("Priority:"), 0, 1);
      grid.add(priorityComboBox, 1, 1);
      grid.add(new Label("Time Estimate:"), 0, 2);
      grid.add(timeComboBox, 1, 2);
      grid.add(new Label("Manager Approval Required:"), 0, 3);
      grid.add(approvalComboBox, 1, 3);

      dialog.getDialogPane().setContent(grid);

      dialog.setResultConverter(dialogButton -> {
          if (dialogButton == assignButtonType) {
              String email = emailTextField.getText();
              String priorityLevelStr = priorityComboBox.getValue();
              String timeEstimateStr = timeComboBox.getValue();
              boolean requiresManagerApproval = approvalComboBox.getValue().equals("Yes");

              String errorMessage = AssetPlusStateController.assignTicketWithStringEnums(
                      ticketId, email, timeEstimateStr, priorityLevelStr, requiresManagerApproval
              );

              if (errorMessage.isEmpty()) {
                  errorLabel.setText("");
                  staffTextField.setText(email);
              } else {
                  errorLabel.setText(errorMessage);
              }
          }
          return null;
      });

      dialog.showAndWait();
      setAssetDetails();
  }

  /**
   * Prompts the user to add an image url to the ticket
   *
   * @author Luke Freund
   */
  @FXML
  private void onAddImageButtonClicked() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Add Image");
    dialog.setHeaderText(null);
    dialog.setContentText("Please enter the URL of the image:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(url -> {
      String errorMessage =
          AssetPlusFeatureSet5Controller.addImageToMaintenanceTicket(url, ticketId);
      if (errorMessage.isEmpty()) {
        imageListView.getItems().add(url);
      } else {
        showError(errorMessage);
      }
    });
  }

  /**
   * Clears the error message off the screen
   *
   * @author Julien Audet
   */
  @FXML
  private void onErrorClicked() {
    errorLabel.setText("");
  }

  /**
   * Opens a new view corresponding to the given fxml file
   *
   * @author Julien Audet
   * @param fxmlFile path to the fxml file to open
   * @return Returns an FXMLLoader object to access the view controller
   */
  private FXMLLoader loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(Objects
          .requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
      Node page = loader.load();
      if (fxmlFile.equals("tickets/view-edit-status.fxml")) {
        ViewAndEditStatusController updateController = loader.getController();
        updateController.initialize(ticketId);
      } else if (fxmlFile.equals("tickets/view-ticket-notes.fxml")) {
        ViewTicketNotesController notesController = loader.getController();
        notesController.setCurrentMaintenanceTicket(this.currentMaintenanceTicket);
      }
      mainContentArea.getChildren().setAll(page);
      return loader;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Displays an error message
   *
   * @author Julien Audet
   * @param content The error message to display
   */
  private void showError(String content) {
    errorLabel.setText(content);
  }

  /**
   * Sets the ticket details in the view
   *
   * @author Luke Freund
   */
  private void setTicketDetails() {
      this.currentMaintenanceTicket = AssetPlusFeatureSet6Controller.getTicketWithId(ticketId);

      this.ticketIdLabel.setText("Ticket ID: #" + String.format("%05d", currentMaintenanceTicket.getId()));
      this.descriptionTextArea.setText(currentMaintenanceTicket.getDescription());
      this.ticketRaiserTextField.setText(currentMaintenanceTicket.getRaisedByEmail());
      this.raisedOnDateDatePicker.setValue(currentMaintenanceTicket.getRaisedOnDate().toLocalDate());
      this.staffTextField.setText(this.currentMaintenanceTicket.getFixedByEmail());
      this.imageListView.getItems().setAll(currentMaintenanceTicket.getImageURLs());
  }

  /**
   * Sets the asset details in the view
   *
   * @author Luke Freund
   */
  private void setAssetDetails() {
      this.currentMaintenanceTicket = AssetPlusFeatureSet6Controller.getTicketWithId(ticketId);

      String currentAssetName = currentMaintenanceTicket.getAssetName();
      if (currentAssetName == null) {
            currentAssetName = "None";
      }
      this.assetNameLabel.setText("Current Asset Name: " + currentAssetName);

      int expectedLifespanInDays = this.currentMaintenanceTicket.getExpectLifeSpanInDays();
      this.expectedLifespan.setText("Expected lifespan (days): " + (expectedLifespanInDays != -1 ? expectedLifespanInDays : "N/A"));
      Date purchaseDate = this.currentMaintenanceTicket.getPurchaseDate();
      this.purchaseDate.setText("Purchase date: " + (purchaseDate != null ? String.valueOf(purchaseDate) : "N/A"));
      int floorNumber = this.currentMaintenanceTicket.getFloorNumber();
      this.floorNumber.setText("Floor Number: " + (floorNumber != -1 ? floorNumber : "N/A"));
      int roomNumber = this.currentMaintenanceTicket.getRoomNumber();
      this.roomNumber.setText("Room Number: " + (roomNumber != -1 ? roomNumber : "N/A"));
      boolean approvalRequired = this.currentMaintenanceTicket.getApprovalRequired();
      this.approvalRequiredLabel.setText("Approval Required: " + (approvalRequired ? "Yes" : "No"));
      String timeToResolve = this.currentMaintenanceTicket.getTimeToResolve();
      this.resolveTimeLabel.setText("Time to resolve: " + (timeToResolve != null ? timeToResolve : "Not set"));
      String priority = this.currentMaintenanceTicket.getPriority();
      this.priorityLabel.setText("Priority: " + (priority != null ? priority : "Not set"));

      assetNumber = AssetPlusFeatureSet6Controller.getAssetNumber(ticketId);
      if (assetNumber != -1) {
          this.assetNumberTextField.setText(String.valueOf(assetNumber));
      }
  }
}
