package com.immabed.rankmaster.rankings;

import java.util.jar.Pack200;

/**
 * Created by Immabed on 2016-03-18.
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
    public ValueStatisticSpec(boolean isInteger, boolean hasMaxBound, boolean hasMinBound, double max,
                            double min) {
        this.isInteger = isInteger;
        this.hasMaxBound = hasMaxBound;
        this.hasMinBound = hasMinBound;
        this.max = max;
        this.min = min;
    }

    /**
     * Checks if the statistic value must be an integer.
     * @return true if value must be an integer, false if value can be any real.
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


}
