package ca.mcgill.ecse.assetplus.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Objects;


public class UpdateManagerController {
  
  @FXML
  private AnchorPane managerContentArea;

  @FXML
  private void onCancelButtonClicked() {
    loadPage("manager.fxml");
  }

    private void loadPage(String fxmlFile) {
    try {
      Node page = FXMLLoader.load(Objects.requireNonNull(
          getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
      managerContentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}




