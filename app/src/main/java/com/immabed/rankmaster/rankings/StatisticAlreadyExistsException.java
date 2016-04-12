package com.immabed.rankmaster.rankings;

/**
 * Exception that describes a situation where a Statistic or StatisticSpec is expected to be new, but
 * is found to already exist.
 * @author Brady Coles
 */
public class StatisticAlreadyExistsException extends Exception {
    public StatisticAlreadyExistsException() {
        super("Statistic with same id already exists.");
    }
    public StatisticAlreadyExistsException(String message) {
        super(message);
    }
}
