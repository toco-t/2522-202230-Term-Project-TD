package ca.bcit.comp2522.termproject.td.weapon;

import ca.bcit.comp2522.termproject.td.ArmourType;
import ca.bcit.comp2522.termproject.td.Attacker;
import ca.bcit.comp2522.termproject.td.Combatant;
import ca.bcit.comp2522.termproject.td.DamageType;
import ca.bcit.comp2522.termproject.td.ProjectileSize;

import java.util.Objects;

/**
 * A weapon that is used for attacking other Combatants. Weapon stats are used for offensive damage calculation.
 *
 * @author Nathan
 * @version 0.1
 */
public abstract class Weapon implements Attacker {
    private static final String DEFAULT_DESCRIPTION = "I like to keep this for close encounters.";
    private static final int DEFAULT_DAMAGE = 250;
    private static final int DEFAULT_RANGE = 5;
    private static final DamageType DEFAULT_DAMAGE_TYPE = DamageType.STANDARD;
    private static final ProjectileSize DEFAULT_PROJECTILE_SIZE = ProjectileSize.BULLET;

    /**
     * Name of this Weapon.
     */
    protected final String name;
    private final String description;
    private int damage;
    private int range; // might use a custom range table for more advanced damage calculation later
    private final DamageType damageType;
    private final ProjectileSize projectileSize;

    /**
     * Constructs an object of type Weapon using the name to determine stats.
     *
     * @param name the name of this Weapon as a String
     * @throws IllegalArgumentException when the specified name is empty
     */
    public Weapon(final String name) throws IllegalArgumentException {
        // replace this code with I/O to a weapon bank of some sort. This is just a placeholder.
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Name cannot be empty...");
        } else {
            this.name = name;
        }
        this.description = DEFAULT_DESCRIPTION;

