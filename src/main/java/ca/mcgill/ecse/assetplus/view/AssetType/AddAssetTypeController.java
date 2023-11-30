package ca.mcgill.ecse.assetplus.view.AssetType;

import java.io.IOException;
import java.util.Objects;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddAssetTypeController {
  @FXML
  private AnchorPane assetTypeContentArea;

  @FXML
  private Label errorLabel;

  @FXML
  private TextField assetTypeName;

  @FXML
  private TextField assetTypeLifespan;

  /**
   * Returns to default asset type page once cancel button is clicked
   *
   * @author Tiffany Miller
   */
  @FXML
  private void onCancelButtonClicked() {
    loadPage("assets.fxml");
  }

  /**
   * Adds asset type once button is clicked
   *
   * @author Tiffany Miller
   */
  @FXML
  private void onAddAssetTypeButtonClicked() {
    String name = assetTypeName.getText().strip();
    String lifespan = assetTypeLifespan.getText().strip();
    String result = "";
    if (name.equals("") && lifespan.equals("")){
      result += "Please enter name and lifespan to save";
    }

    else{
      try {
        int lifeInt = Integer.parseInt(lifespan);
        result += AssetPlusFeatureSet2Controller.addAssetType(name, lifeInt);
      } catch (Exception e) {
        result += "Please insert a valid integer";
      }
    }

    if (!result.equals("")) {
      errorLabel.setText(result);
      return;
    } else {
      loadPage("assets.fxml");
    }
  }

  /**
   * Loads page of the given fxml file
   *
   * @author Tiffany Miller
   * @param fxmlFile The name of the fxml file one wishes to load
   */
  private void loadPage(String fxmlFile) {
    try {
      Node page = FXMLLoader.load(Objects.requireNonNull(
              getClass().getResource("/ca/mcgill/ecse/assetplus/view/assetTypes/" + fxmlFile)));
      assetTypeContentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}