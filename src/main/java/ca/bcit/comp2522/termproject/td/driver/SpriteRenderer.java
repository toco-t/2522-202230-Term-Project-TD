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
     * @return the Group of converted ImageView nodes
     */
    public static Group groupDrawables(final ArrayList<Drawable> drawables) {
        ArrayList<ImageView> drawableViews = new ArrayList<>();

        for (Drawable drawable : drawables) {
            drawableViews.add(drawable.getImageView());
        }

        ImageView[] imageViews = new ImageView[drawables.size()];
        return new Group(drawableViews.toArray(imageViews));
    }
}
