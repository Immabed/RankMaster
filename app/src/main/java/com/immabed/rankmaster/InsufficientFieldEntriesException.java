package com.immabed.rankmaster;

/**
 * Created by immabed on 2016-04-07.
 */
public class InsufficientFieldEntriesException extends Exception {
    public InsufficientFieldEntriesException() {
        super("Insuffiecient fields entered.");
    }
    public InsufficientFieldEntriesException(String message) {
        super(message);
    }
}
