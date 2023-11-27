package ca.mcgill.ecse.assetplus.view.specificAsset;

import java.io.IOException;
import java.util.Objects;
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
  private String[] values;

  public void displayTitle(String assetInfo){
    values = assetInfo.split("\\s+");
    titleLabel.setText(values[0]+ " " + values[1]);
    assetNumber.setText(values[1]);
    purchasedDate.setText(values[4]);
    floorNumber.setText(values[2]);
    roomNumber.setText(values[3]);
    assetType.setText(values[0]);
  }

  public void backButtonOnClick() {
    loadPage("SpecificAsset.fxml");
  }

  public void editSpecificAssetOnClick() {
    loadPage("editSpecificAsset.fxml");
  }

  public void deleteSpecificAssetOnClick() {
    AssetPlusFeatureSet3Controller.deleteSpecificAsset(Integer.parseInt(values[1].substring(1)));
    loadPage("SpecificAsset.fxml");
  }




  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/view/specificAsset/" + fxmlFile));
      Node page = loader.load();

      if (fxmlFile.equals("editSpecificAsset.fxml")) {
          editSpecificAssetController updateController = loader.getController();
          updateController.setTextFields(values[1], values[4], values[2], values[3], values[0]);
      }

      viewSpecificAssetContentArea.getChildren().setAll(page);
  } catch (IOException e) {
      e.printStackTrace();
  }
}




}
