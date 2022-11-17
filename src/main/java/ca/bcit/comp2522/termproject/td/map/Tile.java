package ca.bcit.comp2522.termproject.td.map;

import ca.bcit.comp2522.termproject.td.Vector2D;

import java.util.Objects;

/**
 * Tile in a Map.
 *
 * @author Toco Tachibana
 * @version 0.1
 */
public class Tile {

    private enum Terrain { ROAD, GRASS, OBSTACLE, AIRSPACE }

    private final Vector2D coordinates;
    private final Terrain terrain;

    protected Tile(final Terrain terrain, final Vector2D coordinates) {
        this.terrain = terrain;
        this.coordinates = coordinates;
    }

    /**
     * Returns the String representation of this Tile.
     *
     * @return toString as a String
     */
    @Override
    public String toString() {
        return String.format("Tile{ coordinates = %s, terrain = %s}", coordinates.toString(), terrain.toString());
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
        return Objects.equals(coordinates, tile.coordinates) && terrain == tile.terrain;
    }

    /**
     * Returns the hash code of this Tile.
     *
     * @return hashCode as an int
     */
    @Override
    public int hashCode() {
        return Objects.hash(coordinates, terrain);
    }
}
