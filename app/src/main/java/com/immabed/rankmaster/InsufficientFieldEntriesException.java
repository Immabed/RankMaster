package com.immabed.rankmaster;

/**
 * Exception detailing UI fields that are not entered properly.
 * @author Brady Coles
 */
public class InsufficientFieldEntriesException extends Exception {
    public InsufficientFieldEntriesException() {
        super("Insufficient fields entered.");
    }
    public InsufficientFieldEntriesException(String message) {
        super(message);
    }
}
