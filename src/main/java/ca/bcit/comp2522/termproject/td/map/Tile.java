package ca.bcit.comp2522.termproject.td.map;

import ca.bcit.comp2522.termproject.td.Drawable;
import ca.bcit.comp2522.termproject.td.GameManager;
import ca.bcit.comp2522.termproject.td.Terrain;
import ca.bcit.comp2522.termproject.td.Vector2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

import static ca.bcit.comp2522.termproject.td.Vector2D.tileCoordinateToScreenSpace;

/**
 * Tile in a Map.
 *
 * @author Toco Tachibana
 * @version 0.1
 */
public class Tile implements Drawable {
    private GameManager gameManager;
    private Image sprite;

    private final Vector2D location;
    private final Terrain terrain;

    /**
     * Constructs an object of type Tile.
     *
     * @param gameManager the GameManager, used for handling events
     * @param terrain the terrain type of this Tile
     * @param sprite the sprite this Tile uses
     * @param location the tile-coordinates of the Tile
     */
    public Tile(final GameManager gameManager, final Terrain terrain, final Image sprite, final Vector2D location) {
        this.gameManager = gameManager;
        this.terrain = terrain;
        this.sprite = sprite;
        this.location = location;
    }

    /**
     * Returns the sprite of this Tile.
     *
     * @return the sprite as an Image
     */
    public Image getSprite() {
        return sprite;
    }

    /**
     * Sets the sprite of this Tile.
     *
     * @param sprite the sprite as an Image
     */
    public void setSprite(final Image sprite) {
        this.sprite = sprite;
    }

    /**
     * Returns the coordinates of this Tile.
     *
     * @return the coordinates as a Vector2D
     */
    public Vector2D getLocation() {
        return location;
    }

    /**
     * Returns an ImageView of this Tile, using its coordinates.
     *
     * @return the ImageView representing this Tile
     */
    @Override
    public ImageView getImageView() {
        final double viewSizeX = 128;
        final double viewSizeY = 128;

        final double tileWidthInPixels = 128;
        final double tileHeightInPixels = 64;

        final double scale = 1;
        double scaledViewSizeX = viewSizeX * scale;
        double scaledViewSizeY = viewSizeY * scale;

        ImageView imageView = new ImageView(sprite);
        imageView.setViewport(new Rectangle2D(0, 0, viewSizeX, viewSizeY));

        Vector2D screenSpaceCoordinates = tileCoordinateToScreenSpace(tileWidthInPixels, tileHeightInPixels,
                location);

        imageView.setX(screenSpaceCoordinates.getXCoordinate());
        imageView.setY(screenSpaceCoordinates.getYCoordinate());

        imageView.setFitWidth(scaledViewSizeX);
        imageView.setFitHeight(scaledViewSizeY);

        imageView.setOnMouseClicked((MouseEvent event) -> {
            gameManager.select(this);
        });

        return imageView;
    }

    /**
     * Returns the terrain type of this Tile.
     *
     * @return the terrain type as a Terrain
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * Returns the String representation of this Tile.
     *
     * @return toString as a String
     */
    @Override
    public String toString() {
        return String.format("Tile{ coordinates = %s, terrain = %s}", location.toString(), terrain.toString());
    }

    /**
     * Returns true if the specified object is equal to this Tile, else false.
     *
     * @param object the specified item to compare, an Object
     * @return true if this Tile and the specified object are euqal, else false
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Tile tile = (Tile) object;
        return Objects.equals(location, tile.location) && terrain == tile.terrain;
    }

    /**
     * Returns the hash code of this Tile.
     *
     * @return hashCode as an int
     */
    @Override
    public int hashCode() {
        return Objects.hash(location, terrain);
    }
}
