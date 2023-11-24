package ca.mcgill.ecse.assetplus.view.AssetType;

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
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;

public class AssetTypeController {
  @FXML
  private AnchorPane assetTypeContentArea;

  @FXML
  private Label assetTypeName;

  @FXML
  private Label assetTypeLifespan;

  @FXML
  private TextField assetTypeSearchBar;

  @FXML
  private ListView<String> assetTypeList;

  @FXML
  private void onAddEmployeeClicked() {
    loadPage("add-asset-type.fxml");
  }

  @FXML
  private void onUpdateAssetTypeClicked() {
      String selectedAssetType = assetTypeList.getSelectionModel().getSelectedItem();
      if (selectedAssetType != null && !selectedAssetType.equals("No search results")) {
          loadPage("update-employee.fxml");
      }
  }  

  @FXML
  private void onDeleteAssetTypeClicked() {
    String name = assetTypeName.getText().strip();
    AssetPlusFeatureSet2Controller.deleteAssetType(name);
    loadPage("employees.fxml");
  }

  @FXML
  private void onSearchButtonClicked() {
    String searchedName = assetTypeSearchBar.getText();
    List<String> filteredNames = filterEmployeeList(searchedName);

    assetTypeList.getItems().clear();
    if (filteredNames.isEmpty()) {
      displayNoSearchResults();
    } else {
      assetTypeList.getItems().addAll(filteredNames);
      resetCellFactory();
    }
  }

  @FXML
  private void onClearButtonClicked() {
    assetTypeSearchBar.setText("");
    resetEmployeeList();
    resetCellFactory();
  }

  @FXML
  public void initialize() {
    assetTypeSearchBar.setFocusTraversable(false);

    String[] assetTypeNames = AssetPlusFeatureSet2Controller.getAssetTypes();
    assetTypeList.setFixedCellSize(50.0);
    assetTypeList.setCellFactory(lv -> new ListCell<String>() {
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
    assetTypeList.setPrefHeight(10 * assetTypeList.getFixedCellSize());
    assetTypeList.getItems().addAll(employeeEmails);

    assetTypeList.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {

          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue,
              String newValue) {
            setEmployeeInformation(newValue);
          }
        });
  }

  private void setInfo(String name) {
    int lifespan = AssetPlusFeatureSet2Controller.getLifespanByName(name);
    if (lifespan != 0) {
      assetTypeName.setText(name);
      assetTypeLifespan.setText(lifespan);
    } else {
      assetTypeName.setText("Information not available");
      assetTypeLifespan.setText("Information not available");
    }
  }


  private List<String> filterAssetTypeList(String searchedName) {
    String[] assetTypeNames = AssetPlusFeatureSet2Controller.getAssetTypes();
    return Arrays.stream(assetTypeNames).filter(name -> name.equalsIgnoreCase(searchedName))
        .collect(Collectors.toList());
  }

  private void displayNoSearchResults() {
    assetTypeList.getItems().add("No search results");
    assetTypeList.setCellFactory(lv -> new ListCell<String>() {
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
    assetTypeList.setCellFactory(lv -> new ListCell<String>() {
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
    assetTypeList.getItems().clear();
    String[] assetTypeNames = AssetPlusFeatureSet2Controller.getAssetTypes();
    assetTypeList.getItems().addAll(assetTypeNames);
  }

  private void loadPage(String fxmlFile) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/view/employees/" + fxmlFile));
        Node page = loader.load();

        if (fxmlFile.equals("update-asset-type.fxml")) {
            AssetTypeUpdateController updateController = loader.getController();
            updateController.setAssetTypeUpdateName(assetTypeLifespan.getText());
            updateController.setAssetTypeOldName(assetTypeName.getText());
            updateController.updateUIWithAssetTypeData();
        }

        assetTypeContentArea.getChildren().setAll(page);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}