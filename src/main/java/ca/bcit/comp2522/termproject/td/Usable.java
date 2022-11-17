package ca.bcit.comp2522.termproject.td;

/**
 * Usable objects.
 *
 * @author Toco Tachibana
 * @version 0.1
 */
public interface Usable {

    /**
     * Uses the object.
     *
     * @return effect of the object, an int
     */
    int use();

    /**
     * Returns the name of the object.
     *
     * @return name as a String
     */
    String getName();

    /**
     * Returns the description of the object.
     *
     * @return description as a String
     */
    String getDescription();
}
