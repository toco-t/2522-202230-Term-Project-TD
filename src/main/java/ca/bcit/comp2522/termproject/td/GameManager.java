package ca.bcit.comp2522.termproject.td;

import ca.bcit.comp2522.termproject.td.driver.SpriteRenderer;
import ca.bcit.comp2522.termproject.td.enums.Affiliation;
import ca.bcit.comp2522.termproject.td.enums.CurrentTurn;
import ca.bcit.comp2522.termproject.td.enums.TurnState;
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


/**
 * Game manager.
 *
 * @author Toco Tachibana
 * @version 0.2
 */
public class GameManager {
    private int turnNumber;
    private CurrentTurn currentTurn;
    private final ArrayList<Combatant> playerUnits;
    private final ArrayList<Combatant> enemyUnits;
    private final ArrayList<Drawable> entities;
    private final UIManager userInterface;
    private GameMap map;
    private Combatant selectedUnit;

    /**
     * Constructs an object of type GameManager.
     */
    public GameManager() {
        this.playerUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.userInterface = new UIManager();

        currentTurn = CurrentTurn.PLAYER_TURN;
        turnNumber = 1;

        generateUnits(-1);
        createGameMap(-1);
    }

    /**
     * Ends the player phase.
     */
    public void endPlayerTurn() {
        if (currentTurn == CurrentTurn.PLAYER_TURN) {
            nextPhase();
        }
    }

    /**
     * Advances to the next turn.
     */
    public void nextPhase() {
        updateTurnCounter();
        setUnitTurnState();
        userInterface.changeTurnDisplay(currentTurn, turnNumber);

        if (currentTurn == CurrentTurn.ENEMY_TURN) {
            takeEnemyTurn();
        }
    }

    /* Updates the phase and adds one to the turn counter if the enemy phase just passed. */
    private void updateTurnCounter() {
        if (currentTurn == CurrentTurn.PLAYER_TURN) {
            currentTurn = CurrentTurn.ENEMY_TURN;
        } else {
            currentTurn = CurrentTurn.PLAYER_TURN;
            turnNumber++;
        }
    }

    /* Sets all the units of a particular team to be movable again. */
    private void setUnitTurnState() {
        ArrayList<Combatant> listToUse = switch (currentTurn) {
            case PLAYER_TURN -> playerUnits;
            case ENEMY_TURN -> enemyUnits;
        };

        for (Combatant combatant : listToUse) {
            combatant.setTurnState(TurnState.CAN_MOVE);
        }
    }

    /* Takes all enemies' turns. */
    private void takeEnemyTurn() {
        for (Combatant enemy : enemyUnits) {
            System.out.printf("%s taking action.\n", enemy.getName());
        }

        nextPhase();
    }

    /**
     * Groups all units and tiles into a Group, so that it can be used for rendering.
     *
     * @return the Group of units and tiles
     */
    public Group groupAllObjectsForRendering() {
        Group units = SpriteRenderer.groupDrawables(entities);
        Group tiles = SpriteRenderer.groupDrawables(map.getTilesForRendering());
        Group userInterfaceElements = userInterface.getGroup();

        return new Group(tiles, units, userInterfaceElements);
    }

