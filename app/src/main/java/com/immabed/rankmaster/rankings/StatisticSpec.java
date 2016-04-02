package com.immabed.rankmaster.rankings;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Brady Coles on 2016-03-18.
 */
public abstract class StatisticSpec {
    private String id;
    private String name;

    StatisticSpec(String name) {
        Random seed = new Random();
        id = this.getClass().toString();
        id = id.length() > 20 ? id.substring(id.length() - 20) : id;
        id += Long.toString(seed.nextLong(), 36);

        this.name = name;
    }

    /**
     * Gets the probably unique id of the StatisticSpec, used to match statistics with their spec.
     * Id's are not guaranteed to be unique, but every type of statistic is unique from other types
     * by their class name, and each id has a randomly generated 64 bit string, meaning the chances
     * of two id's being identical is very small.
     * @return The internal id of the StatisticSpec, used to match statistics to their spec.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns name of statistic. (e.g. W-L-T, Goals, etc.)
     * @return Display name of statistic type.
     */
    public String toString() {
        return name;
    }

    public abstract String getStatisticString(Statistic[] statistics);
}
