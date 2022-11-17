package ca.bcit.comp2522.termproject.td;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Represents a character or vehicle on the battlefield.
 *
 * @author Nathan
 * @version 0.1
 */
public class Unit implements Combatant {
    private String name;
    private Image sprite;
    private Affiliation affiliation;
    private Vector2D location;
    private ArrayList<Attacker> weapons;
    private ArrayList<Item> inventory;
    private int level;
    private int expToNext;
    private int movementRange;
    private boolean aerial;
    private ArmourType armourType;
    private int health;
    private int maxHealth;
    private int defense;
    private int evasion;

    /**
     * Constructs an object of type Unit using the name to determine stats.
     *
     * @param name the name of the Unit as a String
     */
    public Unit(final String name) {
        // replace this code with I/O to a unit bank of some sort. This is just a placeholder.
        this.name = name;
    }

    /**
     * Returns the name of this Unit.
     *
     * @return the name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this Unit.
     *
     * @param name the name as a String
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns the sprite of this Unit.
     *
     * @return the sprite as an Image
     */
    public Image getSprite() {
        return sprite;
    }

    /**
     * Sets the sprite of this Unit.
     *
     * @param sprite the sprite as an Image
     */
    public void setSprite(final Image sprite) {
        this.sprite = sprite;
    }

    /**
     * Returns the affiliation of this Unit.
     *
     * @return the affiliation as an Affiliation
     */
    public Affiliation getAffiliation() {
        return affiliation;
    }

    /**
     * Sets the affiliation of this Unit.
     *
     * @param affiliation the affiliation as an Affiliation
     */
    public void setAffiliation(final Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    /**
     * Returns the location of this Unit.
     *
     * @return the location as a Vector2D
     */
    public Vector2D getLocation() {
        return location;
    }

    /**
     * Sets the location of this Unit.
     *
     * @param location the location as a Vector2D
     */
    public void setLocation(final Vector2D location) {
        this.location = location;
    }

    /**
     * Returns this unit's list of weapons.
     *
     * @return the weapons as an ArrayList of Attacker
     */
    public ArrayList<Attacker> getWeapons() {
        return weapons;
    }

    /**
     * Sets this unit's list of weapons.
     *
     * @param weapons the weapons as an ArrayList of Attacker
     */
    public void setWeapons(final ArrayList<Attacker> weapons) {
        this.weapons = weapons;
    }

    /**
     * Returns the inventory of this Unit.
     *
     * @return the inventory as an ArrayList of Item
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * Sets the inventory of this Unit.
     *
     * @param inventory the inventory as an ArrayList of Item
     */
    public void setInventory(final ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    /**
     * Returns the level of this Unit.
     *
     * @return the level as an int
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level of this Unit.
     *
     * @param level the level as an int
     */
    public void setLevel(final int level) {
        this.level = level;
    }

    /**
     * Returns the amount of experience required to level up this Unit.
     *
     * @return the experience to next level as an int
     */
    public int getExpToNext() {
        return expToNext;
    }

    /**
     * Sets the experience required to level up this Unit.
     *
     * @param expToNext the experience to next level as an int
     */
    public void setExpToNext(final int expToNext) {
        this.expToNext = expToNext;
    }

    /**
     * Returns the movement range of this Unit.
     *
     * @return the movement range as an int
     */
    public int getMovementRange() {
        return movementRange;
    }

    /**
     * Sets the movement range of this Unit.
     *
     * @param movementRange the movement range as an int
     */
    public void setMovementRange(final int movementRange) {
        this.movementRange = movementRange;
    }

    /**
     * Returns whether this Unit is aerial.
     *
     * @return whether this Unit is aerial as a boolean
     */
    public boolean isAerial() {
        return aerial;
    }

    /**
     * Sets whether this Unit is aerial.
     *
     * @param aerial whether this Unit will be aerial as a boolean
     */
    public void setAerial(final boolean aerial) {
        this.aerial = aerial;
    }

    /**
     * Returns the armour type of this Unit.
     *
     * @return the armour type as an ArmourType
     */
    public ArmourType getArmourType() {
        return armourType;
    }

    /**
     * Removes ERA armour and replaces it with MEDIUM armour.
     */
    public void breakERA() {
        if (armourType == ArmourType.ERA) {
            armourType = ArmourType.MEDIUM;
        }
    }

    /**
     * Sets the armour type of this Unit.
     *
     * @param armourType the armour type as an ArmourType
     */
    public void setArmourType(final ArmourType armourType) {
        this.armourType = armourType;
    }

    /**
     * Returns the health of this Unit.
     *
     * @return the health as an int
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of this Unit.
     *
     * @param health the health as an int
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Returns the max health of this Unit.
     *
     * @return the max health as an int
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Sets the max health of this Unit.
     *
     * @param maxHealth the max health as an int
     */
    public void setMaxHealth(final int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Returns the defense of this Unit.
     *
     * @return the defense as an int
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Sets the defense of this Unit.
     *
     * @param defense the defense as an int
     */
    public void setDefense(final int defense) {
        this.defense = defense;
    }

    /**
     * Returns the evasion of this Unit.
     *
     * @return the evasion as an int
     */
    public int getEvasion() {
        return evasion;
    }

    /**
     * Sets the evasion of this Unit.
     *
     * @param evasion the evasion as an int
     */
    public void setEvasion(final int evasion) {
        this.evasion = evasion;
    }

    /**
     * Adds or subtracts health from the Unit.
     *
     * @param delta the amount of add or subtract as an int
     */
    public void changeHealth(final int delta) {
        health += delta;

        if (health < 0) {
            health = 0;
        }
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    /**
     * Moves the Combatant to a specific location.
     *
     * @param destination the location to move to
     */
    public void moveTo(final Vector2D destination) {
        // TODO: check if destination is valid
        location = destination;
    }

    /**
     * Initiates an attack against a target.
     *
     * @param target the Combatant to attack
     * @throws IllegalStateException if there is no weapon at index 0 in the unit's weapon list
     */
    public void attack(final Combatant target) {
        if (weapons.get(0) == null) {
            throw new IllegalStateException("Cannot attack without a weapon.");
        }
        weapons.get(0).attack(target, 0);
    }
}
