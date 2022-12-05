package ca.bcit.comp2522.termproject.td.unit;

import ca.bcit.comp2522.termproject.td.enums.Terrain;
import ca.bcit.comp2522.termproject.td.enums.TurnState;
import ca.bcit.comp2522.termproject.td.interfaces.Drawable;
import ca.bcit.comp2522.termproject.td.enums.Affiliation;
import ca.bcit.comp2522.termproject.td.enums.ArmourType;
import ca.bcit.comp2522.termproject.td.interfaces.Attacker;
import ca.bcit.comp2522.termproject.td.interfaces.Combatant;
import ca.bcit.comp2522.termproject.td.Vector2D;
import ca.bcit.comp2522.termproject.td.items.Item;
import ca.bcit.comp2522.termproject.td.map.Tile;
import ca.bcit.comp2522.termproject.td.weapon.Firearm;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import static ca.bcit.comp2522.termproject.td.Vector2D.tileCoordinateToScreenSpace;

/**
 * Represents a character or vehicle on the battlefield.
 *
 * @author Nathan Ng
 * @version 0.3
 */
public class Unit implements Combatant, Drawable {
    private static final double VIEW_SIZE_X = 20;
    private static final double VIEW_SIZE_Y = 35;

    private static final double TILE_WIDTH_IN_PIXELS = 128;
    private static final double TILE_HEIGHT_IN_PIXELS = 64;

    private static final double SPRITE_SCALE = 2.5;

    private ImageView imageView;
    private String name;
    private Image sprite;
    private Affiliation affiliation;
    private TurnState turnState;
    private Vector2D location;
    private final Vector2D viewOffset;
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
     * @param location the starting location of the Unit as a Vector2D
     * @param viewOffset the offset to apply to the ImageView to simulate a camera
     * @throws IllegalArgumentException if the Unit does not exist
     */
    public Unit(final String name, final Vector2D location, final Vector2D viewOffset) {
        // replace this code with I/O to a unit bank of some sort. This is just a placeholder.
        this.name = name;
        this.location = location;
        this.viewOffset = viewOffset;

        switch (name) {
            case "Ayumi" -> getAyumiStats();
            case "Miyako" -> getMiyakoStats();
            case "RU Infantry" -> getDmitriStats();
            default -> throw new IllegalArgumentException("The requested Unit does not exist.");
        }

        generateImageView(new Vector2D(0, 0));
    }

    /* Sets this unit's stats to that of Ayumi's. */
    private void getAyumiStats() {
        sprite = new Image("ayumi.png", VIEW_SIZE_X * SPRITE_SCALE, VIEW_SIZE_Y * SPRITE_SCALE, true, false);
        affiliation = Affiliation.PLAYER;
        turnState = TurnState.CAN_MOVE;
        armourType = ArmourType.DEFAULT;
        aerial = false;

        weapons = new ArrayList<>();
        weapons.add(new Firearm("Remington M24"));

        final int ayumiHealth = 301;
        final int ayumiDefense = 90;
        final int ayumiEvasion = 80;
        final int ayumiMovementRange = 4;

        health = ayumiHealth;
        maxHealth = ayumiHealth;
        defense = ayumiDefense;
        evasion = ayumiEvasion;
        movementRange = ayumiMovementRange;
    }

    /* Sets this unit's stats to that of Ayumi's. */
    private void getMiyakoStats() {
        sprite = new Image("miyako.png", VIEW_SIZE_X * SPRITE_SCALE, VIEW_SIZE_Y * SPRITE_SCALE, true, false);
        affiliation = Affiliation.PLAYER;
        turnState = TurnState.CAN_MOVE;
        armourType = ArmourType.DEFAULT;
        aerial = false;

        weapons = new ArrayList<>();
        weapons.add(new Firearm("H&K MP7"));

        final int miyakoHealth = 355;
        final int miyakoDefense = 100;
        final int miyakoEvasion = 190;
        final int miyakoMovementRange = 4;

        health = miyakoHealth;
        maxHealth = miyakoHealth;
        defense = miyakoDefense;
        evasion = miyakoEvasion;
        movementRange = miyakoMovementRange;
    }

    /* Sets this unit's stats to that of Ayumi's. */
    private void getDmitriStats() {
        sprite = new Image("dmitri.png", VIEW_SIZE_X * SPRITE_SCALE, VIEW_SIZE_Y * SPRITE_SCALE, true, false);
        affiliation = Affiliation.ENEMY;
        turnState = TurnState.DONE;
        armourType = ArmourType.DEFAULT;
        aerial = false;

        weapons = new ArrayList<>();
        weapons.add(new Firearm("AK-12"));

        final int dmitriHealth = 416;
        final int dmitriDefense = 125;
        final int dmitriEvasion = 120;
        final int dmitriMovementRange = 4;

        health = dmitriHealth;
        maxHealth = dmitriHealth;
        defense = dmitriDefense;
        evasion = dmitriEvasion;
        movementRange = dmitriMovementRange;
    }

