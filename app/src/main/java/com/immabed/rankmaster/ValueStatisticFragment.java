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
 */
public class ValueStatisticFragment extends StatisticFragment {

    private ValueStatisticSpec spec;
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
            spec = (ValueStatisticSpec)getArguments().getSerializable(StatisticFragment.ARG_SPEC);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_value_statistic, container, false);

        valueField = (TextView)view.findViewById(R.id.statistic_value_field);
        setFieldParameters();

        ((TextView)view.findViewById(R.id.statistic_label)).setText(spec.toString() + stringLimits());

        return view;
    }

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
            valueField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL
                    | InputType.TYPE_NUMBER_FLAG_SIGNED);
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

    @Override
    public Statistic getStatistic() throws InsufficientFieldEntriesException {
        checkField();
        return new ValueStatistic(spec, Double.parseDouble(valueField.getText().toString()));
    }

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
