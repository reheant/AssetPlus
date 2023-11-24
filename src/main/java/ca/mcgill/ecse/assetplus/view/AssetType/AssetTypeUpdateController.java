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

public class AssetTypeUpdateController {
  private String assetTypeUpdateName;
  private String assetTypeOldName;
  private String assetTypeOldLifespan;

  public void setAssetTypeUpdateName(String name) {
    this.assetTypeUpdateName = name;
  }

  public void setAssetTypeOldName(String name) {
    this.assetTypeOldName = name;
  }

  public void setAssetTypeOldLifespan(String lifespan) {
    this.assetTypeOldLifespan = lifespan;
  }

  @FXML
  private AnchorPane assetTypeContentArea;

  @FXML
  private Label errorLabel;

  @FXML
  private TextField assetTypeName;

  @FXML
  private TextField assetTypeLifespan;

  @FXML
  private void onCancelButtonClicked() {
    loadPage("assets.fxml");
  }

  @FXML
  private void onUpdateAssetTypeButtonClicked() {
    String name = assetTypeName.getText().strip().equals("") ? this.assetTypeOldName
        : assetTypeName.getText().strip();

    String lifespan =
        assetTypeLifespan.getText().strip().equals("") ? this.assetTypeOldLifespan
            : assetTypeLifespan.getText().strip();

    String result =
        AssetPlusFeatureSet2Controller.updateAssetType(assetTypeOldName, name, Integer.parseInt(lifespan));

    if (!result.equals("")) {
      errorLabel.setText(result);
      return;
    } else {
      loadPage("assets.fxml");
    }
  }

  @FXML
  public void initialize() {
    assetTypeName.setFocusTraversable(false); 
    assetTypeLifespan.setFocusTraversable(false);
  }

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

  private void loadPage(String fxmlFile) {
    try {
      Node page = FXMLLoader.load(Objects.requireNonNull(
          getClass().getResource("/ca/mcgill/ecse/assetplus/view/employees/" + fxmlFile)));
      assetTypeContentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}