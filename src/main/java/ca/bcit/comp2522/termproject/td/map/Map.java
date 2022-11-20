package ca.bcit.comp2522.termproject.td.map;

import ca.bcit.comp2522.termproject.td.Drawable;
import ca.bcit.comp2522.termproject.td.Terrain;
import ca.bcit.comp2522.termproject.td.Vector2D;
import ca.bcit.comp2522.termproject.td.Weather;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Map that consists of number of Tiles.
 *
 * @author Toco Tachibana
 * @version 0.1
 */
public class Map {

    private final ArrayList<Tile> tiles;
    private final Weather weather;
    private final boolean isNighttime;

    /**
     * Constructs an object of type Map.
     *
     * @param weather     weather condition of this Map, an enum Weather
     * @param isNighttime a boolean
     */
    public Map(final Weather weather, final boolean isNighttime) {
        this.tiles = new ArrayList<>();

        this.weather = weather;
        this.isNighttime = isNighttime;
    }

    /**
     * Returns the tiles of this Map.
     *
     * @return the tiles as an ArrayList of Tile
     */
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    /**
     * Returns the tiles of this Map so that they can be rendered.
     *
     * @return the tiles as an ArrayList of Drawable
     */
    public ArrayList<Drawable> getTilesForRendering() {
        return new ArrayList<>(tiles);
    }

    /**
     * Generates the map for a specific mission.
     *
     * @param mission the mission number as an int
     * @throws IllegalArgumentException if the mission does not exist
     */
    public void generateMap(final int mission) {
        if (mission == -1) {
            generateTestMission();
        } else {
            throw new IllegalArgumentException("The requested mission does not exist.");
        }
    }

    /* Creates a small map for testing purposes. */
    private void generateTestMission() {
        final int leftmostColumn = 5;
        final int rightmostColumn = 10;

        final int topmostRow = 2;
        final int bottommostRow = -3;

        Image testTile = new Image("tile_demo.png");

        for (int x = leftmostColumn; x <= rightmostColumn; x++) {
            for (int y = bottommostRow; y <= topmostRow; y++) {
                Tile tile = new Tile(Terrain.ROAD, testTile, new Vector2D(x, y));
                tiles.add(tile);
            }
        }
    }
}
