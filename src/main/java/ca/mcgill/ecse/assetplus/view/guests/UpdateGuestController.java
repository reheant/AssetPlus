package ca.mcgill.ecse.assetplus.view.guests;

import java.io.IOException;
import java.util.Objects;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UpdateGuestController {
  private String guestUpdateEmail;
  private String guestOldName;
  private String guestOldPhoneNumber;
  private String guestOldPassword;

  public void setGuestUpdateEmail(String email) {
    this.guestUpdateEmail = email;
  }

  public void setGuestOldName(String name) {
    this.guestOldName = name;
  }

  public void setGuestOldPhoneNumber(String phoneNumber) {
    this.guestOldPhoneNumber = phoneNumber;
  }

  public void setGuestOldPassword(String password) {
    this.guestOldPassword = password;
  }

  @FXML
  private AnchorPane guestContentArea;

  @FXML
  private Label errorLabel;

  @FXML
  private TextField guestName;

  @FXML
  private Label guestEmail;

  @FXML
  private TextField guestPhoneNumber;

  @FXML
  private TextField guestPassword;

  @FXML
  private void onCancelButtonClicked() {
    loadPage("guests.fxml");
  }

  @FXML
  private void onUpdateGuestButtonClicked() {
    String name = guestName.getText();
    String email = guestUpdateEmail;
    String phoneNumber = guestPhoneNumber.getText();
    String password = guestPassword.getText();

    String result =
        AssetPlusFeatureSet1Controller.updateEmployeeOrGuest(email, password, name, phoneNumber); 

    if (!result.equals("")) {
      errorLabel.setText(result);
      return;
    } else {
      loadPage("guests.fxml");
    }
  }

  @FXML
  public void initialize() {
    guestName.setFocusTraversable(false);
    guestEmail.setFocusTraversable(false);
    guestPhoneNumber.setFocusTraversable(false);
    guestPassword.setFocusTraversable(false);
  }

  public void updateUIWithGuestData() {
    if (guestOldName != null) {
      guestName.setPromptText("Old Name: " + guestOldName);
    }
    if (guestUpdateEmail != null) {
      guestEmail.setText("Email (cannot modify): " + guestUpdateEmail);
    }
    if (guestOldPhoneNumber != null) {
      guestPhoneNumber.setPromptText("Old Phone: " + guestOldPhoneNumber);
    }
    if (guestOldPassword != null) {
      guestPassword.setPromptText("Old Password: " + guestOldPassword);
    }
  }

  private void loadPage(String fxmlFile) {
    try {
      Node page = FXMLLoader.load(Objects.requireNonNull(
          getClass().getResource("/ca/mcgill/ecse/assetplus/view/guests/" + fxmlFile)));
      guestContentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