        damage = DEFAULT_DAMAGE;
        range = DEFAULT_RANGE;
        damageType = DEFAULT_DAMAGE_TYPE;
        projectileSize = DEFAULT_PROJECTILE_SIZE;
    }

    /**
     * Returns the name of this Weapon.
     *
     * @return the name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of this Weapon.
     *
     * @return the description as a String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the attack strength of this Weapon.
     *
     * @return the attack strength as an int
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the attack strength of this Weapon.
     *
     * @param damage the attack strength as an int
     */
    public void setDamage(final int damage) {
        this.damage = damage;
    }

    /**
     * Returns the range of this Weapon. One range unit is equivalent to one grid square.
     *
     * @return the range as an int
     */
    public int getRange() {
        return range;
    }

    /**
     * Sets the range of this Weapon.
     *
     * @param range the range as an int
     */
    public void setRange(final int range) {
        this.range = range;
    }

    /**
     * Returns the damage type of this Weapon.
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

        int damageDealt = calculateDamage(target, distance);

        target.changeHealth(damageDealt);
    }

    /**
     * Calculates damage resulting from a combat initiation. This is subject to probability such as with accuracy.
     *
     * @param target the Combatant to attack
     * @param distance the distance from the target in grid squares
     * @return the damage that may result from the attack
     */
    public int calculateDamage(final Combatant target, final int distance) {
        int baseDamageDealt = damage - target.getDefense();
        double accuracyModifier = getAccuracyModifier(target);
        double damageModifier = getDamageModifier(target);

        if (canBreakTargetERA(target)) {
            target.breakERA();
        }

        // TODO: apply randomness to hits

        return 0;
    }

    private double getAccuracyModifier(final Combatant target) {
        double modifier = 1;

        if (target.isAerial()) {
            final double aerialPenalty = 0.5;
            modifier *= aerialPenalty;
        }

        if (damageType == DamageType.STANDARD && target.getArmourType() == ArmourType.DEFAULT) {
            final double highCaliberAgainstInfantryPenalty = 0.2;
            modifier *= highCaliberAgainstInfantryPenalty;
        }

        return modifier;
    }

    private boolean canBreakTargetERA(final Combatant target) {
        return projectileSize == ProjectileSize.SHELL || damageType == DamageType.EXPLOSIVE;
    }

    private double getDamageModifier(final Combatant target) {
        // TODO: make this method clean
        switch (target.getArmourType()) {
            case DEFAULT -> {
                final double bulletDamageModifier = 1;
                final double explosiveDamageModifier = 1.25;
                final double dartDamageModifier = 2;
                final double heatDamageModifier = 2;
                return applyBulletArmourModifiers(bulletDamageModifier, explosiveDamageModifier, dartDamageModifier,
                        heatDamageModifier);
            }
            case LIGHT -> {
                final double bulletDamageModifier = 0.75;
                final double explosiveDamageModifier = 1;
                final double dartDamageModifier = 0.75;
                final double heatDamageModifier = 2;
                return applyBulletArmourModifiers(bulletDamageModifier, explosiveDamageModifier, dartDamageModifier,
                        heatDamageModifier);
            }
            case MEDIUM -> {
                final double bulletDamageModifier = 0;
                final double explosiveDamageModifier = 0;
                final double dartDamageModifier = 1.75;
                final double heatDamageModifier = 2;
                return applyBulletArmourModifiers(bulletDamageModifier, explosiveDamageModifier, dartDamageModifier,
                        heatDamageModifier);
            }
            case ERA -> {
                final double bulletDamageModifier = 0;
                final double explosiveDamageModifier = 0;
                final double dartDamageModifier = 0.9;
                final double heatDamageModifier = 0;
                return applyBulletArmourModifiers(bulletDamageModifier, explosiveDamageModifier, dartDamageModifier,
                        heatDamageModifier);
            }
            case COMPOSITE -> {
                final double bulletDamageModifier = 0;
                final double explosiveDamageModifier = 0;
                final double dartDamageModifier = 0.75;
                final double heatDamageModifier = 0.5;
                return applyBulletArmourModifiers(bulletDamageModifier, explosiveDamageModifier, dartDamageModifier,
                        heatDamageModifier);
            }
            default -> {
                final double bulletDamageModifier = 1;
                final double explosiveDamageModifier = 1;
                final double dartDamageModifier = 1;
                final double heatDamageModifier = 1;
                return applyBulletArmourModifiers(bulletDamageModifier, explosiveDamageModifier, dartDamageModifier,
                        heatDamageModifier);
            }
        }
    }

    private double applyBulletArmourModifiers(final double bulletDamageModifier, final double explosiveDamageModifier,
                                              final double dartDamageModifier, final double heatDamageModifier) {
        if (projectileSize == ProjectileSize.BULLET) {
            if (damageType == DamageType.STANDARD) {
                return bulletDamageModifier;
            }
            if (damageType == DamageType.EXPLOSIVE) {
                return explosiveDamageModifier;
            }
        }

        if (projectileSize == ProjectileSize.SHELL) {
            if (damageType == DamageType.STANDARD) {
                return dartDamageModifier;
            }
            if (damageType == DamageType.EXPLOSIVE) {
                return heatDamageModifier;
            }
        }

        return 1;
    }

    /**
     * Calculates maximum damage resulting from a combat initiation. This assumes all bullets hit the target.
     *
     * @param target the Combatant to attack
     * @param distance the distance from the target in grid squares
     * @return the total damage that may result from the attack
     */
    @Override
    public int calculateMaxDamage(final Combatant target, final int distance) {
        return 0;
    }

    /**
     * Compares this Weapon with another object.
     *
     * @param object the other Object to compare
     * @return true if they are the equal, otherwise false
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final Weapon weapon = (Weapon) object;

        if (damage != weapon.damage) {
            return false;
        }
        if (range != weapon.range) {
            return false;
        }
        if (!Objects.equals(name, weapon.name)) {
            return false;
        }
        if (!Objects.equals(description, weapon.description)) {
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
        int result = name != null ? name.hashCode() : 0;
        result = primeMultiplier * result + (description != null ? description.hashCode() : 0);
        result = primeMultiplier * result + damage;
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
                + ", description='" + description + '\''
                + ", damage=" + damage
                + ", range=" + range
                + ", damageType=" + damageType
                + '}';
    }
}
