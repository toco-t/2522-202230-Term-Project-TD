package ca.bcit.comp2522.termproject.td.driver;

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

        Image tile = new Image("tile_demo.png");
        ImageView imageView1 = new ImageView(tile);
        ImageView imageView2 = new ImageView(tile);
        ImageView imageView3 = new ImageView(tile);

        imageView1.setViewport(new Rectangle2D(0, 0, 128, 128));
        imageView2.setViewport(new Rectangle2D(0, 0, 128, 128));
        imageView3.setViewport(new Rectangle2D(0, 0, 128, 128));

        imageView2.setX(128);

        imageView3.setX(64);
        imageView3.setY(32);

        Group root = new Group(imageView1, imageView2, imageView3);
        Scene scene = new Scene(root, 1280, 720, Color.BLACK);

        stage.setTitle("Blossom Storm");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
