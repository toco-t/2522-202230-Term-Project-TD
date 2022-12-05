package ca.bcit.comp2522.termproject.td;

import ca.bcit.comp2522.termproject.td.enums.CurrentTurn;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.util.Objects;

/**
 * Cutscene Manager.
 *
 * @author Toco
 * @version 0.1
 */
public class CutsceneManager {

    private final Group cutscene;
    private int lineNumber;
    private final ImageView dialogueBackground;
    private final Text dialogue;
    private final StackPane dialogueDisplay;
    private final Text instruction;
    private final ImageView endOfTurnsBackground;
    private final Text endOfTurns;

    /**
     * Construct an object of type CutsceneManager.
     */
    public CutsceneManager() {
        this.lineNumber = 0;

        dialogueBackground = new ImageView(new Image("cutscene_background.png"));

        dialogue = new Text("First Encounter");
        dialogue.setFont(Font.font("IMPACT", 28));
        dialogue.setFill(Color.WHITE);
        dialogue.setWrappingWidth(720);
        dialogue.setTextAlignment(TextAlignment.valueOf("CENTER"));

        dialogueDisplay = new StackPane(dialogue);
        dialogueDisplay.setLayoutX(150);
        dialogueDisplay.setLayoutY(400);

        instruction = new Text("PRESS ⎵ ");
        instruction.setFont(Font.font("VERDANA", 14));
        instruction.setFill(Color.WHITE);
        instruction.setX(820);
        instruction.setY(500);

        endOfTurnsBackground = new ImageView(new Image("endOfTurns_background.png"));

        endOfTurns = new Text("Player's Turn");
        endOfTurns.setFont(Font.font("IMPACT", 36));
        endOfTurns.setFill(Color.WHITE);
        endOfTurns.setLayoutX(420);
        endOfTurns.setLayoutY(300);

        endOfTurnsBackground.setVisible(false);
        endOfTurns.setVisible(false);

        cutscene = new Group(dialogueBackground, dialogueDisplay, instruction, endOfTurnsBackground, endOfTurns);
    }

    /**
     * Updates the dialogue.
     *
     * @param message next line of the script, a String
     */
    public void changeDialogueDisplay(final String message) {
        if (message != null) {
            this.dialogue.setFont(Font.font("VERDANA", 20));
            this.dialogue.setText(message);
        } else {
            dialogueBackground.setVisible(false);
            dialogueDisplay.setVisible(false);
            instruction.setVisible(false);
        }
    }

    /**
     * Displays the current turn to the player.
     *
     * @param player either player or enemy, CurrentTurn
     */
    public void showEndOfTurnsDisplay(final CurrentTurn player) {
        if (player == CurrentTurn.PLAYER_TURN) {
            endOfTurns.setText("PLAYER'S TURN...");
        } else {
            endOfTurns.setText("ENEMY'S TURN...");
        }

        endOfTurnsBackground.setVisible(true);
        endOfTurns.setVisible(true);
    }

    /**
     * Hides the current turn from the screen.
     */
    public void hideEndOfTurnsDisplay() {
        endOfTurnsBackground.setVisible(false);
        endOfTurns.setVisible(false);
    }

    /**
     * Displays death quotes when Unit gets killed in battle.
     *
     * @param name name of Unit that got killed, a String
     */
    public void displayDeathQuotes(final String name) {
        if (Objects.equals(name, "RU Infantry")) {
            return;
        }
        String deathQuote = switch (name) {
            case "Ayumi" -> "Ayumi: Ah, it was a miserable life...\nWhat could I expect but a miserable death...";
            case "Miyako" -> "Miyako: Why did I ever... fall for those anime recruitment posters?";
            default -> String.format("Killed in Action: %s", name);
        };
        dialogue.setText(deathQuote);
        dialogueBackground.setVisible(true);
        dialogueDisplay.setVisible(true);
    }


    /**
     * Retrieves the specified line of the script from script.txt.
     *
     * @return the next line of the script, a String
     */
    public String loadScript() {
        File file = new File("src/main/resources/script.txt");
        String buffer = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            int count = 0;
            while (count <= lineNumber) {
                buffer = reader.readLine();
                count++;
            }
            lineNumber++;
        } catch (IOException e) {
            System.out.println(e);
        }

        return buffer;
    }

    /**
     * Return Group of components for cutscenes.
     *
     * @return components used to render cutscenes, as a Group
     */
    public Group getGroup() {
        return cutscene;
    }
}
