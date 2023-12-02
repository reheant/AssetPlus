package ca.mcgill.ecse.assetplus.view.specificAsset;

import java.io.IOException;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ViewSpecificAssetController {
  private String[] specificData;
  private String titleLabelString;

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

  /**
   * Initializes the view specific asset page
   * 
   * @author Rehean Thillainathalingam
   * @param titleLabelString A string label for the title of the page
   */
  @FXML
  public void initialize(String titleLabelString) {

    this.titleLabelString = titleLabelString;
    specificData = AssetPlusFeatureSet3Controller.getSpecificAssetFromTitle(titleLabelString);
    displayTitle(specificData);
  }

  /**
   * Loads specific asset page once back button is clicked.
   * 
   * @author Rehean Thillainathalingam
   */
  @FXML
  private void backButtonOnClick() {
    loadPage("SpecificAsset.fxml");
  }

  /**
   * Loads edit specific asset page once edit button is clicked.
   * 
   * @author Rehean Thillainathalingam
   */
  @FXML
  private void editSpecificAssetOnClick() {
    loadPage("editSpecificAsset.fxml");
  }

  /**
   * Deletes specific asset and loads specific asset page.
   * 
   * @author Rehean Thillainathalingam
   */
  @FXML
  private void deleteSpecificAssetOnClick() {
    AssetPlusFeatureSet3Controller.deleteSpecificAsset(Integer.parseInt(specificData[1]));
    loadPage("SpecificAsset.fxml");
  }

  /**
   * Sets the text for the labels on the view page
   * 
   * @author Rehean Thillainathalingam
   * @param specificData String array of the date required.
   */
  private void displayTitle(String[] specificData) {
    titleLabel.setText(specificData[0] + " #" + specificData[1]);
    assetNumber.setText(specificData[1]);
    purchasedDate.setText(specificData[4]);
    floorNumber.setText(specificData[2]);
    roomNumber.setText(specificData[3]);
    assetType.setText(specificData[0]);
  }

  /**
   * Loads the corresponding page of the inputted fxml file
   * 
   * @author Rehean Thillainathalingam
   * @param String AssetType name string
   */
  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/ca/mcgill/ecse/assetplus/view/specificAsset/" + fxmlFile));
      Node page = loader.load();

      if (fxmlFile.equals("editSpecificAsset.fxml")) {
        EditSpecificAssetController updateController = loader.getController();
        updateController.setTextFields(specificData[1], specificData[4], specificData[2],
            specificData[3], specificData[0], titleLabelString);
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
