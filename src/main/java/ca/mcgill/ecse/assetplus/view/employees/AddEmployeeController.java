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
   * Adds a new employee when the Add Employee button is clicked. Validates and submits the
   * employee's information. Displays an error message if the submission fails, otherwise loads the
   * 'employees.fxml' page.
   *
   * @author Nicolas Bolouri
   */
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

  /**
   * Loads a specified FXML page into the employee content area. Catches and prints exceptions if
   * the file cannot be loaded.
   *
   * @author Nicolas Bolouri
   * @param String The FXML file to load, relative to '/ca/mcgill/ecse/assetplus/view/employees/'.
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

