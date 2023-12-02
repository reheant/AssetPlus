package ca.mcgill.ecse.assetplus.view.assetTypes;

import java.io.IOException;
import java.util.Objects;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AssetTypeUpdateController {
  private String assetTypeUpdateName;
  private String assetTypeOldName;
  private String assetTypeOldLifespan;

  @FXML
  private AnchorPane assetTypeContentArea;

  @FXML
  private Label errorLabel;

  @FXML
  private TextField assetTypeName;

  @FXML
  private TextField assetTypeLifespan;

  /**
   * Initializes asset type name and lifespan
   *
   * @author Tiffany Miller
   */
  @FXML
  public void initialize() {
    assetTypeName.setFocusTraversable(false);
    assetTypeLifespan.setFocusTraversable(false);
  }

  /**
   * Sets assetTypeUpdateName variable to updated asset type name
   *
   * @param name The updated asset type name
   * @author Tiffany Miller
   */
  public void setAssetTypeUpdateName(String name) {
    this.assetTypeUpdateName = name;
  }

  /**
   * Sets assetTypeOldName variable to old asset type name
   *
   * @param name The old asset type name
   * @author Tiffany Miller
   */
  public void setAssetTypeOldName(String name) {
    this.assetTypeOldName = name;
  }

  /**
   * Sets assetTypeOldLifespan variable to old lifespan
   *
   * @param lifespan The old lifespan of an asset type
   * @author Tiffany Miller
   */
  public void setAssetTypeOldLifespan(String lifespan) {
    this.assetTypeOldLifespan = lifespan;
  }

  /**
   * Updates the UI with current asset type information
   *
   * @author Tiffany Miller
   */
  public void updateUIWithAssetTypeData() {
    if (assetTypeOldName != null) {
      assetTypeName.setPromptText("Old Name: " + assetTypeOldName);
    }
    if (assetTypeUpdateName != null) {
      assetTypeName.setText("Name (cannot modify): " + assetTypeUpdateName);
    }
    if (assetTypeOldLifespan != null) {
      assetTypeLifespan.setPromptText("Old Lifespan: " + assetTypeOldLifespan);
    }
  }

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
   * Updates the old asset type name and lifespan to new name and lifespan
   *
   * @author Tiffany Miller
   */
  @FXML
  private void onUpdateAssetTypeButtonClicked() {
    String name = assetTypeName.getText();
    String lifespan = assetTypeLifespan.getText();

    String result = "";
    try {
      int lifeInt = Integer.parseInt(lifespan);
      result += AssetPlusFeatureSet2Controller.updateAssetType(assetTypeOldName, name, lifeInt);
    } catch (Exception e) {
      result += "Please insert a valid integer";
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
