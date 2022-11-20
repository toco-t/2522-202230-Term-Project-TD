package ca.bcit.comp2522.termproject.td.driver;

import ca.bcit.comp2522.termproject.td.Drawable;
import ca.bcit.comp2522.termproject.td.GameManager;
import ca.bcit.comp2522.termproject.td.Vector2D;
import ca.bcit.comp2522.termproject.td.Weather;
import ca.bcit.comp2522.termproject.td.map.GameMap;
import ca.bcit.comp2522.termproject.td.unit.Unit;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Driver for the game.
 *
 * @author Nathan
 * @version 0.1
 */
public class BlossomStorm extends Application {
    @Override
    public void start(final Stage stage) throws IOException {
        // Note: this is an early demo for rendering and does not use GameManager to create the map as intended
        // Create a GameManager.
        GameManager gameManager = new GameManager();

        // Create a new Unit.
        Unit ayumi = new Unit("Ayumi", new Vector2D(7, 0));

        // Create a test Map, using the preset mission -1.
        GameMap testMap = new GameMap(gameManager, Weather.OVERCAST, false, -1);

        // Put all the Tiles in one ArrayList.
        ArrayList<Drawable> tiles = testMap.getTilesForRendering();

        // Put all the Units in one ArrayList.
        ArrayList<Drawable> units = new ArrayList<>();
        units.add(ayumi);

        // Convert the Tiles into ImageViews, then put them into a Group.
        Group tileGroup = SpriteRenderer.groupDrawables(tiles);
        Group unitGroup = SpriteRenderer.groupDrawables(units);
        Group root = new Group(tileGroup, unitGroup);
        Scene scene = new Scene(root, 1024, 576, Color.BLACK);

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
