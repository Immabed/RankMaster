package com.immabed.rankmaster;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.immabed.rankmaster.rankings.StatisticSpec;
import com.immabed.rankmaster.rankings.ValueStatisticSpec;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.immabed.rankmaster.CreateStatisticFragment.OnStatisticFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateValueStatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateValueStatisticFragment extends CreateStatisticFragment
        implements CompoundButton.OnCheckedChangeListener {

    /**
     * check box view that determines whether values must be integers or not.
     */
    private CompoundButton isDecimalBtn;
    /**
     * Text field that holds minimum value
     */
    private TextView min;
    /**
     * Text field that holds maximum value
     */
    private TextView max;
    /**
     * Text field that holds name of statistic
     */
    private TextView name;
    /**
     * Text field that holds the number of decimal places (if not limited to integers)
     */
    private TextView decimalPlaces;
    /**
     * Layout to show/hide if input can/cannot be fractional.
     */
    private LinearLayout decimalLayout;

    private OnStatisticFragmentInteractionListener mListener;

    public CreateValueStatisticFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateValueStatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateValueStatisticFragment newInstance() {
        CreateValueStatisticFragment fragment = new CreateValueStatisticFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_value_statistic, container, false);

        // Populate members
        name = (TextView) view.findViewById(R.id.nameText);
        isDecimalBtn = (CompoundButton) view.findViewById(R.id.decimalCheckBox);
        min = (TextView) view.findViewById(R.id.minValue);
        max = (TextView) view.findViewById(R.id.maxValue);
        decimalPlaces = (TextView) view.findViewById(R.id.decimalValue);
        decimalLayout = (LinearLayout) view.findViewById(R.id.decimalLayout);

        // Set defaults
        isDecimalBtn.setOnCheckedChangeListener(this);
        isDecimalBtn.setChecked(true);
        min.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        max.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        decimalLayout.setVisibility(View.INVISIBLE);
        decimalLayout.getLayoutParams().height = 0;
        decimalLayout.requestLayout(); //update layout
        decimalPlaces.setInputType(InputType.TYPE_CLASS_NUMBER);


        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStatisticFragmentInteractionListener) {
            mListener = (OnStatisticFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * When isDecimalButton is checked, update view accordingly.
     * @param buttonView Calling CoumpoundView
     * @param isChecked State of check box
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == isDecimalBtn.getId()) {
            if (isChecked) {
                makeWholeNumbers();
            }
            else {
                makeDecimalNumbers();
            }
        }
    }

    /**
     * Sets the fragment to whole number mode. Makes fields integer only and hides decimalLayout.
     */
    private void makeWholeNumbers() {
        min.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        if (!min.getText().toString().equals(""))
            min.setText(Integer.toString((int)Double.parseDouble(min.getText().toString())));
        max.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        if (!max.getText().toString().equals(""))
            max.setText(Integer.toString((int)Double.parseDouble(max.getText().toString())));
        decimalLayout.setVisibility(View.INVISIBLE);
        decimalLayout.getLayoutParams().height = 0;
        decimalLayout.requestLayout();
    }

    /**
     * Sets the fragment to fractional number mode. Makes fields allow integers and shows decimalLayout.
     */
    private void makeDecimalNumbers() {
        min.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL
                | InputType.TYPE_NUMBER_FLAG_SIGNED);
        max.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL
                | InputType.TYPE_NUMBER_FLAG_SIGNED);
        decimalLayout.setVisibility(View.VISIBLE);
        decimalLayout.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        decimalLayout.requestLayout();
    }

    /**
     * Creates ValueStatisticSpec if possible from user entered data or throws exception if any
     * fields are insufficient or invalid.
     * @return ValueStatisticSpec as StatisticSpec created by this fragment.
     * @throws InsufficientFieldEntriesException If any fields are insufficient or invalid, throws
     * exception.
     */
    @Override
    public StatisticSpec createStatisticSpec() throws InsufficientFieldEntriesException {
        if (name.getText().toString().equals("")) {
            throw new InsufficientFieldEntriesException("Cannot create statistic without name");
        }
        if (!isDecimalBtn.isChecked() && decimalPlaces.getText().toString().equals("")) {
            decimalPlaces.setText("0");
        }

        boolean hasMax = !this.max.getText().toString().equals("");
        boolean hasMin = !this.min.getText().toString().equals("");
        double max = 0;
        double min = 0;

        if (hasMax) {
            max = Double.parseDouble(this.max.getText().toString());
        }
        if (hasMin) {
            min = Double.parseDouble(this.min.getText().toString());
        }

        if (hasMax && hasMin && max < min) {
            // Check to make sure max is greater than or equal to min
            throw new InsufficientFieldEntriesException("Maximum cannot be less than minimum.");
        }

        boolean isIntegerOnly = isDecimalBtn.isChecked();
        int decimalPlaces = 0;

        if (!isIntegerOnly) {
            // Ensure max and min are whole numbers
            decimalPlaces = Integer.parseInt(this.decimalPlaces.getText().toString());
            max = (int)max;
            min = (int)min;
        }

        String name = this.name.getText().toString();

        return new ValueStatisticSpec(name, isIntegerOnly, hasMax, hasMin, max, min, decimalPlaces);
    }

    /**
     * Highlight insufficient fields.
     */
    @Override
    public void highlightInsufficientFields() {
        //TODO
    }


}
