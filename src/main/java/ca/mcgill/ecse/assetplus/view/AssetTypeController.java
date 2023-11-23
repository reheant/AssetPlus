package ca.mcgill.ecse.assetplus.view;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AssetTypeController {
  @FXML private Button assetPageButton;

  @FXML AnchorPane AssetTypeMainContent;

  @FXML
  private void assetPageButtonClicked(){
    loadPage("specificAsset.fxml");
    System.out.println("detected");
  }

  private void loadPage(String fxmlFile) {
        try {
          Node page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
          AssetTypeMainContent.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
