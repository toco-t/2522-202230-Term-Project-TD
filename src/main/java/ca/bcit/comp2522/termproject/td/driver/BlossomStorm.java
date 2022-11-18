package ca.bcit.comp2522.termproject.td.driver;

import ca.bcit.comp2522.termproject.td.Terrain;
import ca.bcit.comp2522.termproject.td.Vector2D;
import ca.bcit.comp2522.termproject.td.map.Tile;
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

        // These Vector2D arguments are in Tile coordinates.
        Tile tile1 = new Tile(Terrain.ROAD, tileImage, new Vector2D(0, 0));
        Tile tile2 = new Tile(Terrain.ROAD, tileImage, new Vector2D(1, 0));
        Tile tile3 = new Tile(Terrain.ROAD, tileImage, new Vector2D(2, 0));
        Tile tile4 = new Tile(Terrain.ROAD, tileImage, new Vector2D(1, -1));
        Tile tile5 = new Tile(Terrain.ROAD, tileImage, new Vector2D(2, -1));

        // Put all the Tiles in one ArrayList.
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        tiles.add(tile4);
        tiles.add(tile5);

        // Create a TileRenderer which will handle coordinate conversion and tile grouping.
        TileRenderer tileRenderer = new TileRenderer();

        // Convert the Tiles into ImageViews, then put them into a Group.
        Group root = tileRenderer.groupTiles(tiles);
        Scene scene = new Scene(root, 1280, 720, Color.BLACK);

        stage.setTitle("Blossom Storm");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
