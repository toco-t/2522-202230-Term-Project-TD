package ca.bcit.comp2522.termproject.td.driver;

import ca.bcit.comp2522.termproject.td.Drawable;
import ca.bcit.comp2522.termproject.td.Terrain;
import ca.bcit.comp2522.termproject.td.Vector2D;
import ca.bcit.comp2522.termproject.td.map.Tile;
import ca.bcit.comp2522.termproject.td.unit.Unit;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
        Text title = new Text(50, 50, "Blossom Storm");
        Text reminiscence = new Text(120, 80, "Ah, it was a miserable life...");
        Text question = new Text(120, 110, "What could I expect but a miserable death?");

        Image tileImage = new Image("tile_demo.png");

        Unit ayumi = new Unit("Ayumi");

        // These Vector2D arguments are in Tile coordinates.
        Tile tile1 = new Tile(Terrain.ROAD, tileImage, new Vector2D(0, 0));
        Tile tile2 = new Tile(Terrain.ROAD, tileImage, new Vector2D(1, 0));
        Tile tile3 = new Tile(Terrain.ROAD, tileImage, new Vector2D(2, 0));
        Tile tile4 = new Tile(Terrain.ROAD, tileImage, new Vector2D(1, -1));
        Tile tile5 = new Tile(Terrain.ROAD, tileImage, new Vector2D(2, -1));

        // Put all the Tiles in one ArrayList.
        ArrayList<Drawable> tiles = new ArrayList<>();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        tiles.add(tile4);
        tiles.add(tile5);

        // Put all the Units in one ArrayList.
        ArrayList<Drawable> units = new ArrayList<>();
        units.add(ayumi);

        // Convert the Tiles into ImageViews, then put them into a Group.
        Group tileGroup = SpriteRenderer.groupDrawables(tiles, 128, 128, 1, false);
        Group unitGroup = SpriteRenderer.groupDrawables(units, 20, 35, 2, true);
        Group root = new Group(tileGroup, unitGroup);
        Scene scene = new Scene(root, 1024, 576, Color.BLACK);

        stage.setTitle("Blossom Storm");
        stage.getIcons().add(new Image("icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
