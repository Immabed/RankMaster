package com.immabed.rankmaster.rankings;

/**
 * Exception that describes a situation where a Player object was expected to be found, but was not
 * found.
 * @author Brady Coles
 */
public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException() {
        super("Player not found.");
    }
    public PlayerNotFoundException (String message) {
        super(message);
    }
}
