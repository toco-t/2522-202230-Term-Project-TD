package ca.bcit.comp2522.termproject.td.driver;

import ca.bcit.comp2522.termproject.td.Terrain;
import ca.bcit.comp2522.termproject.td.Vector2D;
import ca.bcit.comp2522.termproject.td.map.Tile;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        Text hello = new Text(50, 50, "Blossom Storm");
        Text question = new Text(120, 80, "Ah, what a miserable life...");

        Image tileImage = new Image("tile_demo.png");

        /* These Vector2D arguments are in Tile coordinates. */
        Tile tile1 = new Tile(Terrain.ROAD, tileImage, new Vector2D(0, 0));
        Tile tile2 = new Tile(Terrain.ROAD, tileImage, new Vector2D(1, 0));
        Tile tile3 = new Tile(Terrain.ROAD, tileImage, new Vector2D(2, 0));
        Tile tile4 = new Tile(Terrain.ROAD, tileImage, new Vector2D(1, -1));
        Tile tile5 = new Tile(Terrain.ROAD, tileImage, new Vector2D(2, -1));

        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        tiles.add(tile4);
        tiles.add(tile5);

        TileRenderer tileRenderer = new TileRenderer();

        Group root = tileRenderer.getTileGroup(tiles);
        Scene scene = new Scene(root, 1280, 720, Color.BLACK);

        stage.setTitle("Blossom Storm");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
