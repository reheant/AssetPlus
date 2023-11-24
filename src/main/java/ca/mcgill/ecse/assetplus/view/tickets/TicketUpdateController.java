package ca.mcgill.ecse.assetplus.view.tickets;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.util.Objects;
public class TicketUpdateController {
  @FXML
  private AnchorPane mainContentArea;

  @FXML
  private void onHiClicked() {
    System.out.println("omg hiiii!!");
    //loadPage("manager.fxml");
  }

  private void loadPage(String fxmlFile) {
      try {
          Node page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ca/mcgill/ecse/assetplus/view/" + fxmlFile)));
          mainContentArea.getChildren().setAll(page);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
}
