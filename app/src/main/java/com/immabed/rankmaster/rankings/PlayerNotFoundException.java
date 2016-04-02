package com.immabed.rankmaster.rankings;

/**
 * Created by Brady Coles on 2016-04-02.
 */
public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException() {
        super("Player not found.");
    }
    public PlayerNotFoundException (String message) {
        super(message);
    }
}
