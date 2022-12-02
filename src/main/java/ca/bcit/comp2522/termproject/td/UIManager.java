package ca.bcit.comp2522.termproject.td;

import ca.bcit.comp2522.termproject.td.enums.CurrentTurn;
import ca.bcit.comp2522.termproject.td.interfaces.Attacker;
import ca.bcit.comp2522.termproject.td.interfaces.Combatant;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.Effect;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Manages the game's User Interface.
 *
 * @author Nathan & Toco
 * @version 0.2
 */
public class UIManager {
    private final Text selectionHint;
    private final Text keyPrompts;
    private final Text selectedUnitHint;
    private final Text hoverHint;
    private final Text combatForecastDisplay;
    private final Text turnDisplay;
    private final Text levelDisplay;
    private final Rectangle dialogueDisplay;
    private final Text dialogue;
    private final StackPane stack = new StackPane();

    /**
     * Constructs an object of type UIManager.
     */
    public UIManager() {
        selectionHint = new Text(10, 566, "Select a unit to move.");
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

        levelDisplay = new Text(10, 15, "Test Mission");
        levelDisplay.setFill(Color.WHITE);

        dialogueDisplay = new Rectangle(0, 0, 780, 160);
        Color colour = new Color(0.2, 0.2, 0.2, 0.7);
        dialogueDisplay.setFill(colour);

        dialogue = new Text("First Mission - Battle in CoCo");
        dialogue.setFont(Font.font("VERDANA", 20));

        dialogue.setFill(Color.WHITE);
        dialogue.setWrappingWidth(720);
        dialogue.setTextAlignment(TextAlignment.valueOf("CENTER"));

        stack.getChildren().addAll(dialogueDisplay, dialogue);
        stack.setLayoutX(150);
        stack.setLayoutY(360);
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
                case CAN_MOVE -> "Click on a blue tile to move there, or a red tile to attack.";
                case CAN_ATTACK -> "Click on a red tile to attack.";
                default -> "This unit cannot take any further action.";
            };
        } else {
            hintToDisplay = "Select a unit to move.";
        }

        selectionHint.setText(hintToDisplay);
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
        if (combatant != null) {
            String name = combatant.getName();
            String weapon = "Remington M24";
            int currentHealth = combatant.getHealth();
            int maxHealth = combatant.getMaxHealth();

            selectedUnitHint.setText(String.format("%s (%s) %d/%d HP", name, weapon, currentHealth, maxHealth));
        } else {
            selectedUnitHint.setText("");
        }
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
     * Updates the dialogue.
     *
     * @param message next line of the script, a String
     */
    public void changeDialogueDisplay(final String message) {
        if (message != null) {
            this.dialogue.setText(message);
        } else {
            stack.setVisible(false);
            dialogueDisplay.setVisible(false);
        }
    }

    /**
     * Returns the Group of UI elements, used for rendering.
     *
     * @return the Group of UI elements
     */
    public Group getGroup() {
        return new Group(selectionHint, selectedUnitHint, hoverHint, combatForecastDisplay, keyPrompts, turnDisplay,
                levelDisplay, stack);
    }
}