    /**
     * Pans the "camera" by changing all drawable objects' offsets.
     *
     * @param keyEvent the KeyEvent that invoked this method
     */
    public void panCameraTo(final KeyEvent keyEvent) {
        final double speedInPixelsPerFrame = 10;

        switch (keyEvent.getCode()) {
            case ENTER -> endPlayerTurn();
            case W -> moveAllDrawables(new Vector2D(0, speedInPixelsPerFrame));
            case A -> moveAllDrawables(new Vector2D(speedInPixelsPerFrame, 0));
            case S -> moveAllDrawables(new Vector2D(0, -speedInPixelsPerFrame));
            case D -> moveAllDrawables(new Vector2D(-speedInPixelsPerFrame, 0));
            default -> { }
        }
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
        Unit ayumi = new Unit("Ayumi", new Vector2D(6, 0));
        playerUnits.add(ayumi);
        entities.add(ayumi);

        Unit miyako = new Unit("Miyako", new Vector2D(6, 1));
        playerUnits.add(miyako);
        entities.add(miyako);

        Unit dmitri1 = new Unit("Dmitri", new Vector2D(10, 0));
        enemyUnits.add(dmitri1);
        entities.add(dmitri1);

        Unit dmitri2 = new Unit("Dmitri", new Vector2D(10, 1));
        enemyUnits.add(dmitri2);
        entities.add(dmitri2);
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
     * Responds to a Tile being hovered over by showing the hovered unit's stats.
     *
     * @param tile the Tile that was hovered over
     */
    public void hoverHint(final Tile tile) {
        if (currentTurn == CurrentTurn.ENEMY_TURN) {
            return;
        }

        Vector2D selectionLocation = tile.getLocation();
        Combatant hoveredUnit = getCombatantAtLocation(selectionLocation);

        userInterface.changeHoverHint(hoveredUnit);

        if (selectedUnit != null && hoveredUnit != null && hoveredUnit.getAffiliation() == Affiliation.ENEMY) {
            userInterface.displayCombatForecast(selectedUnit, hoveredUnit, selectedUnit.getEquippedWeapon());
        } else {
            userInterface.hideCombatForecast();
        }
    }

    /**
     * Responds to a Tile being clicked, and takes action according to the context (whether a unit is selected).
     *
     * @param tile the Tile that was clicked
     */
    public void select(final Tile tile) {
        if (currentTurn == CurrentTurn.ENEMY_TURN) {
            return;
        }

        // a friendly unit is already selected, take action with them...
        if (selectedUnit != null) {
            takeActionWithSelectedUnit(tile);
        } else {
            takeActionWithoutSelectedUnit(tile);
        }

        userInterface.changeSelectionHint(selectedUnit);
    }

    /* Take action without a selected unit depending on the context. */
    private void takeActionWithoutSelectedUnit(final Tile tile) {
        Combatant clickedUnit = getCombatantAtLocation(tile.getLocation());

        // select unit if a unit is clicked but no unit is already selected
        if (clickedUnit != null && clickedUnit.getAffiliation() == Affiliation.PLAYER) {
            selectedUnit = clickedUnit;
            userInterface.changeUnitDisplay(selectedUnit);
        }
    }

    /* Take action with a selected unit depending on the context. */
    private void takeActionWithSelectedUnit(final Tile tile) {
        Vector2D selectionLocation = tile.getLocation();
        Combatant clickedUnit = getCombatantAtLocation(tile.getLocation());

        // move unit if an empty tile is clicked and a unit is selected
        if (clickedUnit == null) {
            moveUnit(selectionLocation);
        }

        // attack enemy if occupied tile is clicked and a friendly unit is selected
        if (clickedUnit != null && clickedUnit.getAffiliation() == Affiliation.ENEMY) {
            attackEnemy(clickedUnit);
            userInterface.changeUnitDisplay(selectedUnit);
            userInterface.changeHoverHint(clickedUnit);
        }

        // deselect unit if clicked again
        if (clickedUnit == selectedUnit) {
            selectedUnit = null;
            userInterface.changeUnitDisplay(null);
        }

        // select the other unit if it is a friendly
        if (clickedUnit != null && selectedUnit != null && clickedUnit.getAffiliation() == Affiliation.PLAYER) {
            selectUnit(clickedUnit);
            userInterface.changeUnitDisplay(selectedUnit);
        }
    }

    /* Gets a combatant at a specific world-space location, or returns null if tile is empty. */
    private Combatant getCombatantAtLocation(final Vector2D location) {
        // possibly replace combatant list with hashset based on coordinates?
        for (Combatant combatant : playerUnits) {
            if (combatant.getLocation().equals(location)) {
                return combatant;
            }
        }

        for (Combatant combatant : enemyUnits) {
            if (combatant.getLocation().equals(location)) {
                return combatant;
            }
        }

        return null;
    }

    /**
     * Selects the specified Unit to play.
     *
     * @param unit the specified Unit to play, a Combatant
     */
    public void selectUnit(final Combatant unit) {
        this.selectedUnit = unit;
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
        } else if (selectedUnit.getTurnState() == TurnState.CAN_MOVE) {
            selectedUnit.moveTo(coordinates);
            selectedUnit.setTurnState(TurnState.CAN_ATTACK);
        } else {
            selectedUnit = null;
        }
    }

    /**
     * Attacks the target using the selected Unit.
     *
     * @param target the Combatant to attack
     * @throws IllegalArgumentException if target is null
     */
    public void attackEnemy(final Combatant target) throws IllegalArgumentException {
        if (target == null) {
            throw new IllegalArgumentException("The target to attack cannot be null.");
        } else if (selectedUnit.getTurnState() != TurnState.DONE) {
            selectedUnit.attack(target);
            selectedUnit.setTurnState(TurnState.DONE);
            selectedUnit = null;
        }
    }

    /**
     * Displays the selected enemy target's stats.
     *
     * @param target the Combatant whose stats will be revealed
     * @throws IllegalArgumentException if target is null
     */
    public void displayEnemyStats(final Combatant target) throws IllegalArgumentException {
        if (target == null) {
            throw new IllegalArgumentException("The enemy is null.");
        } else {
            userInterface.changeUnitDisplay(target);
        }
    }
}
