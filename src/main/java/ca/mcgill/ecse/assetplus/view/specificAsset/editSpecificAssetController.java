package ca.mcgill.ecse.assetplus.view.specificAsset;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class editSpecificAssetController {
  @FXML
  private AnchorPane editSpecificAssetContentArea;
  @FXML
  private Label assetNumber;
  @FXML
  private TextField purchaseDate; 
  @FXML
  private TextField assetType; 
  @FXML
  private TextField floorNb; 
  @FXML
  private TextField roomNb; 
  @FXML
  private Button backButton;
  @FXML
  private Button confirmSpecificAsset;

  @FXML
  public void backButtonOnClick(){
    loadPage("SpecificAsset.fxml");

  }

  public void setTextFields(String originalAssetNb, String originalPurchaseDate, String originalFloorNb, String originalRoomNb,String originalAssetType){
    assetNumber.setText(originalAssetNb);
    purchaseDate.setText(originalPurchaseDate);
    floorNb.setText(originalFloorNb);
    roomNb.setText(originalRoomNb);
    assetType.setText(originalAssetType);
  }

  @FXML
  public void confirmSpecificAssetOnClick() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date utilDate;
    try {
      utilDate = dateFormat.parse(purchaseDate.getText());
      java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
      AssetPlusFeatureSet3Controller.updateSpecificAsset(Integer.parseInt(assetNumber.getText().substring(1)), Integer.parseInt(floorNb.getText()),Integer.parseInt(roomNb.getText()), sqlDate, assetType.getText());
      loadPage("SpecificAsset.fxml");
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }









  private void loadPage(String fxmlFile) {
        try {
          Node page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/specificAsset/" + fxmlFile)));
          editSpecificAssetContentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
