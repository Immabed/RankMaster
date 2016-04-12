package com.immabed.rankmaster.rankings;


/**
 * A Statistic that holds a numeric value. Good for things like goals, assists, saves, points, etc.
 * @author Brady Coles
 */
public class ValueStatistic extends Statistic{
    /**
     * Value of statistic.
     */
    private Double value = null;
    /**
     * ValueStatisticSpec that defines the statistic value.
     */
    private ValueStatisticSpec spec;

    /**
     * Create a ValueStatistic object.
     * @param spec The ValueStatisticSpec that defines the nature of this ValueStatistic;
     * @param input The value to set this statistic to.
     */
    public ValueStatistic(ValueStatisticSpec spec, double input) {
        super(spec);
        this.spec = spec;
        setValue(input);
    }

    /**
     * Set the value based on the parameters of the spec. If beyond limits, is set to limit.
     * @param input Value is set to this number or limit, if this is beyond a limit.
     */
    private void setValue(double input) {
        if (spec.valueIsInteger()) {
            input = (int) input;
        }
        if (spec.valueHasMaxBound() && input > spec.getMaxValue() )
            value = spec.getMaxValue();
        else if (spec.valueHasMinBound() && input < spec.getMinValue())
            value = spec.getMinValue();
        else
            value = input;
    }

    /**
     * Get the value of this statistic
     * @return The value of this statistic.
     */
    public double getValue() {
        return value;
    }

}
