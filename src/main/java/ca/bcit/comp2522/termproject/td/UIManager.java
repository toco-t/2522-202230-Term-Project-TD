package ca.bcit.comp2522.termproject.td;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * Manages the game's User Interface.
 *
 * @author Nathan
 * @version 0.2
 */
public class UIManager {
    private final Text selectionHint;

    /**
     * Constructs an object of type UIManager.
     */
    public UIManager() {
        selectionHint = new Text(10, 566, "Select a unit to move.");
        selectionHint.setFill(Color.WHITE);
    }

    /**
     * Returns the selection hint of this UIManager.
     *
     * @return the selection hint as a Text
     */
    public Text getSelectionHint() {
        return selectionHint;
    }

    /**
     * Changes the selection hint.
     *
     * @param newHint the new hint as a String
     */
    public void changeSelectionHint(final String newHint) {
        selectionHint.setText(newHint);
    }

    /**
     * Returns the Group of UI elements, used for rendering.
     *
     * @return the Group of UI elements
     */
    public Group getGroup() {
        return new Group(selectionHint);
    }
}
