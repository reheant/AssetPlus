package ca.mcgill.ecse.assetplus.view;

import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
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
  private TextField expectedLifespan;
  @FXML
  private TextField floorNumber;
  @FXML
  private TextField roomNumber;
  @FXML 
  private Button backButton;
  @FXML
  private Label errorLabel;


  @FXML
  public void addSpecificAssetOnClick(){
    String assetNb = assetNumber.getText().strip(); //TODO: THIS SHOULDNT BE INPUT BY THE USER????
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
      AssetPlusFeatureSet2Controller.addAssetType("Tiffany", 23);
      //String result =
      AssetPlusFeatureSet3Controller.addSpecificAsset(intAssetnb, intFloorNb, intRoomNb, sqlDate, "Tiffany"); //TODO: TIFFANY ASSET TYPE
      //if (result.equals("")) {
      //System.out.println(AssetPlusFeatureSet3Controller.getSpecificAssetNumbers()[0]);
      loadPage("SpecificAsset.fxml");
      //} 
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
          Node page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
          addSpecificAssetContentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
