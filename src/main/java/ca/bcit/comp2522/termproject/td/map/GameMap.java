package ca.bcit.comp2522.termproject.td.map;

import ca.bcit.comp2522.termproject.td.*;
import ca.bcit.comp2522.termproject.td.enums.Terrain;
import ca.bcit.comp2522.termproject.td.enums.Weather;
import ca.bcit.comp2522.termproject.td.interfaces.Combatant;
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
     * Returns a list of tile-space coordinates where the combatant to move to on the map.
     *
     * @param combatant the Combatant to move
     * @return a list of Vector2D representing possible tile-space coordinates
     */
    public ArrayList<Vector2D> getMovementOptions(final Combatant combatant) {
        ArrayList<Vector2D> movementOptions = new ArrayList<>();
        for (Tile tile : tiles) {
            if (combatant.canMoveTo(tile)) {
                movementOptions.add(tile.getLocation());
            }
        }

        return movementOptions;
    }

    /**
     * Generates the map for a specific mission.
     *
     * @param mission the mission number as an int
     * @throws IllegalArgumentException if the mission does not exist
     */
    public void generateMap(final int mission) {
        switch (mission) {
            case -1 -> generateTestMission();
            case 0 -> generateCurryHouseMission();
            default -> throw new IllegalArgumentException("The requested mission does not exist.");
        }
    }

    /* Generates the tiles for the Curry House mission. */
    private void generateCurryHouseMission() {
        final int leftmostColumn = 5;
        final int rightmostColumn = 20;

        final int topmostRow = 2;
        final int bottommostRow = -4;

        Image cocoTextures = new Image("coco_textures.png");

        // generate walkable flooring
        generateFlooring(true, cocoTextures, new Vector2D(7, 2),
                new Vector2D(7, -4));
        generateFlooring(true, cocoTextures, new Vector2D(8, 2),
                new Vector2D(8, 1));
        generateFlooring(true, cocoTextures, new Vector2D(8, 0),
                new Vector2D(20, -2));
        generateFlooring(true, cocoTextures, new Vector2D(11, -3),
                new Vector2D(11, -4));
        generateFlooring(true, cocoTextures, new Vector2D(15, -3),
                new Vector2D(15, -4));
        generateFlooring(true, cocoTextures, new Vector2D(16, 1),
                new Vector2D(20, 1));
        generateFlooring(true, cocoTextures, new Vector2D(16, 2),
                new Vector2D(16, 2));
        generateFlooring(true, cocoTextures, new Vector2D(19, -3),
                new Vector2D(20, -4));
        generateFlooring(true, cocoTextures, new Vector2D(20, 2),
                new Vector2D(20, 2));

        // generate non-walkable flooring
        generateFlooring(false, cocoTextures, new Vector2D(5, 2),
                new Vector2D(6, -4));
        generateFlooring(false, cocoTextures, new Vector2D(8, -3),
                new Vector2D(10, -4));
        generateFlooring(false, cocoTextures, new Vector2D(12, -3),
                new Vector2D(14, -4));
        generateFlooring(false, cocoTextures, new Vector2D(16, -3),
                new Vector2D(18, -4));
        generateFlooring(false, cocoTextures, new Vector2D(17, 2),
                new Vector2D(19, 2));
        generateFlooring(false, cocoTextures, new Vector2D(9, 2),
                new Vector2D(15, 1));

        // generate walls
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

        // generate west counter
        for (int y = topmostRow; y >= bottommostRow; y--) {
            generateTable(cocoTextures, new Vector2D(leftmostColumn, y));
        }

        // generate west counter chairs
        for (int y = topmostRow; y >= bottommostRow; y--) {
            generateChair(cocoTextures, new Vector2D(leftmostColumn + 1, y));
        }

        generateTableAndChairs(cocoTextures, new Vector2D(8, -3));
        generateTableAndChairs(cocoTextures, new Vector2D(8, -4));
        generateTableAndChairs(cocoTextures, new Vector2D(12, -3));
        generateTableAndChairs(cocoTextures, new Vector2D(12, -4));
        generateTableAndChairs(cocoTextures, new Vector2D(16, -3));
        generateTableAndChairs(cocoTextures, new Vector2D(16, -4));
        generateTableAndChairs(cocoTextures, new Vector2D(17, 2));

        generateTable(cocoTextures, new Vector2D(9, 2));
        generateTable(cocoTextures, new Vector2D(15, 2));

        for (int i = 9; i <= 15; i++) {
            generateTable(cocoTextures, new Vector2D(i, 1));
        }
    }

    private void generateFlooring(final boolean walkable, final Image cocoTextures, final Vector2D northwestCorner,
                                  final Vector2D southeastCorner) {
        for (int x = (int) northwestCorner.getXCoordinate(); x <= southeastCorner.getXCoordinate(); x++) {
            for (int y = (int) southeastCorner.getYCoordinate(); y <= northwestCorner.getYCoordinate(); y++) {
                Terrain terrain;
                if (walkable) {
                    terrain = Terrain.ROAD;
                } else {
                    terrain = Terrain.OBSTACLE;
                }

                Tile tile = new Tile(gameManager, terrain, cocoTextures, new Vector2D(2, 0),
                        new Vector2D(x, y));
                tile.setHeight(0);
                tiles.add(tile);
            }
        }
    }

    private void generateChair(final Image cocoTextures, final Vector2D position) {
        Tile chair = new Tile(gameManager, Terrain.OBSTACLE, cocoTextures, new Vector2D(1, 1), position);
        chair.setHeight(0);
        tiles.add(chair);
    }

    private void generateTable(final Image cocoTextures, final Vector2D position) {
        Tile table = new Tile(gameManager, Terrain.OBSTACLE, cocoTextures, new Vector2D(0, 1), position);
        table.setHeight(1);
        tiles.add(table);
    }

    private void generateCounter(final Image cocoTextures, final Vector2D position) {
        Tile table = new Tile(gameManager, Terrain.OBSTACLE, cocoTextures, new Vector2D(0, 1), position);
        table.setHeight(1);
        tiles.add(table);
    }

    private void generateTableAndChairs(final Image cocoTextures, final Vector2D position) {
        generateChair(cocoTextures, position);
        generateTable(cocoTextures, new Vector2D(position.getXCoordinate() + 1, position.getYCoordinate()));
        generateChair(cocoTextures, new Vector2D(position.getXCoordinate() + 2, position.getYCoordinate()));
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
                Tile tile = new Tile(gameManager, Terrain.ROAD, cocoTextures, new Vector2D(2, 0), new Vector2D(x, y));
                tile.setHeight(0);
                tiles.add(tile);
            }
        }

        for (int wallHeight = 1; wallHeight <= 3; wallHeight++) {
            for (int y = topmostRow + 1; y >= bottommostRow; y--) {
                Tile tile = new Tile(gameManager, Terrain.OBSTACLE, cocoTextures, new Vector2D(0, 0),
                        new Vector2D(leftmostColumn - 1, y));
                tile.setHeight(wallHeight);
                tiles.add(tile);
            }

            for (int x = leftmostColumn; x <= rightmostColumn; x++) {
                Tile tile = new Tile(gameManager, Terrain.OBSTACLE, cocoTextures, new Vector2D(0, 0),
                        new Vector2D(x, topmostRow + 1));
                tile.setHeight(wallHeight);
                tiles.add(tile);
            }
        }

        for (int y = topmostRow; y >= bottommostRow; y--) {
            Tile tile = new Tile(gameManager, Terrain.OBSTACLE, cocoTextures, new Vector2D(1, 1),
                    new Vector2D(leftmostColumn, y));
            tile.setHeight(0);
            tiles.add(tile);
        }
    }
}
