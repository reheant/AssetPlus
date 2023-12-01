package ca.mcgill.ecse.assetplus.view.tickets;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

import ca.mcgill.ecse.assetplus.controller.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class AddTicketController {
  @FXML
  private AnchorPane mainContentArea;
  
  @FXML
  private Label errorLabel;

  @FXML
  private TextField ticketIdTextField;
  
  @FXML
  private Label assetNameLabel;
  
  @FXML 
  private TextField assetNumberTextField;

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
  private TextArea descriptionTextArea;

  @FXML
  private TextField ticketRaiserTextField;

  @FXML
  private DatePicker raisedOnDateDatePicker;

  private int ticketId;

  public void setTicketId(int newViewedTicketId){
    ticketId = newViewedTicketId;
  }

  @FXML
  public void onCancelTicketClicked(){
      loadPage("tickets/tickets.fxml");
  }

  @FXML
  public void onCreateTicketClicked(){
    String result;
    String newDescription = descriptionTextArea.getText();    
    
    String newTicketIdString = ticketIdTextField.getText();
    if (newTicketIdString == null || newTicketIdString.isEmpty()){
      showAlert("Error: missing information", "Missing ticket id.");      
      return;
    }
    Integer newTicketId = Integer.parseInt(newTicketIdString);
    
    String newAssetNumberString = assetNumberTextField.getText();
    if (newAssetNumberString == null || newAssetNumberString.isEmpty()){
      showAlert("Error: missing information", "Missing asset number. Put asset number -1 to avoid specifying an asset.");      
      return;
    }
    Integer newAssetNumber = Integer.parseInt(newAssetNumberString);

    LocalDate localDate = raisedOnDateDatePicker.getValue();
    if (localDate == null){
      showAlert("Error: missing information", "Missing date.");
      return;
    }
    Date newRaisedOnDate = Date.valueOf(localDate);

    String newTicketRaiserEmail = ticketRaiserTextField.getText();
    result = AssetPlusFeatureSet4Controller.addMaintenanceTicket(newTicketId, newRaisedOnDate, newDescription, newTicketRaiserEmail, newAssetNumber);


    if (result.equals("")) {
      loadPage("tickets/tickets.fxml");  
    }
    else{
      System.out.println(result);
      showAlert("Error: Could not create ticket", result);
    }    
  }

  @FXML
  public void onBackToTicketsClicked(){
    loadPage("tickets/tickets.fxml");
  }

  @FXML
  public void reinitialize() {
    this.ticketIdTextField.setPromptText("Suggested New Ticket ID: " + String.format("%05d", ticketId));
  }

  private FXMLLoader loadPage(String fxmlFile) {
      try {
          FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
          Node page = loader.load();
          mainContentArea.getChildren().setAll(page);
          return loader;
      } catch (IOException e) {
          e.printStackTrace();
      }
      return null;
  }


  private void showError(String content) {

  }

  private void showAlert(String title, String content) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle(title);
      alert.setHeaderText(null);
      alert.setContentText(content);
      alert.showAndWait();
  }
}
