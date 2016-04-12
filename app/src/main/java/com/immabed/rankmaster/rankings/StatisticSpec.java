package com.immabed.rankmaster.rankings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Abstract parent of all StatisticSpec's. These define the properties of statistics.
 * @author Brady Coles
 */
public abstract class StatisticSpec implements Serializable{
    /**
     * Unique id used to connect Statistic objects to this StatisticSpec.
     */
    private String id;
    /**
     * Display name.
     */
    private String name;

    /**
     * Creates a StatisticSpec, sets name and id.
     * @param name
     */
    public StatisticSpec(String name) {
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

    /**
     * Returns the value of a combined group of statistics as a displayable string.
     * @param statistics Array of statistics with same id as calling object.
     * @return Displayable string of combined value.
     */
    public abstract String getStatisticString(Statistic[] statistics);

    /**
     * Returns the value of a group of statistics as a comparable double.
     * @param statistics Array of statistics with same id as calling object.
     * @return Comparable double of combined statistics.
     */
    public abstract double getValue(Statistic[] statistics);
}