    /**
     * Returns the image view of this Unit.
     *
     * @return the image view as an ImageView
     */
    @Override
    public ImageView getImageView() {
        return imageView;
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
     * Returns the name of the Unit's weapon.
     *
     * @throws IllegalStateException if there is no weapon at index 0 in the unit's weapon list
     * @return the weapon name as a String
     */
    public String getWeaponName() {
        if (weapons.get(0) == null) {
            throw new IllegalStateException("Cannot attack without a weapon.");
        }
        return weapons.get(0).getName();
    }

    /**
     * Returns the equipped weapon.
     *
     * @return the equipped weapon as an Attacker
     */
    @Override
    public Attacker getEquippedWeapon() {
        if (weapons.get(0) == null) {
            throw new IllegalStateException("Cannot attack without a weapon.");
        }
        return weapons.get(0);
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
     * Returns the affiliation of this Unit.
     *
     * @return the affiliation as an Affiliation
     */
    public Affiliation getAffiliation() {
        return affiliation;
    }

    /**
     * Returns the turn state of this Unit.
     *
     * @return the turnState as a TurnState
     */
    public TurnState getTurnState() {
        return turnState;
    }

    /**
     * Sets the turn state of this Unit.
     *
     * @param turnState the turn state as a TurnState
     */
    public void setTurnState(final TurnState turnState) {
        this.turnState = turnState;
    }

    /**
     * Returns the location of this Unit.
     *
     * @return the location as a Vector2D
     */
    public Vector2D getLocation() {
        return location;
    }

    /* Gets the coordinates of this Unit in screen space (pixels). */
    private Vector2D getScreenSpaceCoordinates(final Vector2D offset) {
        double scaledViewSizeX = VIEW_SIZE_X * SPRITE_SCALE;
        double scaledViewSizeY = VIEW_SIZE_Y * SPRITE_SCALE;

        Vector2D screenSpaceCoordinates = tileCoordinateToScreenSpace(TILE_WIDTH_IN_PIXELS, TILE_HEIGHT_IN_PIXELS,
                location);

        final double verticalPositionInTile = 0.75;

        double adjustedXCoordinate = screenSpaceCoordinates.getXCoordinate() + TILE_WIDTH_IN_PIXELS / 2
                - scaledViewSizeX / 2 + offset.getXCoordinate();
        double adjustedYCoordinate = screenSpaceCoordinates.getYCoordinate() + TILE_HEIGHT_IN_PIXELS
                * (verticalPositionInTile) - scaledViewSizeY + offset.getYCoordinate();

        return new Vector2D(adjustedXCoordinate, adjustedYCoordinate);
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
     * Returns the health of this Unit.
     *
     * @return the health as an int
     */
    public int getHealth() {
        return health;
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
     * Returns the defense of this Unit.
     *
     * @return the defense as an int
     */
    public int getDefense() {
        return defense;
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
     * Moves the ImageView of this Unit without changing its actual coordinates.
     *
     * @param offsetDelta the amount to move the Unit by, as a Vector2D
     */
    @Override
    public void moveImageView(final Vector2D offsetDelta) {
        updateImageViewPosition();
    }

    /* Generates an ImageView of this Unit, using its coordinates. */
    private void generateImageView(final Vector2D spritePosition) {
        double scaledViewSizeX = VIEW_SIZE_X * SPRITE_SCALE;
        double scaledViewSizeY = VIEW_SIZE_Y * SPRITE_SCALE;

        double xOffsetPixels = VIEW_SIZE_X * spritePosition.getXCoordinate();
        double yOffsetPixels = VIEW_SIZE_Y * spritePosition.getYCoordinate();

        imageView = new ImageView(sprite);
        imageView.setMouseTransparent(true);
        imageView.setViewport(new Rectangle2D(xOffsetPixels, yOffsetPixels, scaledViewSizeX,
                scaledViewSizeY));

        updateImageViewPosition();
    }

    /* Updates the ImageView's position based on the offset. */
    private void updateImageViewPosition() {
        Vector2D screenSpaceCoordinates = getScreenSpaceCoordinates(viewOffset);
        imageView.setX(screenSpaceCoordinates.getXCoordinate());
        imageView.setY(screenSpaceCoordinates.getYCoordinate());
    }

    /**
     * Adds or subtracts health from the Unit.
     *
     * @param delta the amount of add or subtract as an int
     */
    public void changeHealth(final int delta) {
        health += delta;

        if (health <= 0) {
            health = 0;
            turnState = TurnState.DEAD;
            imageView.setVisible(false);
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
        location = destination;
        updateImageViewPosition();
    }

    /**
     * Returns whether this Unit can move to the destination Tile.
     *
     * @param tile the destination Tile
     * @return true if the Unit can move to the destination, otherwise false
     */
    public boolean canMoveTo(final Tile tile) {
        boolean inMovementRange = location.manhattanDistance(tile.getLocation()) <= movementRange;
        boolean notMovingToObstacle = tile.getTerrain() != Terrain.OBSTACLE;
        boolean notMovingToAirspaceIfUnitNotAerial = tile.getTerrain() == Terrain.AIRSPACE && aerial;
        return (inMovementRange && (notMovingToObstacle || notMovingToAirspaceIfUnitNotAerial));
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

        final int distanceToTarget = location.manhattanDistance(target.getLocation());
        weapons.get(0).attack(target, distanceToTarget);
    }
}
