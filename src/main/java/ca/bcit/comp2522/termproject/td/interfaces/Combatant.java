package ca.bcit.comp2522.termproject.td.interfaces;

import ca.bcit.comp2522.termproject.td.Vector2D;
import ca.bcit.comp2522.termproject.td.enums.Affiliation;
import ca.bcit.comp2522.termproject.td.enums.ArmourType;
import ca.bcit.comp2522.termproject.td.enums.Terrain;
import ca.bcit.comp2522.termproject.td.enums.TurnState;
import ca.bcit.comp2522.termproject.td.map.Tile;

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
     * Returns the name of the Combatant's weapon.
     *
     * @return the weapon name as a String
     */
    String getWeaponName();

    /**
     * Returns the equipped weapon.
     *
     * @return the equipped weapon as an Attacker
     */
    Attacker getEquippedWeapon();

    /**
     * Returns the affiliation of this Combatant.
     *
     * @return the affiliation as an Affiliation
     */
    Affiliation getAffiliation();

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
     * Returns the defense of this Combatant.
     *
     * @return the defense as an int
     */
    int getDefense();

    /**
     * Returns the evasion of this Unit.
     *
     * @return the defense as an int
     */
    int getEvasion();

    /**
     * Returns whether this Unit is aerial.
     *
     * @return whether this Unit is aerial as a boolean
     */
    boolean isAerial();

    /**
     * Returns the armour type of this Unit.
     *
     * @return the armour type as an ArmourType
     */
    ArmourType getArmourType();

    /**
     * Removes ERA armour and replaces it with MEDIUM armour.
     */
    void breakERA();


    /**
     * Returns the turn state of this Combatant.
     *
     * @return the turnState as a TurnState
     */
    TurnState getTurnState();

    /**
     * Sets the turn state of this Combatant.
     *
     * @param turnState the turn state as a TurnState
     */
    void setTurnState(TurnState turnState);

    /**
     * Returns the health of this Combatant.
     *
     * @return the health as an int
     */
    int getHealth();

    /**
     * Returns the max health of this Combatant.
     *
     * @return the max health as an int
     */
    int getMaxHealth();

    /**
     * Adds or subtracts health from the Combatant.
     *
     * @param delta the amount of add or subtract as an int
     */
    void changeHealth(int delta);

    /**
     * Returns whether this Combatant can move to the destination Tile.
     *
     * @param tile the destination Tile
     * @return true if the Combatant can move to the destination, otherwise false
     */
    boolean canMoveTo(Tile tile);

    /**
     * Initiates an attack against a target.
     *
     * @param target the Combatant to attack
     */
    void attack(Combatant target);
}
