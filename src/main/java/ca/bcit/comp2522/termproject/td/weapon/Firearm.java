package ca.bcit.comp2522.termproject.td.weapon;

/**
 * A firearm that deals ranged damage. Includes infantry guns, tank cannons, and rocket tubes.
 *
 * @author Nathan Ng
 * @version 0.1
 */
public class Firearm extends Weapon {

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
            case "H&K MP7" -> getMP7Stats();
            case "AK-12" -> getAK12tats();
            default -> throw new IllegalArgumentException("The requested weapon does not exist.");
        }
    }

    /* Sets this Firearm's stats to that of the Remington M24. */
    private void getM24Stats() {
        final int m24Hits = 1;
        final int m24Range = 8;
        final int m24Damage = 290;
        final int m24Accuracy = 190;

        setHits(m24Hits);
        setRange(m24Range);
        setDamage(m24Damage);
        setAccuracy(m24Accuracy);

    }

    /* Sets this Firearm's stats to that of the Remington M24. */
    private void getMP7Stats() {
        final int mp7Hits = 3;
        final int mp7Range = 4;
        final int mp7Damage = 210;
        final int mp7Accuracy = 132;

        setHits(mp7Hits);
        setRange(mp7Range);
        setDamage(mp7Damage);
        setAccuracy(mp7Accuracy);
    }

    /* Sets this Firearm's stats to that of the Remington M24. */
    private void getAK12tats() {
        final int ak12Hits = 3;
        final int ak12Range = 5;
        final int ak12Damage = 150;
        final int ak12Accuracy = 142;

        setHits(ak12Hits);
        setRange(ak12Range);
        setDamage(ak12Damage);
        setAccuracy(ak12Accuracy);
    }
}
