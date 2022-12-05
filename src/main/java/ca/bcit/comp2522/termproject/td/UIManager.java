package ca.bcit.comp2522.termproject.td;

import ca.bcit.comp2522.termproject.td.enums.CurrentTurn;
import ca.bcit.comp2522.termproject.td.interfaces.Attacker;
import ca.bcit.comp2522.termproject.td.interfaces.Combatant;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Objects;

/**
 * Manages the game's User Interface.
 *
 * @author Nathan Ng & Toco Tachibana
 * @version 0.3
 */
public class UIManager {
    private final Group hudElements;
    private final Rectangle hintBackground;
    private final Text selectionHint;
    private final Text keyPrompts;
    private final Text selectedUnitHint;
    private final Text hoverHint;
    private final Text combatForecastDisplay;
    private final Text turnDisplay;
    private final Text levelDisplay;
    private final ImageView defeatImage;
    private final ImageView victoryImage;


    /**
     * Constructs an object of type UIManager.
     */
    public UIManager() {
        hintBackground = new Rectangle(0, 526, 1024, 50);
        hintBackground.setFill(new Color(0, 0, 0,  0.5));

        selectionHint = new Text(10, 566, "SELECT a unit to move.");
        selectionHint.setFill(Color.WHITE);

        keyPrompts = new Text(700, 566, "[W][A][S][D] Adjust Camera     [ENTER] End Turn");
        keyPrompts.setFill(Color.WHITE);

        selectedUnitHint = new Text(10, 546, "");
        selectedUnitHint.setFill(Color.WHITE);

        hoverHint = new Text(700, 546, "");
        hoverHint.setFill(Color.WHITE);

        combatForecastDisplay = new Text(480, 546, "");
        combatForecastDisplay.setFill(Color.WHITE);

        turnDisplay = new Text(455, 15, "PLAYER PHASE 01");
        turnDisplay.setFill(Color.WHITE);

        levelDisplay = new Text(10, 15, "First Encounter");
        levelDisplay.setFill(Color.WHITE);

        Image gameOverImage = new Image("game_over.png");
        defeatImage = new ImageView(gameOverImage);
        defeatImage.setX(25);
        defeatImage.setY(150);
        defeatImage.setVisible(false);
        defeatImage.setMouseTransparent(true);

        Image winImage = new Image("victory.png");
        victoryImage = new ImageView(winImage);
        victoryImage.setX(25);
        victoryImage.setY(150);
        victoryImage.setVisible(false);
        defeatImage.setMouseTransparent(true);

        hudElements = new Group(hintBackground, selectionHint, selectedUnitHint, hoverHint, combatForecastDisplay,
                keyPrompts, turnDisplay, levelDisplay, defeatImage, victoryImage);
    }

    /**
     * Changes the selection hint.
     *
     * @param selectedUnit the selected unit
     */
    public void changeSelectionHint(final Combatant selectedUnit) {
        String hintToDisplay;
        if (selectedUnit != null) {
            hintToDisplay = switch (selectedUnit.getTurnState()) {
                case CAN_MOVE -> "CLICK on a BLUE tile to move there, or a RED tile to attack.";
                case CAN_ATTACK -> "CLICK on a RED tile to attack.";
                default -> "This unit cannot take any further action. After all units have moved,"
                        + " press ENTER to end your turn.";
            };
        } else {
            hintToDisplay = "SELECT a unit to move.";
        }

        changeSelectionHintTo(hintToDisplay);
    }

    /**
     * Changes the selection hint to a specific string.
     *
     * @param string the string to display
     */
    public void changeSelectionHintTo(final String string) {
        selectionHint.setText(string);
    }

    /**
     * Changes the turn display text.
     *
     * @param currentTurn the current phase of the game
     * @param turnNumber the number of elapsed turns
     */
    public void changeTurnDisplay(final CurrentTurn currentTurn, final int turnNumber) {
        String turnText;

        if (currentTurn == CurrentTurn.PLAYER_TURN) {
            turnText = "PLAYER PHASE";
        } else {
            turnText = "ENEMY PHASE";
        }

        turnDisplay.setText(String.format("%s %02d", turnText, turnNumber));
    }

    /**
     * Updates the text for the selected unit's stats.
     *
     * @param combatant the selected unit
     */
    public void changeUnitDisplay(final Combatant combatant) {
        changeHint(combatant, selectedUnitHint);
    }

