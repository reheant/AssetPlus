package ca.mcgill.ecse.assetplus.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainView extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a Button that will print to the console when clicked
        Button btn = new Button();
        btn.setText("Click me!");
        btn.setOnAction(event -> System.out.println("Button Clicked!"));

        // Create a layout and add the button to it
        StackPane root = new StackPane();
        root.getChildren().add(btn);

        // Create a scene with the layout, set the title and show the stage
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Test JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
