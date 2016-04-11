package com.immabed.rankmaster.rankings;

import java.io.Serializable;

/**
 * A base class for an individual statistic for a single match. Statistic objects are intended to be
 * immutable to reduce risk of security leaks when statistic objects are passed around.
 * @author Brady Coles
 */
public abstract class Statistic implements Serializable{
    private String statisticSpecId;

    public Statistic(StatisticSpec spec) {
        statisticSpecId = spec.getId();
    }

    public String getStatisticSpecId() {
        return statisticSpecId;
    }
}
