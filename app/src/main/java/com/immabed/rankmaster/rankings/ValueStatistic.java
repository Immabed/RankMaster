package com.immabed.rankmaster.rankings;

/**
 * Created by Immabed on 2016-03-18.
 */
public class ValueStatistic extends Statistic {

    private Integer intValue = null;
    private Double doubleValue = null;
    private ValueStatisticSpec spec;

    public ValueStatistic(ValueStatisticSpec spec) {
        this.spec = spec;
    }

    public ValueStatistic(ValueStatisticSpec spec, double input) {
        this.spec = spec;

    }

    public void setValue(double input) {
        //TODO: throw value out of bounds exception
        if (spec.valueIsInteger()) {
            int intInput = (int)input;
            if (spec.valueHasMaxBound() && intInput > spec.getMaxValue() ||
                    spec.valueHasMinBound() && intInput < spec.getMinValue()) {
                //Throw exception
            }
            else {
                intValue = intInput;
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

}
