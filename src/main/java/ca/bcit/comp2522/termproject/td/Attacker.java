package ca.bcit.comp2522.termproject.td;

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
     */
    void attack(Combatant target);
}
