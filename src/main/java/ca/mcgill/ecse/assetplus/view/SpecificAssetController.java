package ca.mcgill.ecse.assetplus.view;

import java.io.IOException;
import java.util.Objects;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class SpecificAssetController {
  @FXML
  private Button addSpecificAsset;
  @FXML
  private AnchorPane specificAssetContentArea;
  @FXML
  public void addSpecificAssetOnClick(){
    loadPage("addSpecificAsset.fxml");

  }

  @FXML
  public void initialize() {
    
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
