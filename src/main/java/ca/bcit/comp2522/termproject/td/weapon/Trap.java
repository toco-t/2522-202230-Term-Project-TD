package ca.bcit.comp2522.termproject.td.weapon;

import ca.bcit.comp2522.termproject.td.interfaces.Usable;

import java.util.Objects;

/**
 * Trap that implements Usable interface.
 *
 * @author Toco Tachibana
 * @version 0.1
 */
public class Trap extends Weapon implements Usable {

    private final String description;
    private final int effect;

    /**
     * Constructs an object of type Trap.
     *
     * @param name        a String
     * @param description simple/short description, a String
     * @param effect      amount of damage that this Trap inflicts, an int
     * @throws IllegalArgumentException when the specified name is empty
     * @throws IllegalArgumentException when the specified effect is negative
     */
    public Trap(final String name, final String description, final int effect) {
        super(name);

        this.description = description;

        if (effect < 0) {
            throw new IllegalArgumentException("Effect cannot be negative...");
        } else {
            this.effect = effect;
        }
    }

    /**
     * Uses this Trap.
     *
     * @return effect of this Trap as an int
     */
    @Override
    public int use() {
        return this.effect;
    }

    /**
     * Returns the simple description of this Trap.
     *
     * @return description as a String
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the String representation of this Trap.
     *
     * @return toString as a String
     */
    @Override
    public String toString() {
        return String.format("Trap{name = %s, description = %s, effect = %d}", name, description, effect);
    }

    /**
     * Returns true if the specified object is equal to this Trap, else false.
     *
     * @param object specified item to compare, an Object
     * @return true if this Trap and the specified object are equal, else false
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Trap trap = (Trap) object;
        return effect == trap.effect && Objects.equals(name, trap.name)
                && Objects.equals(description, trap.description);
    }

    /**
     * Returns the hash code of this Trap.
     *
     * @return hashCode as an int
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, description, effect);
    }
}
