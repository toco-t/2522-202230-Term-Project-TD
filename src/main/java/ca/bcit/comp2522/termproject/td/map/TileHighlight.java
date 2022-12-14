package ca.bcit.comp2522.termproject.td.map;

import ca.bcit.comp2522.termproject.td.interfaces.Drawable;
import ca.bcit.comp2522.termproject.td.Vector2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

import static ca.bcit.comp2522.termproject.td.Vector2D.tileCoordinateToScreenSpace;

/**
 * Tile Highlight that shows potential movement options.
 *
 * @author Nathan
 * @version 0.3
 */
public class TileHighlight implements Drawable {
    private static final double VIEW_SIZE_X = 128;
    private static final double VIEW_SIZE_Y = 128;

    private static final double TILE_WIDTH_IN_PIXELS = 128;
    private static final double TILE_HEIGHT_IN_PIXELS = 64;

    private static final double SPRITE_SCALE = 1;
    private ImageView imageView;
    private final Image sprite;

    private final Vector2D location;
    private int height;
    private final Vector2D viewOffset;

    /**
     * Constructs an object of type Tile.
     *
     * @param sprite the sprite this Tile uses
     * @param location the tile-coordinates of this Tile
     * @param viewOffset the offset to apply to the ImageView to simulate a camera
     */
    public TileHighlight(final Image sprite, final Vector2D location, final Vector2D viewOffset) {
        this.sprite = sprite;
        this.location = location;
        this.viewOffset = viewOffset;

        generateImageView();
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
     * Returns the coordinates of this Tile.
     *
     * @return the coordinates as a Vector2D
     */
    public Vector2D getLocation() {
        return location;
    }

    /**
     * Moves the ImageView of this Tile without changing its actual coordinates.
     *
     * @param offsetDelta the amount to move the Tile by, as a Vector2D
     */
    @Override
    public void moveImageView(final Vector2D offsetDelta) {
        updateImageViewPosition();
    }

    /* Generates an ImageView of this Tile, using its coordinates. */
    private void generateImageView() {
        double scaledViewSizeX = VIEW_SIZE_X * SPRITE_SCALE;
        double scaledViewSizeY = VIEW_SIZE_Y * SPRITE_SCALE;

        imageView = new ImageView(sprite);
        imageView.setViewport(new Rectangle2D(0, 0, scaledViewSizeX,
                scaledViewSizeY));
        imageView.setMouseTransparent(true);

        updateImageViewPosition();
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
     * Compares this TileHighlight with another object.
     *
     * @param o the other Object to compare
     * @return true if they are the equal, otherwise false
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TileHighlight highlight = (TileHighlight) o;

        if (height != highlight.height) {
            return false;
        }
        if (!Objects.equals(imageView, highlight.imageView)) {
            return false;
        }
        if (!Objects.equals(sprite, highlight.sprite)) {
            return false;
        }
        if (!Objects.equals(location, highlight.location)) {
            return false;
        }
        return Objects.equals(viewOffset, highlight.viewOffset);
    }

    /**
     * Returns this instance of TileHighlight's hashcode.
     *
     * @return the hashcode as an int
     */
    @Override
    public int hashCode() {
        final int primeMultiplier = 31;
        int result = imageView != null ? imageView.hashCode() : 0;
        result = primeMultiplier * result + (sprite != null ? sprite.hashCode() : 0);
        result = primeMultiplier * result + (location != null ? location.hashCode() : 0);
        result = primeMultiplier * result + height;
        result = primeMultiplier * result + (viewOffset != null ? viewOffset.hashCode() : 0);
        return result;
    }
}
