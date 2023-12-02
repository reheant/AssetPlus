package ca.mcgill.ecse.assetplus.view.manager;

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

  /**
   * Initializes the manager information. This method fetches the manager's information and updates
   * the display. If no manager information is found, it sets the display to 'Manager not found'.
   *
   * @author Nicolas Bolouri
   */
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

  /**
   * Handles the event when the Manager Information button is clicked. This method loads the page
   * 'update-manager.fxml'.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onManagerInfoClicked() {
    loadPage("update-manager.fxml");
  }

  /**
   * Handles the event when the Cancel button is clicked. This method clears the content area of the
   * manager and reinitializes it.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onCancelButtonClicked() {
    managerContentArea.getChildren().clear();
    initialize();
  }

  /**
   * Loads a given FXML page into the manager content area. This method attempts to load a specified
   * FXML file and displays it in the manager content area. If the file cannot be loaded, an
   * IOException is caught and printed.
   *
   * @author Nicolas Bolouri
   * @param fxmlFile The name of the FXML file to load, relative to
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
