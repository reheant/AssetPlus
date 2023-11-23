package ca.mcgill.ecse.assetplus.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SpecifcAssetController {
  @FXML
  private Button testButton;
  
  @FXML
  public void testButtonClicked(ActionEvent event){
    System.out.println("button pressed");

  }
}
