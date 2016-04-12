package com.immabed.rankmaster;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.immabed.rankmaster.rankings.Statistic;
import com.immabed.rankmaster.rankings.StatisticSpec;
import com.immabed.rankmaster.rankings.ValueStatistic;
import com.immabed.rankmaster.rankings.ValueStatisticSpec;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ValueStatisticFragment.OnStatisticFragmentInteractionListener
 * } interface
 * to handle interaction events.
 * Use the {@link ValueStatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Modified from a template.
 * @author Brady Coles
 */
public class ValueStatisticFragment extends StatisticFragment {

    /**
     * The ValueStatisticSpec that defines the ValueStatistic this fragment creates.
     */
    private ValueStatisticSpec spec;
    /**
     * The field that the user enters the value into.
     */
    private TextView valueField;

    private OnStatisticFragmentInteractionListener
            mListener;

    public ValueStatisticFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Get ValueStatisticSpec from bundle
            spec = (ValueStatisticSpec)getArguments().getSerializable(StatisticFragment.ARG_SPEC);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_value_statistic, container, false);

        // Populate members
        valueField = (TextView)view.findViewById(R.id.statistic_value_field);
        // Set limits of the valueField based on spec
        setFieldParameters();

        // Update label based on name of spec and limits defined in spec
        ((TextView)view.findViewById(R.id.statistic_label)).setText(spec.toString() + stringLimits());

        return view;
    }

    /**
     * Checks specification in spec and updates the valueField input type accordingly. Does not
     * completely limit entries to specifications (does not enforce maximum, only enforces minimum
     * for minimum at 0)
     */
    private void setFieldParameters() {
        if (spec.valueIsInteger()) {
            if (spec.valueHasMinBound() && spec.getMinValue() > 0) {
                valueField.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
            else {
                valueField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
            }
        }
        else {
            if (spec.valueHasMinBound() && spec.getMinValue() > 0) {
                valueField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }
            else {
                valueField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED
                    | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStatisticFragmentInteractionListener
                ) {
            mListener = (OnStatisticFragmentInteractionListener
                    ) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStatisticFragmentInteractionListener" +
                    "");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * If value entered is valid, returns a ValueStatistic (cast as a Statistic).
     * @return The Statistic object created according to user entered data and the StatisticSpec that
     * defines the Statistic object.
     * @throws InsufficientFieldEntriesException If the value field is empty or the value is invalid,
     * (out of range), throws exception.
     */
    @Override
    public Statistic getStatistic() throws InsufficientFieldEntriesException {
        checkField();
        return new ValueStatistic(spec, Double.parseDouble(valueField.getText().toString()));
    }

    /**
     * Creates a string representation of the limits defined in spec (if any).
     * @return A short string defining the limits, empty if no limits. e.g. (5-19) for min 5, max 19.
     */
    private String stringLimits(){
        int decimalPlaces = spec.getDecimalPlaces();

        if (spec.valueHasMaxBound() && spec.valueHasMinBound())
            return String.format("(%."+decimalPlaces+"f-%."+decimalPlaces+"f)", spec.getMinValue(), spec.getMaxValue());
        else if (spec.valueHasMinBound())
            return String.format("(Min %."+decimalPlaces+"f)", spec.getMinValue());
        else if (spec.valueHasMaxBound())
            return String.format("(Max %."+decimalPlaces+"f)", spec.getMaxValue());
        else
            return "";
    }

    /**
     * Checks the valueField to see if there is a value that is valid. Throws an exception if field
     * is empty or value is beyond the limits as defined in spec.
     * @throws InsufficientFieldEntriesException If the value field is empty or the value is invalid,
     * (out of range), throws exception.
     */
    private void checkField() throws InsufficientFieldEntriesException {
        if (valueField.getText().toString().equals("")) {
            throw new InsufficientFieldEntriesException(
                    String.format("%s statistic needs a value.", spec.toString()));
        }
        Double value = Double.parseDouble(valueField.getText().toString());
        if (spec.valueHasMinBound() && value < spec.getMinValue()
                || spec.valueHasMaxBound() && value > spec.getMaxValue()) {
            throw new InsufficientFieldEntriesException(
                    String.format("%s: %f is not valid; must be %s", spec, value, stringLimits()));
        }
    }


}
