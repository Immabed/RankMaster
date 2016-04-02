package com.immabed.rankmaster.rankings;

/**
 * Created by Brady Coles on 2016-04-02.
 */
public class PlayerAlreadyExistsException extends Exception {
    public PlayerAlreadyExistsException() {
        super("Player already exists.");
    }
    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}
