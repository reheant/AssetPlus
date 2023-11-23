package ca.mcgill.ecse.assetplus.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Objects;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;

public class ManagerController {

  @FXML
  private AnchorPane managerContentArea;

  @FXML
  private Label managerName;

  @FXML
  private Label managerEmail;

  @FXML
  private Label managerPhoneNumber;

  @FXML
  private void onManagerInfoClicked() {
    loadPage("update-manager.fxml");
  }

  @FXML
  private void onCancelButtonClicked() {
    managerContentArea.getChildren().clear();
    initialize();
  }

  @FXML
  public void initialize() {
    String managerInfo = AssetPlusFeatureSet1Controller.getManagerInfo();
    if (managerInfo.isEmpty() || managerInfo.equals("Manager not found")) {
      managerName.setText("Manager not found");
      managerEmail.setText("Manager not found");
      managerPhoneNumber.setText("Manager not found");

    } else {
      String[] infoParts = managerInfo.split("~");
      managerName.setText(infoParts[0]);
      managerEmail.setText(infoParts[1]);
      managerPhoneNumber.setText(infoParts[2]);
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
