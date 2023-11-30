package ca.mcgill.ecse.assetplus.view.specificAsset;

import java.io.IOException;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class viewSpecificAssetController {
  @FXML
  private Label assetNumber;
  @FXML
  private Label purchasedDate;
  @FXML
  private Label expectedLifespan;
  @FXML
  private Label floorNumber;
  @FXML
  private Label roomNumber;
  @FXML
  private Label titleLabel;
  @FXML
  private Label assetType;
  @FXML
  private Button backButton;
  @FXML
  private Button editSpecificAsset;
  @FXML
  private Button deleteSpecificAsset1;
  @FXML
  private AnchorPane viewSpecificAssetContentArea;
  private String[] specificData;
  private String titleLabelString;

  public void displayTitle(String[] specificData){
    titleLabel.setText(specificData[0]+ " #" + specificData[1]);
    assetNumber.setText(specificData[1]);
    purchasedDate.setText(specificData[4]);
    floorNumber.setText(specificData[2]);
    roomNumber.setText(specificData[3]);
    assetType.setText(specificData[0]);
  }

  public void backButtonOnClick() {
    loadPage("SpecificAsset.fxml");
  }

  public void editSpecificAssetOnClick() {
    loadPage("editSpecificAsset.fxml");
  }

  public void deleteSpecificAssetOnClick() {
    AssetPlusFeatureSet3Controller.deleteSpecificAsset(Integer.parseInt(specificData[1]));
    loadPage("SpecificAsset.fxml");
  }

@FXML
  public void initialize(String titleLabelString) {
    
    this.titleLabelString = titleLabelString;
    specificData = AssetPlusFeatureSet3Controller.getSpecificAssetFromTitle(titleLabelString);
    displayTitle(specificData);
    
  }

  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/view/specificAsset/" + fxmlFile));
      Node page = loader.load();

      if (fxmlFile.equals("editSpecificAsset.fxml")) {
          editSpecificAssetController updateController = loader.getController();
          updateController.setTextFields(specificData[1], specificData[4], specificData[2], specificData[3], specificData[0], titleLabelString);
      }
      if (fxmlFile.equals("SpecificAsset.fxml")) {
        SpecificAssetController updateController = loader.getController();
        updateController.initialize(specificData[0]);
      }

      viewSpecificAssetContentArea.getChildren().setAll(page);
  } catch (IOException e) {
      e.printStackTrace();
  }
}
}
