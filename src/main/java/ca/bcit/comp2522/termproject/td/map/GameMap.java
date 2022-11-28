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
 * @version 0.2
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
     * Moves the tiles' ImageViews to simulate camera movements.
     *
     * @param delta the amount to move, in pixels
     */
    public void moveTiles(final Vector2D delta) {
        for (Drawable tile : tiles) {
            tile.moveImageView(delta);
        }
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

        Image cocoTextures = new Image("coco_textures.png");

        for (int x = leftmostColumn; x <= rightmostColumn; x++) {
            for (int y = bottommostRow; y <= topmostRow; y++) {
                Tile tile = new Tile(gameManager, Terrain.ROAD, cocoTextures, new Vector2D(3, 0), new Vector2D(x, y));
                tile.setHeight(0);
                tiles.add(tile);
            }
        }

        for (int wallHeight = 1; wallHeight <= 3; wallHeight++) {
            for (int y = topmostRow + 1; y >= bottommostRow; y--) {
                Tile tile = new Tile(gameManager, Terrain.OBSTACLE, cocoTextures, new Vector2D(0, 1),
                        new Vector2D(leftmostColumn - 1, y));
                tile.setHeight(wallHeight);
                tiles.add(tile);
            }

            for (int x = leftmostColumn; x <= rightmostColumn; x++) {
                Tile tile = new Tile(gameManager, Terrain.OBSTACLE, cocoTextures, new Vector2D(0, 1),
                        new Vector2D(x, topmostRow + 1));
                tile.setHeight(wallHeight);
                tiles.add(tile);
            }
        }
    }
}
