package com.immabed.rankmaster.rankings;

import java.io.Serializable;

/**
 * A base class for an individual statistic for a single match. Statistic objects are intended to be
 * immutable to reduce risk of security leaks when statistic objects are passed around.
 * @author Brady Coles
 */
public abstract class Statistic implements Serializable{
    /**
     * The id of the StatisticSpec that defines the nature of this Statistic.
     */
    private String statisticSpecId;

    /**
     * Creates a Statistic object and sets its id with an associated StatisticSpec
     * @param spec The StatisticSpec that defines this Statistic.
     */
    public Statistic(StatisticSpec spec) {
        statisticSpecId = spec.getId();
    }

    /**
     * Returns the id of the assumed StatisticSpec
     * @return The id of the defining StatisticSpec
     */
    public String getStatisticSpecId() {
        return statisticSpecId;
    }
}
