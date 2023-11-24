package ca.mcgill.ecse.assetplus.view;

import java.io.IOException;
import java.util.Objects;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class SpecificAssetController {
  @FXML
  private Button addSpecificAsset;
  @FXML
  private AnchorPane specificAssetContentArea;
  @FXML
  private ListView<String> specificAssetList;
  private String specificAssetInfo;
  @FXML
  public void addSpecificAssetOnClick(){
    loadPage("addSpecificAsset.fxml");

  }

  @FXML
  public void initialize() {
    String[] assetData = AssetPlusFeatureSet3Controller.getSpecificAssetData();
    
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
    //specificAssetList.setItems(observableList);
    specificAssetList.getItems().addAll(assetData);

    specificAssetList.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {

          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            System.out.println("ListView selection changed from oldValue = " 
                    + oldValue + " to newValue = " + newValue);
            specificAssetInfo = newValue;
            loadPage("viewSpecificAsset.fxml");
            
        }
        });



  }

  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile));
      Node page = loader.load();

      if (fxmlFile.equals("viewSpecificAsset.fxml")) {
          viewSpecificAssetController updateController = loader.getController();
          updateController.displayTitle(specificAssetInfo);
      }

      specificAssetContentArea.getChildren().setAll(page);
  } catch (IOException e) {
      e.printStackTrace();
  }
}
}
