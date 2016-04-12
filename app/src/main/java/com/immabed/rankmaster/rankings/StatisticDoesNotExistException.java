package com.immabed.rankmaster.rankings;

/**
 * Exception that describes a situation where a Statistic or StatisticSpec object is expected but
 * not found.
 * @author Brady Coles
 */
public class StatisticDoesNotExistException extends Exception {
    public StatisticDoesNotExistException() {
        super("Statistic does not exist.");
    }
    public StatisticDoesNotExistException(String message) {
        super(message);
    }
}
