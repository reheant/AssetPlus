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

  /**
   * Loads the 'employees.fxml' page when the Cancel button is clicked.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onCancelButtonClicked() {
    loadPage("employees.fxml");
  }

  /**
   * Updates an employee's details when the Update Employee button is clicked. Displays error
   * message if the update fails, otherwise loads the 'employees.fxml' page.
   *
   * @author Nicolas Bolouri
   */
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

  /**
   * Initializes employee detail fields, making them non-focusable.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  public void initialize() {
    employeeName.setFocusTraversable(false);
    employeeEmail.setFocusTraversable(false);
    employeePhoneNumber.setFocusTraversable(false);
    employeePassword.setFocusTraversable(false);
  }

  /**
   * Updates the UI with the existing employee data. Sets prompt text or text fields with old
   * employee information.
   *
   * @author Nicolas Bolouri
   */
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

  /**
   * Sets the email for employee update.
   *
   * @author Nicolas Bolouri
   * @param email The email of the employee to update.
   */
  public void setEmployeeUpdateEmail(String email) {
    this.employeeUpdateEmail = email;
  }

  /**
   * Sets the old name for employee update.
   *
   * @author Nicolas Bolouri
   * @param name The old name of the employee to update.
   */
  public void setEmployeeOldName(String name) {
    this.employeeOldName = name;
  }


  /**
   * Sets the old phone number for employee update.
   *
   * @author Nicolas Bolouri
   * @param phoneNumber The old phone number of the employee to update.
   */
  public void setEmployeeOldPhoneNumber(String phoneNumber) {
    this.employeeOldPhoneNumber = phoneNumber;
  }

  /**
   * Sets the old password for employee update.
   *
   * @param password The old password of the employee to update.
   * @author Nicolas Bolouri
   */
  public void setEmployeeOldPassword(String password) {
    this.employeeOldPassword = password;
  }

  /**
   * Loads a specified FXML page into the employee content area. Catches and prints exceptions if
   * the file cannot be loaded.
   *
   * @author Nicolas Bolouri
   * @param fxmlFile The FXML file to load, relative to '/ca/mcgill/ecse/assetplus/view/employees/'.
   */
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


