package com.immabed.rankmaster.rankings;

import java.io.Serializable;
import java.util.Random;

/**
 * A Player that can be used in a Match.
 * @author Brady Coles
 */
public class Player implements Serializable{
    /**
     * Uniquely identifiable id.
     */
    private String id;
    /**
     * Display name.
     */
    private String name;

    /**
     * Create a new player.
     * @param name The display name of the Player.
     */
    public Player(String name) {
        Random seed = new Random();
        id = Long.toString(seed.nextLong(), 36);
        this.name = name;
    }

    /**
     * Get the unique identifier of the player.
     * @return The unique id of te Player.
     */
    public String getId () {
        return id;
    }

    /**
     * Returns the display name.
     * @return The display name of the Player.
     */
    public String toString() {
        return name;
    }

    /**
     * Allows a name to be changes while retaining the unique identifier.
     * @param name
     */
    public void changeName(String name) {
        this.name = name;
    }
}
