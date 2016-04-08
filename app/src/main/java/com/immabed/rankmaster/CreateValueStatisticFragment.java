package com.immabed.rankmaster;

import android.app.ActionBar;
import android.content.Context;
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
 * {@link CreateValueStatisticFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateValueStatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateValueStatisticFragment extends CreateStatisticFragment
        implements CompoundButton.OnCheckedChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CompoundButton isDecimalBtn;
    private TextView min;
    private TextView max;
    private TextView name;
    private TextView decimalPlaces;
    private LinearLayout decimalLayout;

    private OnFragmentInteractionListener mListener;

    public CreateValueStatisticFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateValueStatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateValueStatisticFragment newInstance(String param1, String param2) {
        CreateValueStatisticFragment fragment = new CreateValueStatisticFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_value_statistic, container, false);

        name = (TextView) view.findViewById(R.id.nameText);

        isDecimalBtn = (CompoundButton) view.findViewById(R.id.decimalCheckBox);
        min = (TextView) view.findViewById(R.id.minValue);
        max = (TextView) view.findViewById(R.id.maxValue);
        decimalPlaces = (TextView) view.findViewById(R.id.decimalValue);
        decimalLayout = (LinearLayout) view.findViewById(R.id.decimalLayout);

        isDecimalBtn.setOnCheckedChangeListener(this);
        isDecimalBtn.setChecked(true);
        min.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        max.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        decimalLayout.setVisibility(View.INVISIBLE);
        decimalLayout.getLayoutParams().height = 0;
        decimalLayout.requestLayout();
        decimalPlaces.setInputType(InputType.TYPE_CLASS_NUMBER);


        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == isDecimalBtn.getId()) {
            if (isChecked) {
                min.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                max.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                decimalLayout.setVisibility(View.INVISIBLE);
                decimalLayout.getLayoutParams().height = 0;
                decimalLayout.requestLayout();

            }
            else {
                min.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                max.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                decimalLayout.setVisibility(View.VISIBLE);
                decimalLayout.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                decimalLayout.requestLayout();

            }
        }
    }

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

        boolean isIntegerOnly = isDecimalBtn.isChecked();
        int decimalPlaces = 0;

        if (isIntegerOnly) {
            decimalPlaces = Integer.parseInt(this.decimalPlaces.getText().toString());
            max = (int)max;
            min = (int)min;
        }

        String name = this.name.getText().toString();

        return new ValueStatisticSpec(name, isIntegerOnly, hasMax, hasMin, max, min, decimalPlaces);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
