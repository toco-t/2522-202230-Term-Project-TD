package ca.bcit.comp2522.termproject.td.driver;

import ca.bcit.comp2522.termproject.td.GameManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Driver for the game.
 *
 * @author Nathan Ng & Toco Tachibana
 * @version 0.3
 */
public class BlossomStorm extends Application {
    private Stage applicationStage;

    /**
     * Starts the game.
     *
     * @param stage the stage to place nodes in
     */
    @Override
    public void start(final Stage stage) {
        Image titleBackground = new Image("title.png", 1024, 756, true, false);
        ImageView titleBGView = new ImageView(titleBackground);

        Image logo = new Image("logo_full.png", 400, 150, true, true);
        ImageView logoView = new ImageView(logo);
        logoView.setX(300);
        logoView.setY(25);

        Text pressStartText = new Text(465, 516, "Press SPACE");
        pressStartText.setFill(Color.WHITE);
        pressStartText.setFont(Font.font(18));

        Text developers = new Text(10, 550, "Toco Tachibana\nNathan Ng");
        developers.setFill(Color.GRAY);

        Text version = new Text(1000, 566, "0.3");
        version.setFill(Color.GRAY);

        Group root = new Group(titleBGView, logoView, pressStartText, developers, version);
        Scene scene = new Scene(root, 1024, 576, Color.BLACK);
        scene.setOnKeyPressed(this::startScreenInput);

        applicationStage = stage;
        stage.setTitle("Blossom Storm");
        stage.getIcons().add(new Image("icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    private void startScreenInput(final KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            loadCurryHouseMission();
        }
    }

    private void loadCurryHouseMission() {
        // Create a GameManager.
        GameManager gameManager = new GameManager();

        // Convert the Tiles into ImageViews, then put them into a Group.
        Group root = gameManager.groupAllObjectsForRendering();
        Scene scene = new Scene(root, 1024, 576, Color.BLACK);

        // Add a listener to the Scene for keyboard presses.
        scene.setOnKeyPressed(gameManager::panCameraTo);

        // Let the game begin.
        applicationStage.setScene(scene);
        applicationStage.show();
    }

    /**
     * Drives the program.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        launch();
    }
}
