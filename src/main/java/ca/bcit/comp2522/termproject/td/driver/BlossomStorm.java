package ca.bcit.comp2522.termproject.td.driver;

import ca.bcit.comp2522.termproject.td.GameManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Driver for the game.
 *
 * @author Nathan
 * @version 0.1
 */
public class BlossomStorm extends Application {
    @Override
    public void start(final Stage stage) {
        // Create a GameManager.
        GameManager gameManager = new GameManager();

        // Convert the Tiles into ImageViews, then put them into a Group.
        Group root = gameManager.groupAllObjectsForRendering();
        Scene scene = new Scene(root, 1024, 576, Color.BLACK);

        // Add a listener to the Scene for keyboard presses.
        scene.setOnKeyPressed(gameManager::panCameraTo);

        // Let the game begin.
        stage.setTitle("Blossom Storm");
        stage.getIcons().add(new Image("icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
