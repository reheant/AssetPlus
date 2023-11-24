package ca.mcgill.ecse.assetplus.view;

import java.io.IOException;
import java.util.Objects;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
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
  @FXML
  public void addSpecificAssetOnClick(){
    loadPage("addSpecificAsset.fxml");

  }

  @FXML
  public void initialize() {
    String[] assetNumbers = AssetPlusFeatureSet3Controller.getSpecificAssetData();
    
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
    specificAssetList.getItems().addAll(assetNumbers);



  }

  private void loadPage(String fxmlFile) {
        try {
          Node page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
          specificAssetContentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
