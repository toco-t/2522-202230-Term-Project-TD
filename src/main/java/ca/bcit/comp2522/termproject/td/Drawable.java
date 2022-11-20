package ca.bcit.comp2522.termproject.td;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents an object that has a sprite.
 *
 * @author Nathan
 * @version 0.1
 */
public interface Drawable {
    /**
     * Returns the sprite of this Drawable object.
     *
     * @return the sprite as an Image
     */
    Image getSprite();

    /**
     * Returns the coordinates of this Drawable object.
     *
     * @return the coordinates as a Vector2D
     */
    Vector2D getLocation();

    /**
     * Returns an ImageView of this Drawable object, using its coordinates.
     *
     * @return the ImageView representing this object
     */
    ImageView getImageView();
}
