package ca.mcgill.ecse.assetplus.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;

public class ManagerController {

  @FXML
  private Label managerName;

  @FXML
  private Label managerEmail;

  @FXML
  private Label managerPhoneNumber;

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
}
