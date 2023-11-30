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
  private java.sql.Date sqlDate;

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
    String result = "";
    int intAssetnb = -1;
    int intFloorNb = -1;
    int intRoomNb = -2;


    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date utilDate;
    if (assetNb.equals("") || purchased.equals("") || floorNb.equals("") || roomNb.equals("")){
      result += "Please do not leave any empty values";
    } else {
      try {
      utilDate = dateFormat.parse(purchased);
      sqlDate = new java.sql.Date(utilDate.getTime());
    } catch (ParseException e) {
      result += "Please enter a valid date. (yyyy-mm-dd) \n";
      e.printStackTrace();
    }
    try {
      intAssetnb = Integer.parseInt(assetNb);
      intFloorNb = Integer.parseInt(floorNb);
      intRoomNb = Integer.parseInt(roomNb);
    } catch (Exception e) {
      result += "Please enter valid numbers with no characters \n";
    }

    if (result.equals("")) {
      result += AssetPlusFeatureSet3Controller.addSpecificAsset(intAssetnb, intFloorNb, intRoomNb, sqlDate, assetTypeString); 
    }}
    if (!result.equals("")) {
      errorLabel.setText(result);
      return;
    } else {
      loadPage("SpecificAsset.fxml");
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
