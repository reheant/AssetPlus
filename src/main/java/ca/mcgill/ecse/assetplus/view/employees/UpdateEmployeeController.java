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

public class UpdateEmployeeController {
  private String employeeUpdateEmail;
  private String employeeOldName;
  private String employeeOldPhoneNumber;
  private String employeeOldPassword;

  public void setEmployeeUpdateEmail(String email) {
    this.employeeUpdateEmail = email;
  }

  public void setEmployeeOldName(String name) {
    this.employeeOldName = name;
  }

  public void setEmployeeOldPhoneNumber(String phoneNumber) {
    this.employeeOldPhoneNumber = phoneNumber;
  }

  public void setEmployeeOldPassword(String password) {
    this.employeeOldPassword = password;
  }

  @FXML
  private AnchorPane employeeContentArea;

  @FXML
  private Label errorLabel;

  @FXML
  private TextField employeeName;

  @FXML
  private Label employeeEmail;

  @FXML
  private TextField employeePhoneNumber;

  @FXML
  private TextField employeePassword;

  @FXML
  private void onCancelButtonClicked() {
    loadPage("employees.fxml");
  }

  @FXML
  private void onUpdateEmployeeButtonClicked() {
    String name = employeeName.getText();
    String email = employeeUpdateEmail;
    String phoneNumber = employeePhoneNumber.getText();
    String password = employeePassword.getText();

    String result =
        AssetPlusFeatureSet1Controller.updateEmployeeOrGuest(email, password, name, phoneNumber);

    if (!result.equals("")) {
      errorLabel.setText(result);
      return;
    } else {
      loadPage("employees.fxml");
    }
  }

  @FXML
  public void initialize() {
    employeeName.setFocusTraversable(false);
    employeeEmail.setFocusTraversable(false);
    employeePhoneNumber.setFocusTraversable(false);
    employeePassword.setFocusTraversable(false);
  }

  public void updateUIWithEmployeeData() {
    if (employeeOldName != null) {
      employeeName.setPromptText("Old Name: " + employeeOldName);
    }
    if (employeeUpdateEmail != null) {
      employeeEmail.setText("Email (cannot modify): " + employeeUpdateEmail);
    }
    if (employeeOldPhoneNumber != null) {
      employeePhoneNumber.setPromptText("Old Phone: " + employeeOldPhoneNumber);
    }
    if (employeeOldPassword != null) {
      employeePassword.setPromptText("Old Password: " + employeeOldPassword);
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


