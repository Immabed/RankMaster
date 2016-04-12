package com.immabed.rankmaster;

/**
 * Describes an exception where a parameter is passed that is not understood by the method.
 * @author Brady Coles
 */
public class UnknownParameterObjectException extends Exception{
    UnknownParameterObjectException(String message) {
        super(message);
    }
}
