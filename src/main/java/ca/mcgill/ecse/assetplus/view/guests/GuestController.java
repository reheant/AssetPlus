package ca.mcgill.ecse.assetplus.view.guests;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;

public class GuestController {
  @FXML
  private AnchorPane guestContentArea;

  @FXML
  private Label guestName;

  @FXML
  private Label guestEmailLabel;

  @FXML
  private Label guestPhoneNumber;

  @FXML
  private Label guestPassword;

  @FXML
  private TextField guestSearchBar;

  @FXML
  private ListView<String> guestList;

  @FXML
  private void onAddGuestClicked() {
    loadPage("add-guest.fxml");
  }

  @FXML
  private void onUpdateGuestClicked() {
    String selectedGuestEmail = guestList.getSelectionModel().getSelectedItem();
    if (selectedGuestEmail != null && !selectedGuestEmail.equals("No search results")) {
      loadPage("update-guest.fxml");
    }
  }

  @FXML
  private void onDeleteGuestClicked() {
    String email = guestEmailLabel.getText().strip();
    AssetPlusFeatureSet6Controller.deleteEmployeeOrGuest(email);
    loadPage("guests.fxml");
  }

  @FXML
  private void onSearchButtonClicked() {
    String searchedEmail = guestSearchBar.getText();
    List<String> filteredEmails = filterGuestList(searchedEmail);

    guestList.getItems().clear();
    if (filteredEmails.isEmpty()) {
      displayNoSearchResults();
    } else {
      guestList.getItems().addAll(filteredEmails);
      resetCellFactory();
    }
  }

  @FXML
  private void onClearButtonClicked() {
    guestSearchBar.setText("");
    resetGuestList();
    resetCellFactory();
  }

  @FXML
  public void initialize() {
    guestSearchBar.setFocusTraversable(false);

    String[] guestEmails = AssetPlusFeatureSet1Controller.getGuestEmails();
    guestList.setFixedCellSize(50.0);
    guestList.setCellFactory(lv -> new ListCell<String>() {
      @Override
      public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText(null);
        } else {
          setText(item);
          setStyle("-fx-font-size: 16pt;");
        }
      }
    });
    guestList.setPrefHeight(10 * guestList.getFixedCellSize());
    guestList.getItems().addAll(guestEmails);

    guestList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        setGuestInformation(newValue);
      }
    });
  }

  private void setGuestInformation(String email) {
    String[] guestInfo = AssetPlusFeatureSet1Controller.getGuestInformationByEmail(email);
    if (guestInfo != null) {
      guestName.setText(guestInfo[0]);
      guestEmailLabel.setText(guestInfo[1]);
      guestPhoneNumber.setText(guestInfo[2]);
      guestPassword.setText(guestInfo[3]);
    } else {
      guestName.setText("Information not available");
      guestEmailLabel.setText("Information not available");
      guestPhoneNumber.setText("Information not available");
      guestPassword.setText("Information not available");
    }
  }

  private List<String> filterGuestList(String searchedEmail) {
    String[] guestEmails = AssetPlusFeatureSet1Controller.getGuestEmails();
    return Arrays.stream(guestEmails).filter(email -> email.equalsIgnoreCase(searchedEmail))
        .collect(Collectors.toList());
  }

  private void displayNoSearchResults() {
    guestList.getItems().add("No search results");
    guestList.setCellFactory(lv -> new ListCell<String>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
          setGraphic(null);
        } else {
          setText(item);
          if (item.equals("No search results")) {
            setDisable(true);
            setStyle("-fx-font-style: italic;");
            setStyle("-fx-font-size: 16pt;");
          }
        }
      }
    });
  }

  private void resetCellFactory() {
    guestList.setCellFactory(lv -> new ListCell<String>() {
      @Override
      public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText(null);
        } else {
          setText(item);
          setStyle("-fx-font-size: 16pt;");
        }
      }
    });
  }

  private void resetGuestList() {
    guestList.getItems().clear();
    String[] guestEmails = AssetPlusFeatureSet1Controller.getGuestEmails();
    guestList.getItems().addAll(guestEmails);
  }

  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/ca/mcgill/ecse/assetplus/view/guests/" + fxmlFile));
      Node page = loader.load();

      if (fxmlFile.equals("update-guest.fxml")) {
        UpdateGuestController updateController = loader.getController();
        updateController.setGuestUpdateEmail(guestEmailLabel.getText());
        updateController.setGuestOldName(guestName.getText());
        updateController.setGuestOldPhoneNumber(guestPhoneNumber.getText());
        updateController.setGuestOldPassword(guestPassword.getText());
        updateController.updateUIWithGuestData();
      }

      guestContentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
