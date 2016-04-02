package com.immabed.rankmaster.rankings;


import android.renderscript.Sampler;

import java.util.ArrayList;

/**
 * Created by Brady Coles on 2016-03-18.
 */
public class ValueStatisticSpec extends StatisticSpec {

    private boolean isInteger;
    private boolean hasMaxBound;
    private double max;
    private boolean hasMinBound;
    private double min;

    /**
     * All parameters must be supplied. Values that are unneeded can be anything at all.
     * @param isInteger Is the value bound to whole numbers?
     * @param hasMaxBound Is there a maximum range for the value?
     * @param hasMinBound Is there a minimum range for the value?
     * @param max If there is a maximum bound, this is it. If not, enter anything.
     * @param min If there is a minimum bound, this is it. If not, enter anything.

     */
    public ValueStatisticSpec(String name, boolean isInteger, boolean hasMaxBound, boolean hasMinBound,
                              double max, double min) {
        super(name);
        this.isInteger = isInteger;
        this.hasMaxBound = hasMaxBound;
        this.hasMinBound = hasMinBound;
        this.max = max;
        this.min = min;
    }

    /**
     * Checks if the statistic value must be an integer.
     * @return true if value must be an integer, false if value can be any real number.
     */
    public boolean valueIsInteger() {
        return isInteger;
    }

    /**
     * Checks if value has ultimate maximum value.
     * @return true if the value has a max bound.
     */
    public boolean valueHasMaxBound() {
        return hasMaxBound;
    }

    /**
     * Checks if value has ultimate minimum value.
     * @return true if the value has a min bound.
     */
    public boolean valueHasMinBound() {
        return hasMinBound;
    }

    /**
     * Gets the maximum value that is allowed.
     * @return The maximum value that is allowed, as a double.
     */
    public double getMaxValue() {
        // TODO: Throw no max value exception
        return max;
    }

    /**
     * Gets the minimum value that is allowed.
     * @return The minimum value that is allowed, as a double.
     */
    public double getMinValue(){
        // TODO: Throw no min value exception
        return min;
    }

    /**
     * Accepts and array
     * @param statistics An array of statistics, these statistics should be of type ValueStatistic
     *                   and have the same id as this ValueStatisticSpec.
     * @return The string representation of the combined value of the inputted statistics, if those
     * statistics are of the right type (ValueStatistic), and have the same id as this Spec.
     */
    public String getStatisticString(Statistic[] statistics) {
        double sum = 0;
        for (Statistic statistic : statistics) {
            if (statistic instanceof ValueStatistic && statistic.getStatisticSpecId().equals(getId())) {
                sum += ((ValueStatistic)statistic).getValue();
            }
        }
        // TODO: String formatting
        return Double.toString(sum);
    }


}
