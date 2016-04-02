package com.immabed.rankmaster.rankings;

/**
 * Created by Brady Coles on 2016-04-02.
 */
public class StatisticAlreadyExistsException extends Exception {
    public StatisticAlreadyExistsException() {
        super("Statistic with same id already exists.");
    }
    public StatisticAlreadyExistsException(String message) {
        super(message);
    }
}
