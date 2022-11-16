package ca.bcit.comp2522.termproject.td;

/**
 * A weapon that is used for attacking other Combatants. Weapon stats are used for offensive damage calculation.
 *
 * @author Nathan
 * @version 0.1
 */
public class Weapon {
    private static final int DEFAULT_DAMAGE = 300;
    private static final int DEFAULT_ACCURACY = 50;
    private static final int DEFAULT_RANGE = 5;
    private static final DamageType DEFAULT_DAMAGE_TYPE = DamageType.STANDARD;

    private final String name;
    private int damage;
    private int accuracy;
    private int range; // might use a custom range table for more advanced damage calculation later
    private final DamageType damageType;

    /**
     * Constructs an object of type Weapon using the name to determine stats.
     *
     * @param name the name of the Weapon as a String
     */
    public Weapon(final String name) {
        // replace this code with I/O to a weapon bank of some sort. This is just a placeholder.
        this.name = name;

        damage = DEFAULT_DAMAGE;
        accuracy = DEFAULT_ACCURACY;
        range = DEFAULT_RANGE;
        damageType = DEFAULT_DAMAGE_TYPE;
    }

    /**
     * Returns the name of the Weapon.
     *
     * @return the name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the accuracy of the Weapon.
     *
     * @return the accuracy as an int
     */
    public int getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the accuracy of the Weapon.
     *
     * @param accuracy the accuracy as an int
     */
    public void setAccuracy(final int accuracy) {
        this.accuracy = accuracy;
    }

    /**
     * Returns the attack strength of the Weapon.
     *
     * @return the attack strength as an int
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the attack strength of the Weapon.
     *
     * @param damage the attack strength as an int
     */
    public void setDamage(final int damage) {
        this.damage = damage;
    }

    /**
     * Returns the range of the Weapon. One range unit is equivalent to one grid square.
     *
     * @return the range as an int
     */
    public int getRange() {
        return range;
    }

    /**
     * Sets the range of the Weapon.
     *
     * @param range the range as an int
     */
    public void setRange(final int range) {
        this.range = range;
    }

    /**
     * Returns the damage type of the Weapon.
     *
     * @return the damage type as a DamageType enum
     */
    public DamageType getDamageType() {
        return damageType;
    }

    /**
     * Attacks another Combatant, potentially dealing damage to them.
     *
     * @param target the Combatant to attack
     * @param distance the distance from the target in grid squares
     * @throws IllegalArgumentException if the target is null
     */
    public void attack(final Combatant target, final int distance) {
        if (target == null) {
            throw new IllegalArgumentException("Attack target cannot be null.");
        }

        target.receiveAttack(distance, this);
    }

    /**
     * Compares this Weapon with another object.
     *
     * @param o the other Object to compare
     * @return true if they are the equal, otherwise false
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Weapon weapon = (Weapon) o;

        if (damage != weapon.damage) {
            return false;
        }
        if (accuracy != weapon.accuracy) {
            return false;
        }
        if (range != weapon.range) {
            return false;
        }
        if (!name.equals(weapon.name)) {
            return false;
        }
        return damageType == weapon.damageType;
    }

    /**
     * Returns this instance of Weapon's hashcode.
     *
     * @return the hashcode as an int
     */
    @Override
    public int hashCode() {
        final int primeMultiplier = 31;
        int result = name.hashCode();
        result = primeMultiplier * result + damage;
        result = primeMultiplier * result + accuracy;
        result = primeMultiplier * result + range;
        result = primeMultiplier * result + (damageType != null ? damageType.hashCode() : 0);
        return result;
    }

    /**
     * Returns a String representation of this Weapon's state.
     *
     * @return String representation of object
     */
    @Override
    public String toString() {
        return "Weapon{"
                + "name='" + name + '\''
                + ", damage=" + damage
                + ", accuracy=" + accuracy
                + ", range=" + range
                + ", damageType=" + damageType
                + '}';
    }
}