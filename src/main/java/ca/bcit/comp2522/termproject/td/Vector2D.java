package ca.bcit.comp2522.termproject.td;

/**
 * Two-dimensional vector.
 *
 * @author Toco Tachibana
 * @version 0.1
 */
public class Vector2D {
    private double xCoordinate;
    private double yCoordinate;

    /**
     * Constructs an object of type Vector2D.
     *
     * @param xCoordinate a double
     * @param yCoordinate a double
     */
    public Vector2D(final double xCoordinate, final double yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Returns the x coordinate of this Vector.
     *
     * @return xCoordinate as a double
     */
    public double getXCoordinate() {
        return this.xCoordinate;
    }

    /**
     * Returns the y coordinate of this Vector.
     *
     * @return yCoordinate as a double
     */
    public double getYCoordinate() {
        return this.yCoordinate;
    }

    /**
     * Increases the x, y or both coordinates of this Vector.
     *
     * @param xIncrease the amount to increase, a double
     * @param yIncrease the amount to increase, a double
     */
    public void add(final double xIncrease, final double yIncrease) {
        this.xCoordinate += xIncrease;
        this.yCoordinate += yIncrease;
    }

    /**
     * Increases the x, y or both coordinates of this Vector.
     *
     * @param vector the specified vector to add, a Vector2D
     * @throws IllegalArgumentException when the specified vector is null
     */
    public void add(final Vector2D vector) throws IllegalArgumentException {
        if (vector == null) {
            throw new IllegalArgumentException("Vector cannot be null...");
        }

        this.xCoordinate += vector.getXCoordinate();
        this.yCoordinate += vector.getYCoordinate();
    }

    /**
     * Decreases the x, y or both coordinates of this Vector.
     *
     * @param xDecrease the amount to decrease, a double
     * @param yDecrease the amount to decrease, a double
     */
    public void subtract(final double xDecrease, final double yDecrease) {
        this.xCoordinate -= xDecrease;
        this.yCoordinate -= yDecrease;
    }

    /**
     * Decreases the x, y or both coordinates of this Vector.
     *
     * @param vector the specified vector to subtract, a Vector2D
     * @throws IllegalArgumentException when the specified vector is null
     */
    public void subtract(final Vector2D vector) throws IllegalArgumentException {
        if (vector == null) {
            throw new IllegalArgumentException("Vector cannot be null...");
        }

        this.xCoordinate -= vector.getXCoordinate();
        this.yCoordinate -= vector.getYCoordinate();
    }

    /**
     * Calculates the Manhattan distance between this Vector2D and another Vector2D.
     *
     * @param vector the other Vector2D to subtract
     * @return the Manhattan distance as an int
     * @throws IllegalArgumentException if the other Vector2D is null
     */
    public int manhattanDistance(final Vector2D vector) {
        if (vector == null) {
            throw new IllegalArgumentException("Vector cannot be null...");
        }

        int xDistance = (int) Math.round(Math.abs(this.xCoordinate - vector.getXCoordinate()));
        int yDistance = (int) Math.round(Math.abs(this.yCoordinate - vector.getYCoordinate()));

        return xDistance + yDistance;
    }

    /**
     * Converts a coordinate pair from tile-space to screen-space.
     *
     * @param tileWidthInPixels the width, in pixels, of each tile
     * @param tileHeightInPixels the height, in pixels, of each tile
     * @param tileCoordinate the tile coordinates to convert as a Vector2D
     * @return the converted screen coordinates as a Vector2D
     */
    public static Vector2D tileCoordinateToScreenSpace(final double tileWidthInPixels, final double tileHeightInPixels,
                                                       final Vector2D tileCoordinate) {

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

    /**
     * Compares this Vector2D with another object.
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

        final Vector2D vector2D = (Vector2D) o;

        if (Double.compare(vector2D.xCoordinate, xCoordinate) != 0) {
            return false;
        }
        return Double.compare(vector2D.yCoordinate, yCoordinate) == 0;
    }

    /**
     * Returns this instance of Vector2D's hashcode.
     *
     * @return the hashcode as an int
     */
    @Override
    public int hashCode() {
        final int primeMultiplier = 31;
        final int bitShiftAmount = 32;
        int result;
        long temp;
        temp = Double.doubleToLongBits(xCoordinate);
        result = (int) (temp ^ (temp >>> bitShiftAmount));
        temp = Double.doubleToLongBits(yCoordinate);
        result = primeMultiplier * result + (int) (temp ^ (temp >>> bitShiftAmount));
        return result;
    }

    /**
     * Returns a String representation of this Vector2D's state.
     *
     * @return String representation of object
     */
    @Override
    public String toString() {
        return "Vector2D{"
                + "xCoordinate=" + xCoordinate
                + ", yCoordinate=" + yCoordinate
                + '}';
    }
}
