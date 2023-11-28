package ca.mcgill.ecse.assetplus.view.employees;

import java.io.IOException;
import java.util.Objects;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddEmployeeController {
  @FXML
  private AnchorPane employeeContentArea;

  @FXML
  private Label errorLabel;

  @FXML
  private TextField employeeName;

  @FXML
  private TextField employeeEmail;

  @FXML
  private TextField employeePhoneNumber;

  @FXML
  private TextField employeePassword;

  @FXML
  private void onCancelButtonClicked() {
    loadPage("employees.fxml");
  }

  @FXML
  private void onAddEmployeeButtonClicked() {
    String name = employeeName.getText().strip();
    String email = employeeEmail.getText().strip();
    String phoneNumber = employeePhoneNumber.getText().strip();
    String password = employeePassword.getText().strip();

    String result =
        AssetPlusFeatureSet1Controller.addEmployeeOrGuest(email, password, name, phoneNumber, true);

    if (!result.equals("")) {
      errorLabel.setText(result);
      return;
    } else {
      loadPage("employees.fxml");
    }
  }

  private void loadPage(String fxmlFile) {
    try {
      Node page = FXMLLoader.load(Objects.requireNonNull(
          getClass().getResource("/ca/mcgill/ecse/assetplus/view/employees/" + fxmlFile)));
      employeeContentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

