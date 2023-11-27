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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SpecificAssetController {
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
  private String specificAssetInfo;

  @FXML
  private TextField purchaseDateFilter;
  @FXML
  private TextField assetNbFilter;
  @FXML
  private TextField floorNbFilter;
  @FXML
  private TextField roomNbFilter;

  private String assetTypeString;


  @FXML 
  public List<String> filterList(String searchedDate, String searchedAssetNb, String searchedFloorNb, String searchedRoomNb) {
    
    String[] assetData = AssetPlusFeatureSet3Controller.getSpecificAssetDataByAssetType(assetTypeString);
    List<String> finalData = new ArrayList<>();     
    for (int i = 0; i<assetData.length; i++) {
      String[] parsedString = assetData[i].split("\\s+");
      if (parsedString[4].equals(searchedDate) || searchedDate.isEmpty() &&
            (searchedAssetNb.isEmpty() || parsedString[1].substring(1).equals(searchedAssetNb)) &&
            (searchedFloorNb.isEmpty() || parsedString[2].equals(searchedFloorNb)) &&
            (searchedRoomNb.isEmpty() || parsedString[3].equals(searchedRoomNb))) {
            finalData.add(assetData[i]);
        }
    }
    return finalData;
  }

  @FXML
  private void filterButtonOnClick() {
    String searchedDate = purchaseDateFilter.getText();
    String searchedAssetNb = assetNbFilter.getText();
    String searchedFloorNb = floorNbFilter.getText();
    String searchedRoomNb = roomNbFilter.getText();
    List<String> filteredDates = filterList(searchedDate, searchedAssetNb, searchedFloorNb, searchedRoomNb);

    specificAssetList.getItems().clear();
    if (filteredDates.isEmpty()) {
      displayNoSearchResults();
    } else {
      specificAssetList.getItems().addAll(filteredDates);
      resetCellFactory();
    }
  }

  @FXML 
  private void clearButtonOnClick() {
    purchaseDateFilter.setText("");
    assetNbFilter.setText("");
    roomNbFilter.setText("");
    floorNbFilter.setText("");
    resetSpecificAssetList();
    resetCellFactory();
  }

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


  private void resetSpecificAssetList() {
    specificAssetList.getItems().clear();
    String[] assetData = AssetPlusFeatureSet3Controller.getSpecificAssetDataByAssetType(assetTypeString);
    specificAssetList.getItems().addAll(assetData);
  }

  @FXML
  public void addSpecificAssetOnClick(){
    loadPage("addSpecificAsset.fxml");

  }

  @FXML
  public void backButtonOnClick(){
    loadPage("../assetTypes/assets.fxml");
  }

  @FXML
  public void initialize(String assetType) {
    purchaseDateFilter.setPromptText("YYYY-MM-DD");
    assetNbFilter.setPromptText("Asset Number");
    floorNbFilter.setPromptText("Floor Number");
    roomNbFilter.setPromptText("Room Number");
    
    //String[] assetData = AssetPlusFeatureSet3Controller.getSpecificAssetData();
    System.out.println("2.5: " + assetTypeString);
    this.assetTypeString = assetType;
    String[] assetData = AssetPlusFeatureSet3Controller.getSpecificAssetDataByAssetType(assetType);
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

    for (int i = 0; i<assetData.length; i++) {
      //System.out.println(assetData[i]);
    }

    specificAssetList.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {

          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            specificAssetInfo = newValue;
            loadPage("viewSpecificAsset.fxml");
            
        }
        });



  }

  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/view/specificAsset/" + fxmlFile));
      Node page = loader.load();

      if (fxmlFile.equals("viewSpecificAsset.fxml")) {
          viewSpecificAssetController updateController = loader.getController();
          updateController.displayTitle(specificAssetInfo);
      }
      if (fxmlFile.equals("addSpecificAsset.fxml")) {
        addSpecificAssetController updateController = loader.getController();
        updateController.setAssetTypeString(assetTypeString);
      }
      

      specificAssetContentArea.getChildren().setAll(page);
  } catch (IOException e) {
      e.printStackTrace();
  }
}
}
