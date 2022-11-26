package ca.bcit.comp2522.termproject.td;

import ca.bcit.comp2522.termproject.td.driver.SpriteRenderer;
import ca.bcit.comp2522.termproject.td.enums.Weather;
import ca.bcit.comp2522.termproject.td.interfaces.Combatant;
import ca.bcit.comp2522.termproject.td.interfaces.Drawable;
import ca.bcit.comp2522.termproject.td.map.GameMap;
import ca.bcit.comp2522.termproject.td.map.Tile;
import ca.bcit.comp2522.termproject.td.unit.Unit;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeoutException;


/**
 * Game manager.
 *
 * @author Toco Tachibana
 * @version 0.2
 */
public class GameManager {
    private final ArrayList<Combatant> playerUnits;
    private final ArrayList<Combatant> enemyUnits;
    private final ArrayList<Drawable> entities;
    private GameMap map;
    private Combatant selectedUnit;

    /**
     * Constructs an object of type GameManager.
     */
    public GameManager() {
        this.playerUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.entities = new ArrayList<>();

        generateUnits(-1);
        createGameMap(-1);
    }

    /**
     * Groups all units and tiles into a Group, so that it can be used for rendering.
     *
     * @return the Group of units and tiles
     */
    public Group groupAllObjectsForRendering() {
        Group units = SpriteRenderer.groupDrawables(entities);
        Group tiles = SpriteRenderer.groupDrawables(map.getTilesForRendering());

        return new Group(tiles, units);
    }

    /**
     * Pans the "camera" by changing all drawable objects' offsets.
     *
     * @param keyEvent the KeyEvent that invoked this method
     */
    public void panCameraTo(final KeyEvent keyEvent) {
        Vector2D movementAmount;
        final double speedInPixelsPerFrame = 5;

        switch (keyEvent.getCode()) {
            case W -> {
                movementAmount = new Vector2D(0, speedInPixelsPerFrame);
            }
            case A -> {
                movementAmount = new Vector2D(speedInPixelsPerFrame, 0);
            }
            case S -> {
                movementAmount = new Vector2D(0, -speedInPixelsPerFrame);
            }
            case D -> {
                movementAmount = new Vector2D(-speedInPixelsPerFrame, 0);
            }
            default -> {
                return;
            }
        }

        moveAllDrawables(movementAmount);
    }

    /* Moves all drawable objects' offsets by a certain amount. */
    private void moveAllDrawables(final Vector2D delta) {
        moveAllUnits(delta);
        map.moveTiles(delta);
    }

    /* Moves all units' (both players and enemies) offsets by a certain amount. */
    private void moveAllUnits(final Vector2D delta) {
        for (Drawable drawable : entities) {
            drawable.moveImageView(delta);
        }
    }

    /**
     * Loads all the units to the specified list.
     *
     * @param listToLoad the specified list, an ArrayList of Combatant
     * @param units variable length of units, an array
     * @throws IllegalArgumentException when units is empty
     */
    public void loadUnits(final ArrayList<Combatant> listToLoad, final Combatant... units) {
        if (units == null) {
            throw new IllegalArgumentException("Units cannot be null...");
        }

        Collections.addAll(listToLoad, units);
    }

    /**
     * Generates units required for a mission.
     *
     * @param mission the mission number as an int
     * @throws IllegalArgumentException if the mission does not exist
     */
    public void generateUnits(final int mission) {
        if (mission == -1) {
            generateUnitsForTestMission();
        } else {
            throw new IllegalArgumentException("The requested mission does not exist.");
        }
    }

    private void generateUnitsForTestMission() {
        Unit ayumi = new Unit("Ayumi", new Vector2D(7, 0));
        playerUnits.add(ayumi);
        entities.add(ayumi);
    }

    /**
     * Creates a GameMap.
     *
     * @param mission the mission number as an int
     */
    public void createGameMap(final int mission) {
        this.map = new GameMap(this, Weather.OVERCAST, false, mission);
    }

    /**
     * Responds to a Tile being clicked, and takes action according to the context (whether a unit is selected).
     *
     * @param tile the Tile that was clicked
     */
    public void select(final Tile tile) {
        // TODO: replace this test implementation with contextual actions
        double xCoordinate = tile.getLocation().getXCoordinate();
        double yCoordinate = tile.getLocation().getYCoordinate();

        System.out.printf("Tile at (%f, %f) has been clicked.\n", xCoordinate, yCoordinate);
    }

    /**
     * Selects the specified Unit to play.
     *
     * @param unit the specified Unit to play, a Combatant
     */
    public void selectUnit(final Combatant unit) {
        if (playerUnits.contains(unit) || enemyUnits.contains(unit)) {
            this.selectedUnit = unit;
        }
    }

    /**
     * Moves the selected Unit to the location at the specified coordinates.
     *
     * @param coordinates destination for this move, a Vector2D
     * @throws IllegalArgumentException when the specified coordinates is null
     */
    public void moveUnit(final Vector2D coordinates) throws IllegalArgumentException {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null...");
        } else {
            selectedUnit.moveTo(coordinates);
        }
    }
}
