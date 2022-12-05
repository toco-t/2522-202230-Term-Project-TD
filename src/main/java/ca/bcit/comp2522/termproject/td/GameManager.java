package ca.bcit.comp2522.termproject.td;

import ca.bcit.comp2522.termproject.td.driver.SpriteRenderer;
import ca.bcit.comp2522.termproject.td.enums.*;
import ca.bcit.comp2522.termproject.td.interfaces.Attacker;
import ca.bcit.comp2522.termproject.td.interfaces.Combatant;
import ca.bcit.comp2522.termproject.td.interfaces.Drawable;
import ca.bcit.comp2522.termproject.td.map.GameMap;
import ca.bcit.comp2522.termproject.td.map.Tile;
import ca.bcit.comp2522.termproject.td.map.TileHighlight;
import ca.bcit.comp2522.termproject.td.unit.Unit;
import javafx.scene.Group;
import javafx.scene.image.Image;
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
    private final CutsceneManager cutscene;
    private ArrayList<Drawable> tileHighlights;
    private Group unitsGroup;
    private Group tilesGroup;
    private Group userInterfaceGroup;
    private Group tileHighlightGroup;
    private Group cutsceneGroup;
    private Vector2D viewOffset;

    /**
     * Constructs an object of type GameManager.
     */
    public GameManager() {
        this.playerUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.userInterface = new UIManager();
        this.cutscene = new CutsceneManager();
        this.tileHighlights = new ArrayList<>();
        this.viewOffset = new Vector2D(0, 0);

        currentTurn = CurrentTurn.PLAYER_TURN;
        turnNumber = 1;

        generateUnits(0);
        createGameMap(0);
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

            selectedUnit = null;
            userInterface.changeUnitDisplay(null);
            userInterface.changeSelectionHint(null);
            clearHighlights();
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
            System.out.printf("\n%s taking action.\n", enemy.getName());

            Combatant playerToTarget = playerInRange(enemy);

            if (playerToTarget != null) {
                enemy.attack(playerToTarget);
                System.out.printf("%s initiating attack against %s.\n", enemy.getName(), playerToTarget.getName());
                if (playerToTarget.getTurnState() == TurnState.DEAD) {
                    cutscene.displayDeathQuotes(playerToTarget.getName());
                    playerUnits.remove(playerToTarget);
                }
            }
        }

        nextPhase();
    }

    /* Returns the player in range, or null if none are in range. */
    private Combatant playerInRange(final Combatant enemy) {
        Combatant playerToTarget = null;
        Attacker enemyWeapon = enemy.getEquippedWeapon();
        double highestAccuracy = 0;

        for (Combatant player : playerUnits) {
            int distance = enemy.getLocation().manhattanDistance(player.getLocation());

            if (distance <= enemyWeapon.getRange() || enemy.getHealth() < enemy.getMaxHealth()) {
                double accuracyPerHit = enemyWeapon.getAccuracyPerHit(player, distance);
                if (accuracyPerHit > highestAccuracy) {
                    highestAccuracy = accuracyPerHit;
                    playerToTarget = player;
                }
            }
        }

        return playerToTarget;
    }

    /**
     * Groups all units and tiles into a Group, so that it can be used for rendering.
     *
     * @return the Group of units and tiles
     */
    public Group groupAllObjectsForRendering() {
        unitsGroup = SpriteRenderer.groupDrawables(entities);
        tilesGroup = SpriteRenderer.groupDrawables(map.getTilesForRendering());
        userInterfaceGroup = userInterface.getGroup();
        tileHighlightGroup = new Group();
        cutsceneGroup = cutscene.getGroup();

        return new Group(tilesGroup, tileHighlightGroup, unitsGroup, userInterfaceGroup, cutsceneGroup);
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
            case SPACE -> cutscene.changeDialogueDisplay(cutscene.loadScript());

            default -> { }
        }
    }

    /* Moves all drawable objects' offsets by a certain amount. */
    private void moveAllDrawables(final Vector2D delta) {
        viewOffset.add(delta);
        moveAllUnits(delta);
        moveAllHighlights(delta);
        map.moveTiles(delta);
    }

    /* Moves all units' (both players and enemies) offsets by a certain amount. */
    private void moveAllUnits(final Vector2D delta) {
        for (Drawable drawable : entities) {
            drawable.moveImageView(delta);
        }
    }

    /* Moves all tile highlights' offsets by a certain amount. */
    private void moveAllHighlights(final Vector2D delta) {
        for (Drawable drawable : tileHighlights) {
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
        switch (mission) {
            case -1 -> generateUnitsForTestMission();
            case 0 -> generateUnitsForCurryHouseMission();
            default -> throw new IllegalArgumentException("The requested mission does not exist.");
        }
    }

    private void generateUnitsForCurryHouseMission() {
        Unit ayumi = new Unit("Ayumi", new Vector2D(7, -1), viewOffset);
        playerUnits.add(ayumi);
        entities.add(ayumi);

        Unit miyako = new Unit("Miyako", new Vector2D(7, 0), viewOffset);
        playerUnits.add(miyako);
        entities.add(miyako);

        Unit ruInfantry = new Unit("RU Infantry", new Vector2D(16, -1), viewOffset);
        enemyUnits.add(ruInfantry);
        entities.add(ruInfantry);

        Unit ruInfantry2 = new Unit("RU Infantry", new Vector2D(16, 2), viewOffset);
        enemyUnits.add(ruInfantry2);
        entities.add(ruInfantry2);

        Unit ruInfantry3 = new Unit("RU Infantry", new Vector2D(20, 1), viewOffset);
        enemyUnits.add(ruInfantry3);
        entities.add(ruInfantry3);

        Unit ruInfantry4 = new Unit("RU Infantry", new Vector2D(20, -2), viewOffset);
        enemyUnits.add(ruInfantry4);
        entities.add(ruInfantry4);
    }

    private void generateUnitsForTestMission() {
        Unit ayumi = new Unit("Ayumi", new Vector2D(6, 0), viewOffset);
        playerUnits.add(ayumi);
        entities.add(ayumi);

        Unit miyako = new Unit("Miyako", new Vector2D(6, 1), viewOffset);
        playerUnits.add(miyako);
        entities.add(miyako);

        Unit dmitri1 = new Unit("Dmitri", new Vector2D(10, 0), viewOffset);
        enemyUnits.add(dmitri1);
        entities.add(dmitri1);

        Unit dmitri2 = new Unit("Dmitri", new Vector2D(10, 1), viewOffset);
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
     * Clears all map highlighting.
     */
    public void clearHighlights() {
        tileHighlightGroup.getChildren().clear();
        tileHighlights.clear();
    }

    /**
     * Highlights possible movement options on the game map.
     *
     * @param combatant the Combatant to move
     */
    public void highlightOptions(final Combatant combatant) {
        ArrayList<Vector2D> movementOptions = map.getMovementOptions(combatant);
        ArrayList<Vector2D> enemyPositions = getEnemyPositions();
        Image movableTile = new Image("tile_movable.png");
        Image targetTile = new Image("tile_target.png");

        if (combatant.getTurnState() == TurnState.CAN_MOVE) {
            highlightMovementOptions(movementOptions, enemyPositions, movableTile);
        }

        if (combatant.getTurnState() != TurnState.DONE) {
            highlightAttackOptions(enemyPositions, targetTile);
        }
    }

    private void highlightMovementOptions(final ArrayList<Vector2D> movementOptions,
                                          final ArrayList<Vector2D> enemyPositions, final Image movableTile) {
        for (Vector2D movementOption : movementOptions) {
            if (!enemyPositions.contains(movementOption)) {
                TileHighlight highlight = new TileHighlight(movableTile, movementOption, viewOffset);
                tileHighlightGroup.getChildren().add(highlight.getImageView());
                tileHighlights.add(highlight);
            }
        }
    }

    private void highlightAttackOptions(final ArrayList<Vector2D> enemyPositions, final Image targetTile) {
        for (Vector2D enemyPosition : enemyPositions) {
            TileHighlight highlight = new TileHighlight(targetTile, enemyPosition, viewOffset);
            tileHighlightGroup.getChildren().add(highlight.getImageView());
            tileHighlights.add(highlight);
        }
    }

    private ArrayList<Vector2D> getEnemyPositions() {
        ArrayList<Vector2D> enemyPositions = new ArrayList<>();

        for (Combatant enemy : enemyUnits) {
            enemyPositions.add(enemy.getLocation());
        }

        return enemyPositions;
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

        System.out.printf("Clicked (%f, %f)\n", tile.getLocation().getXCoordinate(),
                tile.getLocation().getYCoordinate());

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
            highlightOptions(selectedUnit);
        }
    }

    /* Take action with a selected unit depending on the context. */
    private void takeActionWithSelectedUnit(final Tile tile) {
        Combatant clickedUnit = getCombatantAtLocation(tile.getLocation());

        if (clickedUnit == null && validateUnitMove(tile)) {
            // move unit if an empty tile is clicked and a unit is selected
            moveUnit(tile);
            clearHighlights();
            highlightOptions(selectedUnit);
        } else if (clickedUnit == null && !validateUnitMove(tile)) {
            // deselect if tile outside movement range is clicked
            selectedUnit = null;
            clearHighlights();
        } else if (clickedUnit != null && clickedUnit.getAffiliation() == Affiliation.ENEMY) {
            // attack enemy if occupied tile is clicked and a friendly unit is selected
            attackEnemy(clickedUnit);
            userInterface.changeUnitDisplay(selectedUnit);
            userInterface.changeHoverHint(clickedUnit);
            clearHighlights();
        } else if (clickedUnit == selectedUnit) {
            // deselect unit if clicked again
            selectedUnit = null;
            userInterface.changeUnitDisplay(null);
            clearHighlights();
        } else if (clickedUnit != null && clickedUnit.getAffiliation() == Affiliation.PLAYER) {
            // select the other unit if it is a friendly
            selectUnit(clickedUnit);
            userInterface.changeUnitDisplay(selectedUnit);
            clearHighlights();
            highlightOptions(selectedUnit);
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
     * @param tile destination for this move, a Tile
     * @throws IllegalArgumentException when the specified coordinates is null
     */
    public void moveUnit(final Tile tile) throws IllegalArgumentException {
        Vector2D coordinates = tile.getLocation();
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null...");
        }

        if (validateUnitMove(tile)) {
            selectedUnit.moveTo(coordinates);
            selectedUnit.setTurnState(TurnState.CAN_ATTACK);
        }
    }

    private boolean validateUnitMove(final Tile tile) throws IllegalArgumentException {
        if (tile == null) {
            throw new IllegalArgumentException("Tile cannot be null for unit movement validation.");
        }

        return selectedUnit.canMoveTo(tile) && selectedUnit.getTurnState() == TurnState.CAN_MOVE;
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
        }

        if (selectedUnit.getTurnState() != TurnState.DONE) {
            selectedUnit.attack(target);
            selectedUnit.setTurnState(TurnState.DONE);
            selectedUnit = null;

            if (target.getTurnState() == TurnState.DEAD) {
                cutscene.displayDeathQuotes(target.getName());
                enemyUnits.remove(target);
            }
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
