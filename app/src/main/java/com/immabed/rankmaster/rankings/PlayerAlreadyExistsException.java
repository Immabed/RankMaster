package com.immabed.rankmaster.rankings;

/**
 * Exception that describes a situation where a Player was expected to be new, but was found to
 * already exist in whatever situation the exception is created.
 * @author Brady Coles
 */
public class PlayerAlreadyExistsException extends Exception {
    public PlayerAlreadyExistsException() {
        super("Player already exists.");
    }
    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}
