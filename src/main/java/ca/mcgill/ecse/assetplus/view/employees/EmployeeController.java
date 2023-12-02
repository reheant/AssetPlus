package ca.mcgill.ecse.assetplus.view.employees;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;

public class EmployeeController {

  @FXML
  private AnchorPane employeeContentArea;

  @FXML
  private Label employeeName;

  @FXML
  private Label employeeEmailLabel;

  @FXML
  private Label employeePhoneNumber;

  @FXML
  private Label employeePassword;

  @FXML
  private TextField employeeSearchBar;

  @FXML
  private ListView<String> employeeList;

  /**
   * Initializes the employee list and sets up the UI. Loads employee emails and sets up the list
   * cell factory.
   *
   * @author Nicolas Bolouri
   */

  @FXML
  public void initialize() {
    employeeSearchBar.setFocusTraversable(false);

    String[] employeeEmails = AssetPlusFeatureSet1Controller.getEmployeeEmails();
    employeeList.setFixedCellSize(50.0);
    employeeList.setCellFactory(lv -> new ListCell<String>() {
      @Override
      public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText(null);
        } else {
          setText(item);
          setStyle("-fx-font-size: 16pt;");
        }
      }
    });
    employeeList.setPrefHeight(10 * employeeList.getFixedCellSize());
    employeeList.getItems().addAll(employeeEmails);

    employeeList.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {

          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue,
              String newValue) {
            setEmployeeInformation(newValue);
          }
        });
  }

  /**
   * Loads the 'add-employee.fxml' page when Add Employee is clicked.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onAddEmployeeClicked() {
    loadPage("add-employee.fxml");
  }

  /**
   * Loads the 'update-employee.fxml' page for the selected employee. Does nothing if no employee is
   * selected or the selection is invalid.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onUpdateEmployeeClicked() {
    String selectedEmployeeEmail = employeeList.getSelectionModel().getSelectedItem();
    if (selectedEmployeeEmail != null && !selectedEmployeeEmail.equals("No search results")) {
      loadPage("update-employee.fxml");
    }
  }

  /**
   * Deletes the employee with the displayed email and loads the 'employees.fxml' page.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onDeleteEmployeeClicked() {
    String email = employeeEmailLabel.getText().strip();
    AssetPlusFeatureSet6Controller.deleteEmployeeOrGuest(email);
    loadPage("employees.fxml");
  }

  /**
   * Searches and displays employees matching the entered email. Shows 'No search results' if no
   * match is found.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onSearchButtonClicked() {
    String searchedEmail = employeeSearchBar.getText();
    List<String> filteredEmails = filterEmployeeList(searchedEmail);

    employeeList.getItems().clear();
    if (filteredEmails.isEmpty()) {
      displayNoSearchResults();
    } else {
      employeeList.getItems().addAll(filteredEmails);
      resetCellFactory();
    }
  }

  /**
   * Clears the search bar, resets the employee list and cell factory.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onClearButtonClicked() {
    employeeSearchBar.setText("");
    resetEmployeeList();
    resetCellFactory();
  }

  /**
   * Sets the employee information display for the given email. Shows 'Information not available' if
   * the email is not found.
   * 
   * @author Nicolas Bolouri
   * @param email The email of the employee to display information for.
   */
  private void setEmployeeInformation(String email) {
    String[] employeeInfo = AssetPlusFeatureSet1Controller.getEmployeeInformationByEmail(email);
    if (employeeInfo != null) {
      employeeName.setText(employeeInfo[0]);
      employeeEmailLabel.setText(employeeInfo[1]);
      employeePhoneNumber.setText(employeeInfo[2]);
      employeePassword.setText(employeeInfo[3]);
    } else {
      employeeName.setText("Information not available");
      employeeEmailLabel.setText("Information not available");
      employeePhoneNumber.setText("Information not available");
      employeePassword.setText("Information not available");
    }
  }

  /**
   * Filters the employee list based on the searched email.
   *
   * @author Nicolas Bolouri
   * @param searchedEmail The email to filter the employee list by.
   * @return A list of emails matching the search criteria.
   */
  private List<String> filterEmployeeList(String searchedEmail) {
    String[] employeeEmails = AssetPlusFeatureSet1Controller.getEmployeeEmails();
    return Arrays.stream(employeeEmails).filter(email -> email.equalsIgnoreCase(searchedEmail))
        .collect(Collectors.toList());
  }

  /**
   * Displays 'No search results' in the employee list. Adjusts the cell factory for this specific
   * item.
   *
   * @author Nicolas Bolouri
   */
  private void displayNoSearchResults() {
    employeeList.getItems().add("No search results");
    employeeList.setCellFactory(lv -> new ListCell<String>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
          setGraphic(null);
        } else {
          setText(item);
          if (item.equals("No search results")) {
            setDisable(true);
            setStyle("-fx-font-style: italic;");
            setStyle("-fx-font-size: 16pt;");
          }
        }
      }
    });
  }

  /**
   * Resets the cell factory of the employee list to its default state.
   *
   * @author Nicolas Bolouri
   */
  private void resetCellFactory() {
    employeeList.setCellFactory(lv -> new ListCell<String>() {
      @Override
      public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText(null);
        } else {
          setText(item);
          setStyle("-fx-font-size: 16pt;");
        }
      }
    });
  }

  /**
   * Resets the employee list to show all employees.
   *
   * @author Nicolas Bolouri
   */
  private void resetEmployeeList() {
    employeeList.getItems().clear();
    String[] employeeEmails = AssetPlusFeatureSet1Controller.getEmployeeEmails();
    employeeList.getItems().addAll(employeeEmails);
  }

  /**
   * Loads a specified FXML page into the employee content area. Sets up controller data for
   * 'update-employee.fxml'. Catches and prints exceptions if the file cannot be loaded.
   *
   * @author Nicolas Bolouri
   * @param fxmlFile The FXML file to load, relative to '/ca/mcgill/ecse/assetplus/view/employees/'.
   */
  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/ca/mcgill/ecse/assetplus/view/employees/" + fxmlFile));
      Node page = loader.load();

      if (fxmlFile.equals("update-employee.fxml")) {
        UpdateEmployeeController updateController = loader.getController();
        updateController.setEmployeeUpdateEmail(employeeEmailLabel.getText());
        updateController.setEmployeeOldName(employeeName.getText());
        updateController.setEmployeeOldPhoneNumber(employeePhoneNumber.getText());
        updateController.setEmployeeOldPassword(employeePassword.getText());
        updateController.updateUIWithEmployeeData();
      }

      employeeContentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
