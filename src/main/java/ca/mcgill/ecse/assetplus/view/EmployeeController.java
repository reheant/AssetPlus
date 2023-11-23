package ca.mcgill.ecse.assetplus.view;

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
import java.util.Objects;
import java.util.stream.Collectors;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;

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

  @FXML
  private void onAddEmployeeClicked() {
    loadPage("add-employee.fxml");
  }

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

  @FXML
  private void onClearButtonClicked() {
    employeeSearchBar.setText("");
    resetEmployeeList();
    resetCellFactory();
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


  private List<String> filterEmployeeList(String searchedEmail) {
    String[] employeeEmails = AssetPlusFeatureSet1Controller.getEmployeeEmails();
    return Arrays.stream(employeeEmails).filter(email -> email.equalsIgnoreCase(searchedEmail))
        .collect(Collectors.toList());
  }

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

  private void resetEmployeeList() {
    employeeList.getItems().clear();
    String[] employeeEmails = AssetPlusFeatureSet1Controller.getEmployeeEmails();
    employeeList.getItems().addAll(employeeEmails);
  }

    private void loadPage(String fxmlFile) {
    try {
      Node page = FXMLLoader.load(Objects.requireNonNull(
          getClass().getResource("/ca/mcgill/ecse/assetplus/view/employees" + fxmlFile)));
      employeeContentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