    /**
     * Updates the text when hovering over a unit.
     *
     * @param combatant the selected unit
     */
    public void changeHoverHint(final Combatant combatant) {
        changeHint(combatant, hoverHint);
    }

    /**
     * Displays combat stats before battle.
     *
     * @param initiator the initiator of the attack
     * @param target the target of the attack
     * @param attacker the weapon being used to attack
     */
    public void displayCombatForecast(final Combatant initiator, final Combatant target, final Attacker attacker) {
        final int damagePerHit = attacker.getDamagePerHit(target);
        final int hits = attacker.getHits();

        final int distanceToTarget = initiator.getLocation().manhattanDistance(target.getLocation());
        double accuracyPerHit = attacker.getAccuracyPerHit(target, distanceToTarget);

        if (accuracyPerHit < 0) {
            accuracyPerHit = 0;
        } else if (accuracyPerHit > 1) {
            accuracyPerHit = 1;
        }

        final String combatForecast = String.format("DMG: %d ✕ %d\nACC: %.00f%%", damagePerHit, hits,
                accuracyPerHit * 100);

        combatForecastDisplay.setText(combatForecast);
    }

    /**
     * Hides the combat forecast.
     */
    public void hideCombatForecast() {
        combatForecastDisplay.setText("");
    }

    /* Changes the targetText to show the combatant's stats. */
    private void changeHint(final Combatant combatant, final Text targetText) {
        if (combatant != null) {
            String name = combatant.getName();
            String weapon = combatant.getWeaponName();
            int currentHealth = combatant.getHealth();
            int maxHealth = combatant.getMaxHealth();

            targetText.setText(String.format("%s (%s) %d/%d HP", name, weapon, currentHealth, maxHealth));
        } else {
            targetText.setText("");
        }
    }

    /**
     * Displays the Game Over text.
     */
    public void showGameOver() {
        defeatImage.setVisible(true);
    }

    /**
     * Displays the Victory text.
     */
    public void showVictory() {
        victoryImage.setVisible(true);
    }

    /**
     * Returns the Group of UI elements, used for rendering.
     *
     * @return the Group of UI elements
     */
    public Group getGroup() {
        return hudElements;
    }

    /**
     * Returns the string representation of this UIManager.
     *
     * @return toString
     */
    @Override
    public String toString() {
        return "UIManager{"
                + "hudElements=" + hudElements + ", hintBackground=" + hintBackground
                + ", selectionHint=" + selectionHint + ", keyPrompts=" + keyPrompts
                + ", selectedUnitHint=" + selectedUnitHint + ", hoverHint=" + hoverHint
                + ", combatForecastDisplay=" + combatForecastDisplay + ", turnDisplay=" + turnDisplay
                + ", levelDisplay=" + levelDisplay + ", defeatImage=" + defeatImage
                + ", victoryImage=" + victoryImage + '}';
    }

    /**
     * Returns true if the specified object is equal to this UIManager, else False.
     *
     * @param   o object to be compared for equality with this UIManager
     * @return  true if the specified object is equal to this UIManager, else False
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UIManager uiManager = (UIManager) o;

        if (!Objects.equals(hudElements, uiManager.hudElements)) {
            return false;
        }
        if (!Objects.equals(combatForecastDisplay, uiManager.combatForecastDisplay)) {
            return false;
        }
        if (!Objects.equals(turnDisplay, uiManager.turnDisplay)) {
            return false;
        }
        if (!Objects.equals(levelDisplay, uiManager.levelDisplay)) {
            return false;
        }

        return Objects.equals(victoryImage, uiManager.victoryImage);
    }

    /**
     * Returns the unique hashCode of this UIManager.
     *
     * @return hashCode as an int
     */
    @Override
    public int hashCode() {
        final int multiplier = 31;
        int result;
        if (hudElements != null) {
            result = hudElements.hashCode();
        } else {
            result = 0;
        }

        if (combatForecastDisplay != null) {
            result = multiplier * result + combatForecastDisplay.hashCode();
        } else {
            result = multiplier * result;
        }

        if (turnDisplay != null) {
            result = multiplier * result + turnDisplay.hashCode();
        } else {
            result = multiplier * result;
        }

        if (levelDisplay != null) {
            result = multiplier * result + levelDisplay.hashCode();
        } else {
            result = multiplier * result;
        }
        return result;
    }
}
