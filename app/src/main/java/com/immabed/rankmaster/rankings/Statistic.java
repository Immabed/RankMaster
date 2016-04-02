package com.immabed.rankmaster.rankings;

/**
 * Created by Brady Coles on 2016-03-18.
 */
public abstract class Statistic {
    private String statisticSpecId;

    public Statistic(StatisticSpec spec) {
        statisticSpecId = spec.getId();
    }

    public String getStatisticSpecId() {
        return statisticSpecId;
    }
}
