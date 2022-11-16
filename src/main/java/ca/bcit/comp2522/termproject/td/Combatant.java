package ca.bcit.comp2522.termproject.td;

/**
 * An object that is capable of attacking and receiving damage from Combatants.
 *
 * @author Nathan
 * @version 0.1
 */
public interface Combatant {
    /**
     * Returns the name of the Combatant.
     *
     * @return the name as a String
     */
    String getName();

    /**
     * Moves the Combatant to a specific location.
     *
     * @param destination the location to move to
     */
    void moveTo(Vector2D destination);

    /**
     * Returns the current location of the Combatant.
     *
     * @return the current location as a Vector2D.
     */
    Vector2D getLocation();

    /**
     * Adds or subtracts health from the Combatant.
     *
     * @param delta the amount of add or subtract as an int
     */
    void changeHealth(int delta);

    /**
     * Attacks another Combatant.
     *
     * @param target the Combatant to attack
     */
    void attack(Combatant target);

    /**
     * Calculates damage resulting from a combat initiation. This is subject to probability such as with accuracy.
     *
     * @param weapon the Weapon used for the attack
     * @return the damage that may result from the attack
     */
    int calculateDamage(Weapon weapon);

    /**
     * Calculates maximum damage resulting from a combat initiation. This assumes all bullets hit the target.
     *
     * @param weapon the Weapon used for the attack
     * @return the total damage that may result from the attack
     */
    int calculateMaxDamage(Weapon weapon);

    /**
     * Reduces the Combatant's health based on the attacker's weapon's stats and distance from the attacker.
     *
     * @param distance the distance of the attack, in grid squares
     * @param weapon the Weapon used for the attack
     */
    void receiveAttack(int distance, Weapon weapon);
}
