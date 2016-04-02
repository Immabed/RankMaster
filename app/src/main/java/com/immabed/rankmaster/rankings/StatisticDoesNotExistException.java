package com.immabed.rankmaster.rankings;

/**
 * Created by immabed on 2016-04-02.
 */
public class StatisticDoesNotExistException extends Exception {
    public StatisticDoesNotExistException() {
        super("Statistic does not exist.");
    }
    public StatisticDoesNotExistException(String message) {
        super(message);
    }
}
