package ca.bcit.comp2522.termproject.td;

import java.io.*;

/**
 * Cutscene Manager.
 *
 * @author Toco
 * @version 0.1
 */
public class CutsceneManager {

    private int lineNumber;

    /**
     * Construct an object of type CutsceneManager.
     */
    public CutsceneManager() {
        this.lineNumber = 0;
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
}
