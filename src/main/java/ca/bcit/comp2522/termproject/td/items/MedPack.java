package ca.bcit.comp2522.termproject.td.items;

import java.util.Objects;

/**
 * Medical Pack that extends abstract Item class.
 *
 * @author Toco Tachibana
 * @version 0.1
 */
public class MedPack extends Item {

    /**
     * Name of this Item.
     */
    public static final String NAME = "MedPack";

    /**
     * Simple description of this Item.
     */
    public static final String DESCRIPTION = "A well-stocked first-aid kit that help respond effectively to injuries.";

    /* The amount of HP that this MedPack restores */
    private final int effect;

    /**
     * Constructs an object of type MedPack.
     *
     * @param affiliation state of being affiliated with either player or enemy, a String
     * @param effect      amount of HP that this MedPack restores, an int
     * @throws IllegalArgumentException when the specified effect is negative
     */
    public MedPack(final String affiliation, final int effect) {
        super(NAME, DESCRIPTION, affiliation);

        if (effect < 0) {
            throw new IllegalArgumentException("Effect cannot be negative...");
        } else {
            this.effect = effect;
        }
    }

    /**
     * Uses this MedPack.
     *
     * @return effect of this MedPack as an int
     */
    @Override
    public int use() {
        return this.effect;
    }

    /**
     * Returns the name of this Item.
     *
     * @return name as a String
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns a simple description of this Item.
     *
     * @return description as a String
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a String representation of this MedPack.
     *
     * @return toString as a String
     */
    @Override
    public String toString() {
        return String.format("MedPack {effect = %d}", effect);
    }

    /**
     * Returns true if the specified object is equal to this MedPack, else false.
     *
     * @param object specified item to compare, an Object
     * @return true if this MedPack and the object are equal, else false
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        MedPack medPack = (MedPack) object;
        return effect == medPack.effect;
    }

    /**
     * Returns the hash code of this MedPack.
     *
     * @return hashCode as an int
     */
    @Override
    public int hashCode() {
        return Objects.hash(effect);
    }
}
