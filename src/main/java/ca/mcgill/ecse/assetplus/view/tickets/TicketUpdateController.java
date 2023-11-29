package ca.mcgill.ecse.assetplus.view.tickets;

import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;


import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class TicketUpdateController {
  @FXML
  private AnchorPane mainContentArea;


  @FXML
  private ListView<String> imageListView;

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
  private TextArea descriptionTextArea;

  @FXML
  private TextField ticketRaiserTextField;

  @FXML
  private TextField raisedOnDateTextField;

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
  public void onDeleteTicketClicked(){
    loadPage("tickets/tickets.fxml");
    AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(ticketId);
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
    else{
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

  // reinitialize method if needed
  @FXML
  public void reinitialize() {
    this.currentMaintenanceTicket = AssetPlusFeatureSet6Controller.getTicketWithId(ticketId);
    System.out.println(this.currentMaintenanceTicket);
    this.imageListView.getItems().setAll(currentMaintenanceTicket.getImageURLs());

    this.ticketIdLabel.setText("Ticket ID: #" + String.format("%05d", currentMaintenanceTicket.getId()));
    this.descriptionTextArea.setText(currentMaintenanceTicket.getDescription());
    this.ticketRaiserTextField.setText(currentMaintenanceTicket.getRaisedByEmail());
    this.raisedOnDateTextField.setText(String.valueOf(currentMaintenanceTicket.getRaisedOnDate()));
    
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
