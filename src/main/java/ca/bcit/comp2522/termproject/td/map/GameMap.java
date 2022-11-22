package ca.bcit.comp2522.termproject.td.map;

import ca.bcit.comp2522.termproject.td.*;
import ca.bcit.comp2522.termproject.td.enums.Terrain;
import ca.bcit.comp2522.termproject.td.enums.Weather;
import ca.bcit.comp2522.termproject.td.interfaces.Drawable;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * GameMap that consists of number of Tiles.
 *
 * @author Toco Tachibana
 * @version 0.1
 */
public class GameMap {
    private GameManager gameManager;

    private static final Weather DEFAULT_WEATHER = Weather.SUNNY;
    private static final boolean DEFAULT_CONDITION = false;

    private final ArrayList<Tile> tiles;
    private final Weather weather;
    private final boolean isNighttime;

    /**
     * Constructs an object of type Map.
     *
     * @param gameManager the GameManager, used for handling events
     * @param weather     weather condition of this Map, an enum Weather
     * @param isNighttime a boolean
     * @param mission     the mission number as an int
     */
    public GameMap(final GameManager gameManager, final Weather weather, final boolean isNighttime, final int mission) {
        this.gameManager = gameManager;
        this.tiles = new ArrayList<>();

        this.weather = weather;
        this.isNighttime = isNighttime;

        generateMap(mission);
    }

    /**
     * Constructs an object of type Map, no params.
     *
     * @param gameManager the GameManager, used for handling events
     * @param mission     the mission number as an int
     */
    public GameMap(final GameManager gameManager, final int mission) {
        this(gameManager, DEFAULT_WEATHER, DEFAULT_CONDITION, mission);
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
                Tile tile = new Tile(gameManager, Terrain.ROAD, testTile, new Vector2D(x, y));
                tiles.add(tile);
            }
        }
    }
}
