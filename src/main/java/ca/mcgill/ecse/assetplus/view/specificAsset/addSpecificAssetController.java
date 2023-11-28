package ca.mcgill.ecse.assetplus.view.specificAsset;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class addSpecificAssetController {
  @FXML
  private AnchorPane addSpecificAssetContentArea;
  @FXML
  private Button addSpecificAsset;
  @FXML
  private TextField assetNumber;
  @FXML
  private TextField purchasedDate;
  @FXML
  private Label assetType;
  @FXML
  private TextField floorNumber;
  @FXML
  private TextField roomNumber;
  @FXML 
  private Button backButton;
  @FXML
  private Label errorLabel;

  private String assetTypeString;

  public void setAssetTypeString(String string) {
    assetTypeString = string;
    assetType.setText(assetTypeString);
  }

  @FXML
  public void addSpecificAssetOnClick(){
    String assetNb = assetNumber.getText().strip(); 
    String purchased = purchasedDate.getText().strip();
    String floorNb = floorNumber.getText().strip();
    String roomNb = roomNumber.getText().strip();

    int intAssetnb = Integer.parseInt(assetNb);
    int intFloorNb = Integer.parseInt(floorNb);
    int intRoomNb = Integer.parseInt(roomNb);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date utilDate;
    try {
      utilDate = dateFormat.parse(purchased);
      java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
      AssetPlusFeatureSet3Controller.addSpecificAsset(intAssetnb, intFloorNb, intRoomNb, sqlDate, assetTypeString); 
      loadPage("SpecificAsset.fxml");
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void backButtonOnClick(){
    loadPage("SpecificAsset.fxml");

  }

  private void loadPage(String fxmlFile) {
        try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/view/specificAsset/" + fxmlFile));
          Node page = loader.load();

          if (fxmlFile.equals("SpecificAsset.fxml")) {
            SpecificAssetController updateController = loader.getController();
            updateController.initialize(assetTypeString);
          }
          addSpecificAssetContentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
