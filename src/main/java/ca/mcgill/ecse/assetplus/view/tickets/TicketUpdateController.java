package ca.mcgill.ecse.assetplus.view.tickets;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class TicketUpdateController {
  @FXML
  private AnchorPane mainContentArea;

  @FXML
  private ListView<String> imageListView;

  @FXML
  private void onHiClicked() {
    System.out.println("omg hiiii!!");
    //loadPage("manager.fxml");
  }

  @FXML
  private void onAddImageButtonClicked() {
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("Add Image");
      dialog.setHeaderText(null);
      dialog.setContentText("Please enter the URL of the image:");

      // Traditional way to get the response value.
      Optional<String> result = dialog.showAndWait();
      result.ifPresent(url -> {
          // Here you would probably want to validate the URL
          imageListView.getItems().add(url);
      });
  }

    @FXML
    private void onDeleteImageButtonClicked() {
        String selectedUrl = imageListView.getSelectionModel().getSelectedItem();
        if (selectedUrl != null) {
            imageListView.getItems().remove(selectedUrl);
        } else {
            // No item is selected, show an alert or disable the delete button
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an image URL to delete.");
            alert.showAndWait();
        }
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
