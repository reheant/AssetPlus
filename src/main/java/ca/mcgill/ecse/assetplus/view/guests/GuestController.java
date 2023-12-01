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

  /**
   * Loads the 'add-guest.fxml' page when Add Guest is clicked.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onAddGuestClicked() {
    loadPage("add-guest.fxml");
  }

  /**
   * Loads the 'update-guest.fxml' page for the selected guest. Does nothing if no guest is selected
   * or the selection is invalid.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onUpdateGuestClicked() {
    String selectedGuestEmail = guestList.getSelectionModel().getSelectedItem();
    if (selectedGuestEmail != null && !selectedGuestEmail.equals("No search results")) {
      loadPage("update-guest.fxml");
    }
  }

  /**
   * Deletes the guest with the displayed email and loads the 'guests.fxml' page.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onDeleteGuestClicked() {
    String email = guestEmailLabel.getText().strip();
    AssetPlusFeatureSet6Controller.deleteEmployeeOrGuest(email);
    loadPage("guests.fxml");
  }

  /**
   * Searches and displays guests matching the entered email. Shows 'No search results' if no match
   * is found.
   *
   * @author Nicolas Bolouri
   */
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

  /**
   * Clears the search bar, resets the guest list and cell factory.
   *
   * @author Nicolas Bolouri
   */
  @FXML
  private void onClearButtonClicked() {
    guestSearchBar.setText("");
    resetGuestList();
    resetCellFactory();
  }

  /**
   * Initializes the guest list and sets up the UI. Loads guest emails and sets up the list cell
   * factory.
   *
   * @author Nicolas Bolouri
   */
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

  /**
   * Sets the guest information display for the given email. Shows 'Information not available' if
   * the email is not found.
   *
   * @author Nicolas Bolouri
   * @param email The email of the guest to display information for.
   */
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

  /**
   * Filters the guest list based on the searched email.
   *
   * @author Nicolas Bolouri
   * @param searchedEmail The email to filter the guest list by.
   * @return A list of emails matching the search criteria.
   */
  private List<String> filterGuestList(String searchedEmail) {
    String[] guestEmails = AssetPlusFeatureSet1Controller.getGuestEmails();
    return Arrays.stream(guestEmails).filter(email -> email.equalsIgnoreCase(searchedEmail))
        .collect(Collectors.toList());
  }

  /**
   * Displays 'No search results' in the guest list. Adjusts the cell factory for this specific
   * item.
   *
   * @author Nicolas Bolouri
   */
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

  /**
   * Resets the cell factory of the guest list to its default state.
   *
   * @author Nicolas Bolouri
   */
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

  /**
   * Resets the guest list to show all guests.
   *
   * @author Nicolas Bolouri
   */
  private void resetGuestList() {
    guestList.getItems().clear();
    String[] guestEmails = AssetPlusFeatureSet1Controller.getGuestEmails();
    guestList.getItems().addAll(guestEmails);
  }


  /**
   * Loads a specified FXML page into the guest content area. Sets up controller data for
   * 'update-guest.fxml'. Catches and prints exceptions if the file cannot be loaded.
   *
   * @author Nicolas Bolouri
   * @param String The FXML file to load, relative to '/ca/mcgill/ecse/assetplus/view/guests/'.
   */
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
