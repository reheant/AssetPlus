package ca.mcgill.ecse.assetplus.view.manager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Objects;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;

public class UpdateManagerController {

  @FXML
  private AnchorPane managerContentArea;

  @FXML
  private Label errorLabel;

  @FXML
  private TextField oldPassword;

  @FXML
  private TextField newPassword;

  @FXML
  private TextField newPasswordConfirm;

  /**
   * Handles the event when the Cancel button is clicked. This method loads the 'manager.fxml' page.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onCancelButtonClicked() {
    loadPage("manager.fxml");
  }

  /**
   * Handles the event when the Save button is clicked. This method validates the old and new
   * passwords entered by the user and updates the manager's password. It displays error messages if
   * the old password is incorrect, the new password fields are empty, or the new passwords do not
   * match. Upon successful password update, it loads the 'manager.fxml' page.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onSavelButtonClicked() {
    String managerPassword = AssetPlusFeatureSet1Controller.getManagerPassword();
    String oldPassword = this.oldPassword.getText();
    String newPassword = this.newPassword.getText();
    String newPasswordConfirm = this.newPasswordConfirm.getText();

    if (oldPassword.isEmpty() || !oldPassword.equals(managerPassword)) {
      errorLabel.setText("Incorrect password");
      return;
    }

    if (newPassword.isEmpty() || newPasswordConfirm.isEmpty()
        || !newPassword.equals(newPasswordConfirm)) {
      errorLabel.setText("New passwords do not match");
      return;
    }

    String result = AssetPlusFeatureSet1Controller.updateManager(newPassword);

    if (!result.equals("")) {
      errorLabel.setText(result);
      return;
    } else {
      loadPage("manager.fxml");
    }
  }

  /**
   * Loads a specified FXML page into the manager content area. This method attempts to load a
   * specified FXML file and displays it in the manager content area. If the file cannot be loaded,
   * an IOException is caught and printed.
   *
   * @author Nicolas Bolouri
   * @param String The name of the FXML file to load, relative to
   *        '/ca/mcgill/ecse/assetplus/view/manager/'.
   */
  private void loadPage(String fxmlFile) {
    try {
      Node page = FXMLLoader.load(Objects.requireNonNull(
          getClass().getResource("/ca/mcgill/ecse/assetplus/view/manager/" + fxmlFile)));
      managerContentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}


