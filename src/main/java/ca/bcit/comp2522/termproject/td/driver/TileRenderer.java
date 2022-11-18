package ca.bcit.comp2522.termproject.td.driver;

import ca.bcit.comp2522.termproject.td.Vector2D;
import ca.bcit.comp2522.termproject.td.map.Tile;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Renders Tile objects.
 *
 * @author Nathan
 * @version 0.1
 */
public class TileRenderer {
    /**
     * Constructs an object of TileRenderer.
     */
    public TileRenderer() {

    }

    /**
     * Converts an ArrayList of Tiles to a Group, converting coordinates as necessary.
     *
     * @param tiles an ArrayList of Tiles to convert
     * @return the Group of converted ImageView nodes
     */
    public Group groupTiles(final ArrayList<Tile> tiles) {
        ArrayList<ImageView> tileViews = new ArrayList<>();

        for (Tile tile : tiles) {
            tileViews.add(getImageViewFromTile(tile));
        }

        ImageView[] imageViews = new ImageView[tiles.size()];
        return new Group(tileViews.toArray(imageViews));
    }

    /* Converts a Tile into an ImageView. */
    private ImageView getImageViewFromTile(final Tile tile) {
        final int cubeWidth = 128;
        final int cubeHeight = 128;

        Image sprite = tile.getSprite();
        ImageView imageView = new ImageView(sprite);
        imageView.setViewport(new Rectangle2D(0, 0, cubeWidth, cubeHeight));

        Vector2D screenSpaceCoordinates = convertTileCoordinateToScreenSpace(tile.getCoordinates());
        imageView.setX(screenSpaceCoordinates.getXCoordinate());
        imageView.setY(screenSpaceCoordinates.getYCoordinate());

        return imageView;
    }

    /* Converts a tile coordinate into pixel coordinates. */
    private Vector2D convertTileCoordinateToScreenSpace(final Vector2D tileCoordinate) {
        final double tileWidthInPixels = 128;
        final double tileHeightInPixels = 64;

        final double tileXCoordinateToScreenXCoordinate = 0.5 * tileWidthInPixels;
        final double tileXCoordinateToScreenYCoordinate = 0.5 * tileHeightInPixels;

        final double tileYCoordinateToScreenXCoordinate = 0.5 * tileWidthInPixels;
        final double tileYCoordinateToScreenYCoordinate = -0.5 * tileHeightInPixels;

        double screenSpaceX = tileCoordinate.getXCoordinate() * tileXCoordinateToScreenXCoordinate
                + tileCoordinate.getYCoordinate() * tileYCoordinateToScreenXCoordinate;
        double screenSpaceY = tileCoordinate.getXCoordinate() * tileXCoordinateToScreenYCoordinate
                + tileCoordinate.getYCoordinate() * tileYCoordinateToScreenYCoordinate;

        return new Vector2D(screenSpaceX, screenSpaceY);
    }
}
