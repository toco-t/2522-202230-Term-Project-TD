package ca.bcit.comp2522.termproject.td.interfaces;

/**
 * Represents objects that can inflict damage on Combatants.
 *
 * @author Nathan
 * @version 0.1
 */
public interface Attacker {
    /**
     * Initiates an attack against another Combatant.
     *
     * @param target the Combatant to attack
     * @param distance the distance from the target in grid squares
     */
    void attack(Combatant target, int distance);

    /**
     * Calculates damage resulting from a combat initiation. This is subject to probability such as with accuracy.
     *
     * @param target the Combatant to attack
     * @param distance the distance from the target in grid squares
     * @return the damage that may result from the attack
     */
    int calculateDamage(Combatant target, int distance);

    /**
     * Calculates maximum damage resulting from a combat initiation. This assumes all bullets hit the target.
     *
     * @param target the Combatant to attack
     * @param distance the distance from the target in grid squares
     * @return the total damage that may result from the attack
     */
    int calculateMaxDamage(Combatant target, int distance);
}
