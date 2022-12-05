package ca.bcit.comp2522.termproject.td.map;

import ca.bcit.comp2522.termproject.td.interfaces.Drawable;
import ca.bcit.comp2522.termproject.td.GameManager;
import ca.bcit.comp2522.termproject.td.enums.Terrain;
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
 * @author Toco Tachibana & Nathan Ng
 * @version 0.3
 */
public class Tile implements Drawable {
    private static final double VIEW_SIZE_X = 128;
    private static final double VIEW_SIZE_Y = 128;

    private static final double TILE_WIDTH_IN_PIXELS = 128;
    private static final double TILE_HEIGHT_IN_PIXELS = 64;

    private static final double SPRITE_SCALE = 1;
    private ImageView imageView;
    private final GameManager gameManager;
    private Image sprite;

    private final Vector2D location;
    private int height;
    private final Vector2D viewOffset;
    private final Terrain terrain;

    /**
     * Constructs an object of type Tile.
     *
     * @param gameManager the GameManager, used for handling clicking events
     * @param terrain the terrain type of this Tile
     * @param spriteSheet the sprite this Tile uses
     * @param location the tile-coordinates of this Tile
     * @param spriteLocation the location of the sprite within the sprite sheet
     */
    public Tile(final GameManager gameManager, final Terrain terrain, final Image spriteSheet,
                final Vector2D spriteLocation, final Vector2D location) {
        this.gameManager = gameManager;
        this.terrain = terrain;
        this.sprite = spriteSheet;
        this.location = location;
        this.viewOffset = new Vector2D(0, 0);

        generateImageView(spriteLocation);
    }

    /**
     * Returns the image view of this Tile.
     *
     * @return the image view as an ImageView
     */
    @Override
    public ImageView getImageView() {
        return imageView;
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
     * Returns the height of this Tile.
     *
     * @return the height as an int
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of this Tile.
     *
     * @param height the height as an int
     */
    public void setHeight(final int height) {
        this.height = height;
        updateImageViewPosition();
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
     * Moves the ImageView of this Tile without changing its actual coordinates.
     *
     * @param offsetDelta the amount to move the Tile by, as a Vector2D
     */
    @Override
    public void moveImageView(final Vector2D offsetDelta) {
        viewOffset.add(offsetDelta);
        updateImageViewPosition();
    }

    /* Generates an ImageView of this Tile, using its coordinates. */
    private void generateImageView(final Vector2D spritePosition) {
        double scaledViewSizeX = VIEW_SIZE_X * SPRITE_SCALE;
        double scaledViewSizeY = VIEW_SIZE_Y * SPRITE_SCALE;

        double xOffsetPixels = VIEW_SIZE_X * spritePosition.getXCoordinate();
        double yOffsetPixels = VIEW_SIZE_Y * spritePosition.getYCoordinate();

        imageView = new ImageView(sprite);
        imageView.setViewport(new Rectangle2D(xOffsetPixels, yOffsetPixels, scaledViewSizeX,
                scaledViewSizeY));

        updateImageViewPosition();

        imageView.setOnMouseClicked((MouseEvent event) -> gameManager.select(this));
        imageView.setOnMouseEntered((MouseEvent event) -> gameManager.hoverHint(this));

        if (terrain == Terrain.OBSTACLE) {
            imageView.setMouseTransparent(true);
        }
    }

    /* Updates the ImageView's position based on the offset. */
    private void updateImageViewPosition() {
        Vector2D screenSpaceCoordinates = getScreenSpaceCoordinates(viewOffset);
        imageView.setX(screenSpaceCoordinates.getXCoordinate());
        imageView.setY(screenSpaceCoordinates.getYCoordinate() - height * TILE_HEIGHT_IN_PIXELS);
    }

    /* Gets the coordinates of this Tile in screen space (pixels). */
    private Vector2D getScreenSpaceCoordinates(final Vector2D offset) {
        Vector2D screenSpaceCoordinates = tileCoordinateToScreenSpace(TILE_WIDTH_IN_PIXELS, TILE_HEIGHT_IN_PIXELS,
                location);

        return new Vector2D(screenSpaceCoordinates.getXCoordinate() + offset.getXCoordinate(),
                screenSpaceCoordinates.getYCoordinate() + offset.getYCoordinate());
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
