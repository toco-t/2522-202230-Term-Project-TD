package ca.bcit.comp2522.termproject.td.map;

import ca.bcit.comp2522.termproject.td.GameManager;
import ca.bcit.comp2522.termproject.td.Vector2D;
import ca.bcit.comp2522.termproject.td.enums.Terrain;
import ca.bcit.comp2522.termproject.td.enums.Weather;
import ca.bcit.comp2522.termproject.td.interfaces.Combatant;
import ca.bcit.comp2522.termproject.td.interfaces.Drawable;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

/**
 * GameMap that consists of number of Tiles.
 *
 * @author Toco Tachibana & Nathan Ng
 * @version 0.3
 */
public class GameMap {
    private static final Weather DEFAULT_WEATHER = Weather.SUNNY;
    private static final boolean DEFAULT_CONDITION = false;

    private final GameManager gameManager;

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

        generateWalkableFlooring(cocoTextures);
        generateNonWalkableFlooring(cocoTextures);
        generateWalls(leftmostColumn, rightmostColumn, topmostRow, bottommostRow, cocoTextures);

        // generate west counter
        for (int y = topmostRow; y >= bottommostRow; y--) {
            generateTable(cocoTextures, new Vector2D(leftmostColumn - 1, y + 1));
        }

        // generate west counter chairs
        for (int y = topmostRow; y >= bottommostRow; y--) {
            generateChair(cocoTextures, new Vector2D(leftmostColumn + 1, y));
        }

        generateTablesAndChairs(cocoTextures);

        generateCounter(cocoTextures, new Vector2D(9, 2));
        generateCounter(cocoTextures, new Vector2D(15, 2));

        // generate main counter
        for (int i = 9; i <= 15; i++) {
            generateCounter(cocoTextures, new Vector2D(i, 1));
        }
    }

    /* Generates outer walls. */
    private void generateWalls(final int leftmostColumn, final int rightmostColumn, final int topmostRow,
                               final int bottommostRow, final Image cocoTextures) {
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

    /* Generates flooring tiles that can be moved to. */
    private void generateWalkableFlooring(final Image cocoTextures) {
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
    }

    /* Generates flooring tiles that cannot be moved to. */
    private void generateNonWalkableFlooring(final Image cocoTextures) {
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
    }

    /* Generates tables and chairs. */
    private void generateTablesAndChairs(final Image cocoTextures) {
        generateTableAndChairs(cocoTextures, new Vector2D(8, -3));
        generateTableAndChairs(cocoTextures, new Vector2D(8, -4));
        generateTableAndChairs(cocoTextures, new Vector2D(12, -3));
        generateTableAndChairs(cocoTextures, new Vector2D(12, -4));
        generateTableAndChairs(cocoTextures, new Vector2D(16, -3));
        generateTableAndChairs(cocoTextures, new Vector2D(16, -4));
        generateTableAndChairs(cocoTextures, new Vector2D(17, 2));
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

                Tile tile = new Tile(gameManager, terrain, cocoTextures, new Vector2D(1, 0),
                        new Vector2D(x, y));
                tile.setHeight(0);
                tiles.add(tile);
            }
        }
    }

    private void generateChair(final Image cocoTextures, final Vector2D position) {
        Tile chair = new Tile(gameManager, Terrain.OBSTACLE, cocoTextures, new Vector2D(2, 1), position);
        chair.setHeight(0);
        tiles.add(chair);
    }

    private void generateTable(final Image cocoTextures, final Vector2D position) {
        Tile table = new Tile(gameManager, Terrain.OBSTACLE, cocoTextures, new Vector2D(2, 0), position);
        table.setHeight(0);
        tiles.add(table);
    }

    private void generateCounter(final Image cocoTextures, final Vector2D position) {
        Tile table = new Tile(gameManager, Terrain.OBSTACLE, cocoTextures, new Vector2D(1, 1), position);
        table.setHeight(1);
        tiles.add(table);
    }

    private void generateTableAndChairs(final Image cocoTextures, final Vector2D position) {
        generateChair(cocoTextures, position);
        generateTable(cocoTextures, new Vector2D(position.getXCoordinate(), position.getYCoordinate() + 1));
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

        generateWallsForTestMission(leftmostColumn, rightmostColumn, topmostRow, bottommostRow, cocoTextures);

        for (int y = topmostRow; y >= bottommostRow; y--) {
            Tile tile = new Tile(gameManager, Terrain.OBSTACLE, cocoTextures, new Vector2D(1, 1),
                    new Vector2D(leftmostColumn, y));
            tile.setHeight(0);
            tiles.add(tile);
        }
    }

    /* Generates outer walls for the test mission. */
    private void generateWallsForTestMission(final int leftmostColumn, final int rightmostColumn, final int topmostRow,
                                             final int bottommostRow, final Image cocoTextures) {
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
    }

    /**
     * Compares this GameMap with another object.
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

        final GameMap gameMap = (GameMap) o;

        if (isNighttime != gameMap.isNighttime) {
            return false;
        }
        if (!Objects.equals(gameManager, gameMap.gameManager)) {
            return false;
        }
        if (!Objects.equals(tiles, gameMap.tiles)) {
            return false;
        }
        return weather == gameMap.weather;
    }

    /**
     * Returns this instance of GameMap's hashcode.
     *
     * @return the hashcode as an int
     */
    @Override
    public int hashCode() {
        final int primeMultiplier = 31;
        int result;
        if (gameManager != null) {
            result = gameManager.hashCode();
        } else {
            result = 0;
        }
        if (tiles != null) {
            result = primeMultiplier * result + tiles.hashCode();
        } else {
            result = primeMultiplier * result;
        }
        if (weather != null) {
            result = primeMultiplier * result + weather.hashCode();
        } else {
            result = primeMultiplier * result;
        }
        if (isNighttime) {
            result = primeMultiplier * result + 1;
        } else {
            result = primeMultiplier * result;
        }
        return result;
    }

    /**
     * Returns a String representation of this GameMap's state.
     *
     * @return String representation of object
     */
    @Override
    public String toString() {
        return "GameMap{"
                + "gameManager=" + gameManager
                + ", tiles=" + tiles
                + ", weather=" + weather
                + ", isNighttime=" + isNighttime
                + '}';
    }
}
