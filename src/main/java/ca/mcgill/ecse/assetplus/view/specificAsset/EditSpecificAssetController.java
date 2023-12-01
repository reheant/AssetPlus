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

public class EditSpecificAssetController {
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
  private Label titleLabel;
  @FXML
  private Label errorLabel;
  private java.sql.Date sqlDate;

   /**
   * Loads specific asset page once back button is clicked
   * 
   * @author Rehean Thillainathalingam
   */
  @FXML
  public void backButtonOnClick() {
    loadPage("SpecificAsset.fxml");
  }
   /**
   * Loads specific asset page once back button is clicked
   * 
   * @author Rehean Thillainathalingam
   * @param originalAssetNb The asset number of the current specific asset
   * @param originalPurchaseDate The purchase date of the current specific asset
   * @param originalFloorNb The floor number of the current specific asset
   * @param originalRoomNb The room number of the current specific asset
   * @param originalAssetType The asset type of the current specific asset
   * @param label The title label for the page
   */
  public void setTextFields(String originalAssetNb, String originalPurchaseDate,
      String originalFloorNb, String originalRoomNb, String originalAssetType, String label) {
    assetNumber.setText(originalAssetNb);
    purchaseDate.setText(originalPurchaseDate);
    floorNb.setText(originalFloorNb);
    roomNb.setText(originalRoomNb);
    assetType.setText(originalAssetType);
    titleLabel.setText(label);
  }

  /**
   * Updates the specific asset once the update button is pressed.
   * 
   * @author Rehean Thillainathalingam
   */
  @FXML
  public void confirmSpecificAssetOnClick() {
    String result = "";
    int intAssetnb = -1;
    int intFloorNb = -1;
    int intRoomNb = -2;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date utilDate;
    if (assetNumber.getText().equals("") || purchaseDate.getText().equals("")
        || floorNb.getText().equals("") || roomNb.getText().equals("")) {
      result += "Please do not leave any empty values";
    } else {
      try {
        utilDate = dateFormat.parse(purchaseDate.getText());
        sqlDate = new java.sql.Date(utilDate.getTime());
      } catch (ParseException e) {
        result += "Please enter a valid date. (yyyy-mm-dd) \n";
        e.printStackTrace();
      }
      try {
        intAssetnb = Integer.parseInt(assetNumber.getText());
        intFloorNb = Integer.parseInt(floorNb.getText());
        intRoomNb = Integer.parseInt(roomNb.getText());
      } catch (Exception e) {
        result += "Please enter valid numbers with no characters \n";
      }
      if (result.equals("")) {
        result += AssetPlusFeatureSet3Controller.updateSpecificAsset(intAssetnb, intFloorNb,
            intRoomNb, sqlDate, assetType.getText());
      }
    }
    if (!result.equals("")) {
      errorLabel.setText(result);
      return;
    } else {
      loadPage("SpecificAsset.fxml");
    }
  }
  /**
   * Loads the corresponding page of the inputted fxml file
   * 
   * @author Rehean Thillainathalingam
   * @param fxmlFile fxml file name 
   */
  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/ca/mcgill/ecse/assetplus/view/specificAsset/" + fxmlFile));
      Node page = loader.load();
      if (fxmlFile.equals("SpecificAsset.fxml")) {
        SpecificAssetController updateController = loader.getController();
        updateController.initialize(assetType.getText());
      }
      editSpecificAssetContentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
