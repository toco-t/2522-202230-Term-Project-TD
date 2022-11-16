package ca.bcit.comp2522.termproject.td;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Represents a character or vehicle on the battlefield.
 *
 * @author Nathan
 * @version 0.1
 */
public class Unit {
    private String name;
    private Image sprite;
    private Affiliation affiliation;
    private Vector2D location;
    private ArrayList<Item> inventory;
    private int level;
    private int expToNext;
    private int movementRange;
    private boolean aerial;
    private ArmourType armourType;
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
     * Sets the armour type of this Unit.
     *
     * @param armourType the armour type as an ArmourType
     */
    public void setArmourType(final ArmourType armourType) {
        this.armourType = armourType;
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
}
