package ca.mcgill.ecse.assetplus.view.specificAsset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SpecificAssetController {
  private String specificAssetInfo;
  private String assetTypeString;

  @FXML
  private Button addSpecificAsset;

  @FXML
  private Button filterButton;

  @FXML
  private Button clearButton;

  @FXML
  private Button backButton;

  @FXML
  private AnchorPane specificAssetContentArea;

  @FXML
  private ListView<String> specificAssetList;

  @FXML
  private TextField purchaseDateFilter;

  @FXML
  private TextField assetNbFilter;

  @FXML
  private TextField floorNbFilter;

  @FXML
  private TextField roomNbFilter;

  /**
   * Initializes the specific asset page.
   * 
   * @author Rehean Thillainathalingam
   */
  @FXML
  public void initialize(String assetType) {
    purchaseDateFilter.setPromptText("YYYY-MM-DD");
    assetNbFilter.setPromptText("Asset Number");
    floorNbFilter.setPromptText("Floor Number");
    roomNbFilter.setPromptText("Room Number");
    this.assetTypeString = assetType;
    String[] assetData =
        AssetPlusFeatureSet3Controller.getSpecificAssetDataByAssetType(assetType, true);
    specificAssetList.setFixedCellSize(50.0);
    specificAssetList.setCellFactory(lv -> new ListCell<String>() {
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
    specificAssetList.setPrefHeight(10 * specificAssetList.getFixedCellSize());
    specificAssetList.getItems().addAll(assetData);
    specificAssetList.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {
          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue,
              String newValue) {
            specificAssetInfo = newValue;
            loadPage("viewSpecificAsset.fxml");
          }
        });
  }

  /**
   * Filters specific assets once filter button is clicked
   * 
   * @author Rehean Thillainathalingam
   */
  @FXML
  private void filterButtonOnClick() {
    String searchedDate = purchaseDateFilter.getText();
    String searchedAssetNb = assetNbFilter.getText();
    String searchedFloorNb = floorNbFilter.getText();
    String searchedRoomNb = roomNbFilter.getText();
    List<String> filteredDates =
        filterList(searchedDate, searchedAssetNb, searchedFloorNb, searchedRoomNb);

    specificAssetList.getItems().clear();
    if (filteredDates.isEmpty()) {
      displayNoSearchResults();
    } else {
      specificAssetList.getItems().addAll(filteredDates);
      resetCellFactory();
    }
  }

  /**
   * Loads add specific asset page once the add button is pressed.
   * 
   * @author Rehean Thillainathalingam
   */
  @FXML
  private void addSpecificAssetOnClick() {
    loadPage("addSpecificAsset.fxml");

  }

  /**
   * Loads assets page once the back button is pressed.
   * 
   * @author Rehean Thillainathalingam
   */
  @FXML
  private void backButtonOnClick() {
    loadPage("../assetTypes/assets.fxml");
  }

  /**
   * Clears filters once clear button is clicked.
   * 
   * @author Rehean Thillainathalingam
   */
  @FXML
  private void clearButtonOnClick() {
    purchaseDateFilter.setText("");
    assetNbFilter.setText("");
    roomNbFilter.setText("");
    floorNbFilter.setText("");
    resetSpecificAssetList();
    resetCellFactory();
  }

  /**
   * Filters specific assets based on user inputs
   * 
   * @author Rehean Thillainathalingam
   * @param searchedDate desired date filter
   * @param searchedAssetNb desired asset number filter
   * @param searchedFloorNb desired floor number filter
   * @param searchedRoomNb desired room number filter
   * @return A string list with the filtered data
   */
  private List<String> filterList(String searchedDate, String searchedAssetNb,
      String searchedFloorNb, String searchedRoomNb) {

    String[] assetData =
        AssetPlusFeatureSet3Controller.getSpecificAssetDataByAssetType(assetTypeString, false);
    List<String> finalData = new ArrayList<>();
    for (int i = 0; i < assetData.length; i++) {
      String[] parsedString = assetData[i].split("\\s+");
      if ((parsedString[4].equals(searchedDate) || searchedDate.isEmpty())
          && (searchedAssetNb.isEmpty() || parsedString[1].substring(1).equals(searchedAssetNb))
          && (searchedFloorNb.isEmpty() || parsedString[2].equals(searchedFloorNb))
          && (searchedRoomNb.isEmpty() || parsedString[3].equals(searchedRoomNb))) {
        finalData.add(parsedString[0] + " " + parsedString[1]);
      }
    }
    return finalData;
  }

  /**
   * Resets specific asset list data.
   * 
   * @author Rehean Thillainathalingam
   */
  private void resetCellFactory() {
    specificAssetList.setCellFactory(lv -> new ListCell<String>() {
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
   * Displays that no results were found in the specific asset list.
   * 
   * @author Rehean Thillainathalingam
   */
  private void displayNoSearchResults() {
    specificAssetList.getItems().add("No search results");
    specificAssetList.setCellFactory(lv -> new ListCell<String>() {
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
   * Resets the specific asset list
   * 
   * @author Rehean Thillainathalingam
   */
  private void resetSpecificAssetList() {
    specificAssetList.getItems().clear();
    String[] assetData =
        AssetPlusFeatureSet3Controller.getSpecificAssetDataByAssetType(assetTypeString, true);
    specificAssetList.getItems().addAll(assetData);
  }

  /**
   * Loads the corresponding page of the inputted fxml file
   * 
   * @author Rehean Thillainathalingam
   * @param fxmlFile fxml file name
   */
  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/ca/mcgill/ecse/assetplus/view/specificAsset/" + fxmlFile));
      Node page = loader.load();
      if (fxmlFile.equals("viewSpecificAsset.fxml")) {
        ViewSpecificAssetController updateController = loader.getController();
        updateController.initialize(specificAssetInfo);
      }
      if (fxmlFile.equals("addSpecificAsset.fxml")) {
        AddSpecificAssetController updateController = loader.getController();
        updateController.setAssetTypeString(assetTypeString);
      }
      specificAssetContentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
