package ca.bcit.comp2522.termproject.td.items;
import ca.bcit.comp2522.termproject.td.interfaces.Usable;

/**
 * Abstract item that implements Usable interface.
 *
 * @author Toco Tachibana
 * @version 0.1
 */
public abstract class Item implements Usable {

    /**
     * Name of this Item.
     */
    protected final String name;

    /**
     * Simple description of this Item.
     */
    protected final String description;

    /**
     * State of being affiliated with either player or enemy.
     */
    protected final String affiliation;

    protected Item(final String name, final String description, final String affiliation) {
        this.name = name;
        this.description = description;
        this.affiliation = affiliation;
    }
}
