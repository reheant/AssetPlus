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

  @FXML
  private void onCancelButtonClicked() {
    loadPage("guests.fxml");
  }

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
