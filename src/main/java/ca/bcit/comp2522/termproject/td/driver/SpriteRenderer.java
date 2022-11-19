package ca.bcit.comp2522.termproject.td.driver;

import ca.bcit.comp2522.termproject.td.Drawable;
import ca.bcit.comp2522.termproject.td.Vector2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import static ca.bcit.comp2522.termproject.td.Vector2D.tileCoordinateToScreenSpace;

/**
 * Renders Drawable objects and groups them.
 *
 * @author Nathan
 * @version 0.1
 */
public final class SpriteRenderer {
    /**
     * Constructs an object of TileRenderer.
     */
    private SpriteRenderer() {

    }

    /**
     * Converts an ArrayList of Tiles to a Group, converting coordinates as necessary.
     *
     * @param drawables an ArrayList of Tiles to convert
     * @param viewSizeX the width of each sprite in pixels
     * @param viewSizeY the height of each sprite in pixels
     * @param scale the size scale of the sprite
     * @param centerOnTile whether the bottom of the sprite should be centered on each tile
     * @return the Group of converted ImageView nodes
     */
    public static Group groupDrawables(final ArrayList<Drawable> drawables, final int viewSizeX, final int viewSizeY,
                                       final double scale, final boolean centerOnTile) {
        ArrayList<ImageView> drawableViews = new ArrayList<>();

        for (Drawable drawable : drawables) {
            drawableViews.add(getImageViewFromTile(drawable, viewSizeX, viewSizeY, scale, centerOnTile));
        }

        ImageView[] imageViews = new ImageView[drawables.size()];
        return new Group(drawableViews.toArray(imageViews));
    }

    /* Converts a Drawable object into an ImageView. */
    private static ImageView getImageViewFromTile(final Drawable drawable, final int viewSizeX, final int viewSizeY,
                                                  final double scale, final boolean centerOnTile) {
        final double tileWidthInPixels = 128;
        final double tileHeightInPixels = 64;

        double scaledViewSizeX = viewSizeX * scale;
        double scaledViewSizeY = viewSizeY * scale;

        Image sprite = drawable.getSprite();
        ImageView imageView = new ImageView(sprite);
        imageView.setViewport(new Rectangle2D(0, 0, viewSizeX, viewSizeY));

        Vector2D screenSpaceCoordinates = tileCoordinateToScreenSpace(tileWidthInPixels, tileHeightInPixels,
                drawable.getLocation());

        if (centerOnTile) {
            imageView.setX(screenSpaceCoordinates.getXCoordinate() + tileWidthInPixels / 2 - scaledViewSizeX / 2);
            imageView.setY(screenSpaceCoordinates.getYCoordinate() + tileHeightInPixels / 2 - scaledViewSizeY);
        } else {
            imageView.setX(screenSpaceCoordinates.getXCoordinate());
            imageView.setY(screenSpaceCoordinates.getYCoordinate());
        }

        imageView.setFitWidth(scaledViewSizeX);
        imageView.setFitHeight(scaledViewSizeY);

        return imageView;
    }
}
