package ca.bcit.comp2522.termproject.td.map;

import java.util.ArrayList;

/**
 * Map that consists of number of Tiles.
 *
 * @author Toco Tachibana
 * @version 0.1
 */
public class GameMap {

    private enum Weather { SUNNY, OVERCAST, STORMY, RAINING }

    private static final Weather DEFAULT_WEATHER = Weather.SUNNY;
    private static final boolean DEFAULT_CONDITION = false;

    private final ArrayList<Tile> grids;
    private final Weather weather;
    private final boolean isNighttime;

    /**
     * Constructs an object of type Map.
     *
     * @param weather     weather condition of this Map, an enum Weather
     * @param isNighttime a boolean
     */
    public GameMap(final Weather weather, final boolean isNighttime) {
        this.grids = new ArrayList<>();

        this.weather = weather;
        this.isNighttime = isNighttime;
    }

    /**
     * Constructs an object of type Map, no params.
     */
    public GameMap() {
        this(DEFAULT_WEATHER, DEFAULT_CONDITION);
    }
}
