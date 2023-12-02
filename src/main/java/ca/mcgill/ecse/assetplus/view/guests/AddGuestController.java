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

public class AddGuestController {

  @FXML
  private AnchorPane guestContentArea;

  @FXML
  private Label errorLabel;

  @FXML
  private TextField guestName;

  @FXML
  private TextField guestEmail;

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
   * Adds a new guest when the Add Guest button is clicked. Validates and submits the guest's
   * information. Displays an error message if the submission fails, otherwise loads the
   * 'guests.fxml' page.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onAddGuestButtonClicked() {
    String name = guestName.getText().strip();
    String email = guestEmail.getText().strip();
    String phoneNumber = guestPhoneNumber.getText().strip();
    String password = guestPassword.getText().strip();

    String result = AssetPlusFeatureSet1Controller.addEmployeeOrGuest(email, password, name,
        phoneNumber, false);

    if (!result.equals("")) {
      errorLabel.setText(result);
      return;
    } else {
      loadPage("guests.fxml");
    }
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
