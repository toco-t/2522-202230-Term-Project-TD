package ca.bcit.comp2522.termproject.td.camera;

import ca.bcit.comp2522.termproject.td.Vector2D;

/**
 * A Camera that changes the position of tiles and units on-screen.
 * Note: might be discontinued...
 *
 * @author Nathan
 * @version 0.2
 */
public class Camera {
    private Vector2D screenPosition;

    /**
     * Creates an object of type Camera.
     *
     * @param startingPosition the starting position of the camera, in pixels
     */
    public Camera(final Vector2D startingPosition) {
        screenPosition = startingPosition;
    }

    /**
     * Returns the position of this Camera.
     *
     * @return the position as a Vector2D
     */
    public Vector2D getScreenPosition() {
        return screenPosition;
    }

    /**
     * Sets the position of this Camera.
     *
     * @param screenPosition the position as a Vector2D
     */
    public void setScreenPosition(final Vector2D screenPosition) {
        this.screenPosition = screenPosition;
    }

    /**
     * Moves the camera position by a certain number of units.
     *
     * @param delta the amount to move the camera by, as a Vector2D
     */
    public void moveCameraPosition(final Vector2D delta) {
        screenPosition.add(delta);
    }
}
