package ca.bcit.comp2522.termproject.td.weapon;

/**
 * A firearm that deals ranged weapon. Includes infantry guns, tank cannons, and rocket tubes.
 *
 * @author Nathan
 * @version 0.1
 */
public class Firearm extends Weapon {
    private static final int DEFAULT_ACCURACY = 250;
    private int accuracy;

    /**
     * Constructs an object of type Firearm.
     *
     * @param name the name of this Firearm as a String
     * @throws IllegalArgumentException if the requested weapon does not exist
     */
    public Firearm(final String name) {
        super(name);

        switch (name) {
            case "Remington M24" -> getM24Stats();
            default -> throw new IllegalArgumentException("The requested weapon does not exist.");
        }
    }

    /* Sets this Firearm's stats to that of the Remington M24. */
    private void getM24Stats() {
        setDamage(290);
        accuracy = 190;
    }

    /**
     * Returns the accuracy of this Firearm.
     *
     * @return the accuracy as an int
     */
    public int getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the accuracy of this Firearm.
     *
     * @param accuracy the accuracy as an int
     */
    public void setAccuracy(final int accuracy) {
        this.accuracy = accuracy;
    }
}
