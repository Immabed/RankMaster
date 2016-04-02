package com.immabed.rankmaster.rankings;

/**
 * Created by Brady Coles on 2016-03-18.
 */
public class ValueStatistic extends Statistic {

    private Integer integerValue = null;
    private Double doubleValue = null;
    private ValueStatisticSpec spec;

    public ValueStatistic(ValueStatisticSpec spec) {
        super(spec);
        this.spec = spec;
    }

    public ValueStatistic(ValueStatisticSpec spec, double input) {
        super(spec);
        this.spec = spec;
    }

    public void setValue(double input) {
        //TODO: throw value out of bounds exception
        if (spec.valueIsInteger()) {
            int integerInput = (int)input;
            if (spec.valueHasMaxBound() && integerInput > spec.getMaxValue() ||
                    spec.valueHasMinBound() && integerInput < spec.getMinValue()) {
                //Throw exception
            }
            else {
                integerValue = integerInput;
            }
        }
        else {
            if (spec.valueHasMaxBound() && input > spec.getMaxValue() ||
                    spec.valueHasMinBound() && input < spec.getMinValue()) {
                //Throw exception
            }
            else {
                doubleValue = input;
            }
        }
    }

    public double getValue() {
        if (spec.valueIsInteger()) {
            return integerValue;
        }
        else {
            return doubleValue;
        }
    }

}
