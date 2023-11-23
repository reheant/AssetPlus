package ca.mcgill.ecse.assetplus.view;

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

  @FXML
  private void onCancelButtonClicked() {
    loadPage("manager.fxml");
  }

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

    if (newPassword.isEmpty() || newPasswordConfirm.isEmpty() || !newPassword.equals(newPasswordConfirm)) {
      errorLabel.setText("New passwords do not match");
      return;
    }

    String result = AssetPlusFeatureSet1Controller.updateManager(newPassword);

    if (!result.equals("")) {
      errorLabel.setText(result);
      return;
    }
    else {
      loadPage("manager.fxml");
    }
  } 

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




