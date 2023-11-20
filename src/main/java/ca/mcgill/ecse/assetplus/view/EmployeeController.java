package ca.mcgill.ecse.assetplus.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;

public class EmployeeController {
  @FXML
  private Label employeeName;

  @FXML
  private Label employeeEmail;

  @FXML
  private Label employeePhoneNumber;

  @FXML
  private Label employeePassword;

  @FXML
  private TextField employeeSearchBar;

  @FXML
  private ListView<String> employeeList;

  @FXML
  private void onSearchButtonClicked() {
    String searchedEmail = employeeSearchBar.getText();
    System.out.println(searchedEmail);
  }

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

  private void setEmployeeInformation(String email) {
    String[] employeeInfo = AssetPlusFeatureSet1Controller.getEmployeeInformationByEmail(email);
    if (employeeInfo != null) {
      employeeName.setText(employeeInfo[0]);
      employeeEmail.setText(employeeInfo[1]); 
      employeePhoneNumber.setText(employeeInfo[2]);
      employeePassword.setText(employeeInfo[3]);
    } else {
      employeeName.setText("Information not available");
      employeeEmail.setText("Information not available");
      employeePhoneNumber.setText("Information not available");
      employeePassword.setText("Information not available");
    }
  }
}
