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

  @FXML
  private void onCancelButtonClicked() {
    loadPage("assets.fxml");
  }

  @FXML
  private void onAddAssetTypeButtonClicked() {
    String name = assetTypeName.getText().strip();
    String lifespan = assetTypeLifespan.getText().strip();

    String result =
        AssetPlusFeatureSet2Controller.addAssetType(name, Integer.parseInt(lifespan));

    if (!result.equals("")) {
      errorLabel.setText(result);
      return;
    } else {
      loadPage("assets.fxml");
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