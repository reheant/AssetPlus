package ca.mcgill.ecse.assetplus.view.assetTypes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.view.specificAsset.SpecificAssetController;

public class AssetTypeController {
  private String assetTypeString;

  @FXML
  private AnchorPane assetTypeContentArea;

  @FXML
  private Label assetTypeName;

  @FXML
  private Label assetTypeLifespan;

  @FXML
  private TextField assetTypeSearchBar;

  @FXML
  private ListView<String> assetTypeList;

  @FXML
  private Button viewAllSpecificAssets;

  /**
   * Initializes asset type default page
   *
   * @author Tiffany Miller
   */
  @FXML
  public void initialize() {
    assetTypeSearchBar.setFocusTraversable(false);

    String[] assetTypeNames = AssetPlusFeatureSet2Controller.getAssetTypes();
    assetTypeList.setFixedCellSize(50.0);
    assetTypeList.setCellFactory(lv -> new ListCell<String>() {
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
    assetTypeList.setPrefHeight(10 * assetTypeList.getFixedCellSize());
    assetTypeList.getItems().addAll(assetTypeNames);

    assetTypeList.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {

          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue,
              String newValue) {
            setInfo(newValue);
            assetTypeString = newValue;
          }
        });
  }

  /**
   * Loads add asset type page
   *
   * @author Tiffany Miller
   */
  @FXML
  private void onAddAssetTypeClicked() {
    loadPage("add-asset-type.fxml");
  }

  /**
   * Loads update asset type page on user's selected asset type
   *
   * @author Tiffany Miller
   */
  @FXML
  private void viewAllSpecificAssetsClicked() {
    String selectedAssetType = assetTypeList.getSelectionModel().getSelectedItem();
    if (selectedAssetType != null && !selectedAssetType.equals("No search results")) {
      loadPage("../specificAsset/specificAsset.fxml");
    }

  }

  /**
   * Retrieves the currently selected asset type from a list in the UI. If a valid asset type is
   * selected, it loads the corresponding page for updating the asset type.
   * 
   * @author Tiffany Miller
   */
  @FXML
  private void onUpdateAssetTypeClicked() {
    String selectedAssetType = assetTypeList.getSelectionModel().getSelectedItem();
    if (selectedAssetType != null && !selectedAssetType.equals("No search results")) {
      loadPage("update-asset-type.fxml");
    }
  }

  /**
   * Deletes user's selected asset type once button is clicked
   *
   * @author Tiffany Miller
   */
  @FXML
  private void onDeleteAssetTypeClicked() {
    String name = assetTypeName.getText().strip();
    AssetPlusFeatureSet2Controller.deleteAssetType(name);
    loadPage("assets.fxml");
  }

  /**
   * Displays search results once search button is clicked
   *
   * @author Tiffany Miller
   */
  @FXML
  private void onSearchButtonClicked() {
    String searchedName = assetTypeSearchBar.getText();
    List<String> filteredNames = filterAssetTypeList(searchedName);

    assetTypeList.getItems().clear();
    if (filteredNames.isEmpty()) {
      displayNoSearchResults();
    } else {
      assetTypeList.getItems().addAll(filteredNames);
      resetCellFactory();
    }
  }

  /**
   * Clears search results once clear button in search bar is clicked
   *
   * @author Tiffany Miller
   */
  @FXML
  private void onClearButtonClicked() {
    assetTypeSearchBar.setText("");
    resetAssetTypeList();
    resetCellFactory();
  }

  /**
   * Sets and displays previous name and lifespan information when updating that specific asset type
   *
   * @param name The name of the asset type one wishes to set and display information
   * @author Tiffany Miller
   */
  private void setInfo(String name) {
    int lifespan = AssetPlusFeatureSet2Controller.getLifespanByName(name);
    if (lifespan != 0) {
      assetTypeName.setText(name);
      assetTypeLifespan.setText(Integer.toString(lifespan));
    } else {
      assetTypeName.setText("Information not available");
      assetTypeLifespan.setText("Information not available");
    }
  }

  /**
   * Filters asset type list of names by user search results
   *
   * @param searchedName The name of the asset type the user searched
   * @author Tiffany Miller
   */
  private List<String> filterAssetTypeList(String searchedName) {
    String[] assetTypeNames = AssetPlusFeatureSet2Controller.getAssetTypes();
    return Arrays.stream(assetTypeNames).filter(name -> name.equalsIgnoreCase(searchedName))
        .collect(Collectors.toList());
  }

  /**
   * Displays no search results
   *
   * @author Tiffany Miller
   */
  private void displayNoSearchResults() {
    assetTypeList.getItems().add("No search results");
    assetTypeList.setCellFactory(lv -> new ListCell<String>() {
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
   * Resets cell factory in user interface javafx
   *
   * @author Tiffany Miller
   */
  private void resetCellFactory() {
    assetTypeList.setCellFactory(lv -> new ListCell<String>() {
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
   * Resets asset type list of names
   *
   * @author Tiffany Miller
   */
  private void resetAssetTypeList() {
    assetTypeList.getItems().clear();
    String[] assetTypeNames = AssetPlusFeatureSet2Controller.getAssetTypes();
    assetTypeList.getItems().addAll(assetTypeNames);
  }

  /**
   * Loads page of the given fxml file
   *
   * @param fxmlFile The name of fxml file one wishes to load
   * @author Tiffany Miller
   */
  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/ca/mcgill/ecse/assetplus/view/assetTypes/" + fxmlFile));
      Node page = loader.load();

      if (fxmlFile.equals("update-asset-type.fxml")) {
        AssetTypeUpdateController updateController = loader.getController();
        updateController.setAssetTypeOldName(assetTypeName.getText());
        updateController.setAssetTypeOldLifespan(assetTypeLifespan.getText());
        updateController.updateUIWithAssetTypeData();
      }

      if (fxmlFile.equals("../specificAsset/specificAsset.fxml")) {
        SpecificAssetController updateController = loader.getController();
        updateController.initialize(assetTypeString);
      }

      assetTypeContentArea.getChildren().setAll(page);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
