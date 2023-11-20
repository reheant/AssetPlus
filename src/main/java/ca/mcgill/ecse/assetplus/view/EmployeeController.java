package ca.mcgill.ecse.assetplus.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class EmployeeController {
  String[] employeeEmails =
      {"employee1@ap.com", "employee2@ap.com", "employee3@ap.com", "employee4@ap.com",
          "employee5@ap.com", "employee6@ap.com", "employee7@ap.com", "employee8@ap.com",
          "employee9@ap.com", "employee10@ap.com", "employee11@ap.com", "employee12@ap.com",
          "employee13@ap.com", "employee14@ap.com", "employee15@ap.com", "employee16@ap.com",
          "employee17@ap.com", "employee18@ap.com", "employee19@ap.com", "employee20@ap.com"};

  @FXML
  private TextField employeeSearchBar;

  @FXML
  private ListView<String> employeeList;

  @FXML
  private void onSearchButtonClicked() {
    String employeeEmail = employeeSearchBar.getText();
    System.out.println(employeeEmail);
  }

  @FXML
  public void initialize() {
    employeeSearchBar.setFocusTraversable(false);

    employeeList.setFixedCellSize(50.0);
    employeeList.setCellFactory(lv -> new ListCell<String>() {
      @Override
      public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText(null);
        } else {
          setText(item);
          setStyle("-fx-font-size: 18pt;"); 
        }
      }
    });
    employeeList.setPrefHeight(10 * employeeList.getFixedCellSize());
    employeeList.getItems().addAll(employeeEmails);

  }

}
