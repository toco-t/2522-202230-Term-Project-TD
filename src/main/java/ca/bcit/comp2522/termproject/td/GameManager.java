package ca.bcit.comp2522.termproject.td;

import ca.bcit.comp2522.termproject.td.interfaces.Combatant;
import ca.bcit.comp2522.termproject.td.map.GameMap;
import ca.bcit.comp2522.termproject.td.map.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeoutException;


/**
 * Game manager.
 *
 * @author Toco Tachibana
 * @version 0.1
 */
public class GameManager {

    private final ArrayList<Combatant> playerUnits;
    private final ArrayList<Combatant> enemyUnits;
    private GameMap map;
    private Combatant selectedUnit;

    /**
     * Constructs an object of type GameManager.
     */
    public GameManager() {
        this.playerUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();

        createGameMap(-1);
    }

    /**
     * Loads all the units to the specified list.
     *
     * @param lists the specified list, an ArrayList of Combatant
     * @param units variable length of units, an array
     * @throws IllegalArgumentException when units is empty
     */
    public void loadUnits(final ArrayList<Combatant> lists, final Combatant... units) {
        if (units == null) {
            throw new IllegalArgumentException("Units cannot be null...");
        }

        Collections.addAll(lists, units);
    }

    /**
     * Creates a GameMap.
     *
     * @param mission the mission number as an int
     */
    public void createGameMap(final int mission) {
        this.map = new GameMap(this, mission);
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
