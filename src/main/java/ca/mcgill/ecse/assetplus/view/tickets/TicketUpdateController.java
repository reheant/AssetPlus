package ca.mcgill.ecse.assetplus.view.tickets;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ca.mcgill.ecse.assetplus.controller.*;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class TicketUpdateController {
  @FXML
  private AnchorPane mainContentArea;

  @FXML
  private ListView<String> imageListView;

  @FXML
  private TextField staffTextField;

  @FXML
  private Label errorLabel;

  @FXML
  private Label ticketIdLabel;

  @FXML
  private Button backToTicketsButton;

  @FXML
  private Button deleteTicketButton;

  @FXML
  private Button saveTicketButton;

  @FXML
  private Button assignStaffButton;

  @FXML
  private TextArea descriptionTextArea;

  @FXML
  private TextField ticketRaiserTextField;

  @FXML
  private TextField raisedOnDateTextField;
  @FXML
  private Button viewAndEditStatus;

  @FXML
  private Label assetNameLabel;

  @FXML 
  private TextField assetNumberTextField;

  
  private TOMaintenanceTicket currentMaintenanceTicket;

  private int ticketId;

  private String ticketDescription;
  private int assetNumber;




  public void setTicketId(int newViewedTicketId){
    ticketId = newViewedTicketId;
  }

  @FXML
  public void viewAndEditStatusOnClick(){
    loadPage("tickets/view-edit-status.fxml");
  }



  @FXML
  public void onDeleteTicketClicked(){
      AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(ticketId);
      loadPage("tickets/tickets.fxml");
  }

  @FXML
  public void onSaveTicketClicked(){
    String newDescription = descriptionTextArea.getText();

    Date newRaisedOnDate = Date.valueOf(raisedOnDateTextField.getText());
    String newTicketRaiserEmail = ticketRaiserTextField.getText();
    String newAssetNumberString = assetNumberTextField.getText();
    int newAssetNumber;
    if (newAssetNumberString.isEmpty()){
      System.out.println("asset number empty");
      newAssetNumber = assetNumber;
    }
    else {
      newAssetNumber = Integer.parseInt(newAssetNumberString);
    }

    String result = AssetPlusFeatureSet4Controller.updateMaintenanceTicket(ticketId, newRaisedOnDate, newDescription, newTicketRaiserEmail, newAssetNumber);
    if (!result.equals("")) {
      System.out.println(result);
      errorLabel.setText(result);
    }
    reinitialize();
    System.out.println("saved");
  }

  @FXML
  public void onBackToTicketsClicked(){
    loadPage("tickets/tickets.fxml");
  }


    @FXML
    private void onDeleteImageButtonClicked() {
        String selectedUrl = imageListView.getSelectionModel().getSelectedItem();
        if (selectedUrl != null) {
            AssetPlusFeatureSet5Controller.deleteImageFromMaintenanceTicket(selectedUrl, ticketId);
            imageListView.getItems().remove(selectedUrl);
        } else {
            showAlert("No Selection", "Please select an image URL to delete.");
        }
    }

    @FXML
    private void onAssignStaffClicked() {
        // First prompt for the employee email
        TextInputDialog emailDialog = new TextInputDialog();
        emailDialog.setTitle("Assign Staff");
        emailDialog.setHeaderText(null);
        emailDialog.setContentText("Please enter the staff's email:");

        Optional<String> emailResult = emailDialog.showAndWait();
        emailResult.ifPresent(email -> {
            // Then ask for the priority level
            List<MaintenanceTicket.PriorityLevel> priorityLevels = Arrays.asList(MaintenanceTicket.PriorityLevel.values());
            ChoiceDialog<MaintenanceTicket.PriorityLevel> priorityDialog = new ChoiceDialog<>(MaintenanceTicket.PriorityLevel.Normal, priorityLevels);
            priorityDialog.setTitle("Priority Level");
            priorityDialog.setHeaderText(null);
            priorityDialog.setContentText("Choose the priority level:");

            Optional<MaintenanceTicket.PriorityLevel> priorityResult = priorityDialog.showAndWait();
            priorityResult.ifPresent(priorityLevel -> {
                // Finally, ask for the time estimate
                List<MaintenanceTicket.TimeEstimate> timeEstimates = Arrays.asList(MaintenanceTicket.TimeEstimate.values());
                ChoiceDialog<MaintenanceTicket.TimeEstimate> timeDialog = new ChoiceDialog<>(MaintenanceTicket.TimeEstimate.OneToThreeDays, timeEstimates);
                timeDialog.setTitle("Time Estimate");
                timeDialog.setHeaderText(null);
                timeDialog.setContentText("Choose the time estimate:");

                Optional<MaintenanceTicket.TimeEstimate> timeResult = timeDialog.showAndWait();
                timeResult.ifPresent(timeEstimate -> {
                    // Prompt for manager approval
                    List<String> approvalOptions = Arrays.asList("Yes", "No");
                    ChoiceDialog<String> approvalDialog = new ChoiceDialog<>("No", approvalOptions);
                    approvalDialog.setTitle("Manager Approval Required");
                    approvalDialog.setHeaderText(null);
                    approvalDialog.setContentText("Does this assignment require manager approval?");

                    Optional<String> approvalResult = approvalDialog.showAndWait();
                    boolean requiresManagerApproval = approvalResult.isPresent() && approvalResult.get().equals("Yes");

                    String errorMessage = AssetPlusStateController.assignTicket(ticketId, email, timeEstimate, priorityLevel, requiresManagerApproval);
                    if (errorMessage.isEmpty()) {
                        staffTextField.setText(email);
                    } else {
                        showAlert("Error Assigning Ticket: ", errorMessage);
                    }
                });
            });
        });
    }

  


  @FXML
  public void reinitialize() {
      assignStaffButton.disableProperty().bind(
              staffTextField.textProperty().isNotEmpty()
      );
    this.currentMaintenanceTicket = AssetPlusFeatureSet6Controller.getTicketWithId(ticketId);
    System.out.println(this.currentMaintenanceTicket);
    this.imageListView.getItems().setAll(currentMaintenanceTicket.getImageURLs());

    this.ticketIdLabel.setText("Ticket ID: #" + String.format("%05d", currentMaintenanceTicket.getId()));
    this.descriptionTextArea.setText(currentMaintenanceTicket.getDescription());
    this.ticketRaiserTextField.setText(currentMaintenanceTicket.getRaisedByEmail());
    this.raisedOnDateTextField.setText(String.valueOf(currentMaintenanceTicket.getRaisedOnDate()));
    this.staffTextField.setText(this.currentMaintenanceTicket.getFixedByEmail());


      String currentAssetName = currentMaintenanceTicket.getAssetName();
    if (currentAssetName == null){
      currentAssetName = "None";
    }
    this.assetNameLabel.setText("Current Asset Name: " + currentAssetName);

    assetNumber = AssetPlusFeatureSet6Controller.getAssetNumber(ticketId);
    System.out.println("new asset number: " + String.valueOf(assetNumber));
    if (assetNumber != -1){
      this.assetNumberTextField.setText(String.valueOf(assetNumber));
    }
  }


    private FXMLLoader loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
            Node page = loader.load();
            if (fxmlFile.equals("tickets/view-edit-status.fxml")) {
              ViewAndEditStatusController updateController = loader.getController();
              updateController.initialize(ticketId);
            }
            mainContentArea.getChildren().setAll(page);
            return loader;
        } catch (IOException e) {
            System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            e.printStackTrace();
            System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        }
        return null;
    }


  @FXML
  private void onAddImageButtonClicked() {
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("Add Image");
      dialog.setHeaderText(null);
      dialog.setContentText("Please enter the URL of the image:");

      // Traditional way to get the response value.
      Optional<String> result = dialog.showAndWait();
      result.ifPresent(url -> {
          String errorMessage = AssetPlusFeatureSet5Controller.addImageToMaintenanceTicket(url, ticketId);
          if (errorMessage.isEmpty()) {
              imageListView.getItems().add(url);
          } else {
              showAlert("Invalid Image URL: ", errorMessage);
          }
      });
  }

    

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
