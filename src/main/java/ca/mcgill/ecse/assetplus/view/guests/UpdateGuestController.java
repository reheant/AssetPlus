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

  /**
   * Loads the 'guests.fxml' page when the Cancel button is clicked.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onCancelButtonClicked() {
    loadPage("guests.fxml");
  }

  /**
   * Updates guest details when the Update Guest button is clicked. Displays error message if the
   * update fails, otherwise loads the 'guests.fxml' page.
   *
   * @author Nicolas Bolouri
   */
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

  /**
   * Initializes guest detail fields, making them non-focusable.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  public void initialize() {
    guestName.setFocusTraversable(false);
    guestEmail.setFocusTraversable(false);
    guestPhoneNumber.setFocusTraversable(false);
    guestPassword.setFocusTraversable(false);
  }

  /**
   * Updates the UI with the existing guest data. Sets prompt text or text fields with old guest
   * information.
   *
   * @author Nicolas Bolouri
   */
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

  /**
   * Sets the email for guest update.
   *
   * @author Nicolas Bolouri
   * @param email The email of the guest to update.
   */
  public void setGuestUpdateEmail(String email) {
    this.guestUpdateEmail = email;
  }


  /**
   * Sets the old name for guest update.
   *
   * @author Nicolas Bolouri
   * @param name The old name of the guest to update.
   */
  public void setGuestOldName(String name) {
    this.guestOldName = name;
  }

  /**
   * Sets the old phone number for guest update.
   *
   * @author Nicolas Bolouri
   * @param phoneNumber The old phone number of the guest to update.
   */
  public void setGuestOldPhoneNumber(String phoneNumber) {
    this.guestOldPhoneNumber = phoneNumber;
  }

  /**
   * Sets the old password for guest update.
   *
   * @author Nicolas Bolouri
   * @param password The old password of the guest to update.
   */
  public void setGuestOldPassword(String password) {
    this.guestOldPassword = password;
  }

  /**
   * Loads a specified FXML page into the guest content area. Catches and prints exceptions if the
   * file cannot be loaded.
   *
   * @author Nicolas Bolouri
   * @param String The FXML file to load, relative to '/ca/mcgill/ecse/assetplus/view/guests/'.
   */
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
