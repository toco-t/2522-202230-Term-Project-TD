package ca.bcit.comp2522.termproject.td;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.*;

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

    /**
     * Construct an object of type CutsceneManager.
     */
    public CutsceneManager() {
        this.lineNumber = 0;

        dialogueBackground = new ImageView(new Image("cutscene_background.png"));

        dialogue = new Text("First Mission - Battle in CoCo");
        dialogue.setFont(Font.font("IMPACT", 28));
        dialogue.setFill(Color.WHITE);
        dialogue.setWrappingWidth(720);
        dialogue.setTextAlignment(TextAlignment.valueOf("CENTER"));

        dialogueDisplay = new StackPane(dialogue);
        dialogueDisplay.setLayoutX(150);
        dialogueDisplay.setLayoutY(400);

        Text instruction = new Text("PRESS ⎵ ");
        instruction.setFont(Font.font("VERDANA", 14));
        instruction.setFill(Color.WHITE);
        instruction.setX(820);
        instruction.setY(500);


        cutscene = new Group(dialogueBackground, dialogueDisplay, instruction);
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
            cutscene.setVisible(false);
        }
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

    public Group getGroup() {
        return cutscene;
    }
}
