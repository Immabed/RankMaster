package com.immabed.rankmaster;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.immabed.rankmaster.rankings.RankTable;
import com.immabed.rankmaster.rankings.StatisticSpec;
import com.immabed.rankmaster.rankings.compare.Ranking;
import com.immabed.rankmaster.rankings.compare.SumRanking;

/**
 * Fragment to create a SumRanking object.
 * Activities that contain this fragment must implement the
 * {@link CreateSumRankingFragment.OnRankingFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateSumRankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Modified from template.
 * @author Brady Coles
 */
public class CreateSumRankingFragment extends CreateRankingFragment {
    /**
     * Identifier used by onCreate/newInstance as key for rankTable object
     */
    private static final String RANK_TABLE_KEY = "com.immabed.rankmaster.CreateSumRankingFragment.rank_table_key";
;

    private OnRankingFragmentInteractionListener mListener;

    /**
     * The spinner view that holds the type selection for the SumRanking object
     */
    private Spinner typeSpinner;
    /**
     * The spinner view that holds the possible StatisticSpec objects that can be used
     */
    private Spinner statisticSpinner;
    /**
     * The check box view that determines whether the ranks are determined per game or total
     */
    private CompoundButton perGameCheckBox;
    /**
     * The text field that contains the proximity value (if type is proximity)
     */
    private TextView proximityField;
    /**
     * The layout to hide/show if type is proximity
     */
    private LinearLayout proximityLayout;

    /**
     * The rankTable that is being used.
     */
    private RankTable rankTable;

    public CreateSumRankingFragment() {
        //Necessary Empty Constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param rankTable A rank table with some statisticSpecs
     * @return A new instance of fragment CreateSumRankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateSumRankingFragment newInstance(RankTable rankTable) {
        CreateSumRankingFragment fragment = new CreateSumRankingFragment();
        Bundle args = new Bundle();
        args.putSerializable(RANK_TABLE_KEY, rankTable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Populate member.
            rankTable = (RankTable) getArguments().getSerializable(RANK_TABLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_sum_ranking, container, false);

        //Populate members.
        typeSpinner = (Spinner) view.findViewById(R.id.ranking_type);
        statisticSpinner = (Spinner) view.findViewById(R.id.statistic_selector);
        proximityLayout = (LinearLayout) view.findViewById(R.id.proximity_layout);
        proximityField = (TextView) view.findViewById(R.id.proximity_field);
        perGameCheckBox = (CompoundButton) view.findViewById(R.id.per_game_checkbox);

        // Default set per game.
        perGameCheckBox.setChecked(true);

        Context context = getActivity();

        //Array Types
        ArrayAdapter<CharSequence> adapterTypes = ArrayAdapter.createFromResource(context,
                R.array.sum_ranking_type, android.R.layout.simple_spinner_item);
        adapterTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapterTypes);

        //Array Statistics
        ArrayAdapter<StatisticSpec> adapterSpecs = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, rankTable.getStatisticSpecs());
        adapterSpecs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statisticSpinner.setAdapter(adapterSpecs);


        // Set selection listener to show/hide proximityLayout
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (((String) typeSpinner.getSelectedItem()).equals(getResources().getString(R.string.sum_proximity))) {
                    proximityLayout.setVisibility(View.VISIBLE);
                    proximityLayout.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                } else {
                    proximityLayout.setVisibility(View.INVISIBLE);
                    proximityLayout.getLayoutParams().height = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRankingFragmentInteractionListener) {
            mListener = (OnRankingFragmentInteractionListener) context;
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
     * Returns the SumRanking object created, as a Ranking object, if possible, else throw exception.
     * @return The SumRanking object that has been created.
     * @throws InsufficientFieldEntriesException If fields are not entered sufficiently, throws exception.
     */
    @Override
    public Ranking getRanking() throws InsufficientFieldEntriesException {
        if ((typeSpinner.getSelectedItem()).equals(getString(R.string.sum_proximity)) && proximityField.getText().toString().equals("")) {
            throw new InsufficientFieldEntriesException("Must specify a proximity value if using proximity type.");
        }

        StatisticSpec spec = (StatisticSpec)statisticSpinner.getSelectedItem();

        SumRanking.RankCriteria criteria;
        String typeString = (String)typeSpinner.getSelectedItem();
        if (typeString.equals(getResources().getString(R.string.sum_highest)))
            criteria = SumRanking.RankCriteria.MAXIMUM;
        else if (typeString.equals(getResources().getString(R.string.sum_lowest)))
            criteria = SumRanking.RankCriteria.MINIMUM;
        else if (typeString.equals(getResources().getString(R.string.sum_proximity)))
            criteria = SumRanking.RankCriteria.PROXIMITY;
        else
            throw new InsufficientFieldEntriesException("Type " + typeString + " is unknown.");

        SumRanking.GamesPlayedComparison comparison;
        if (perGameCheckBox.isChecked())
            comparison = SumRanking.GamesPlayedComparison.AVERAGE_PER_GAME;
        else
            comparison = SumRanking.GamesPlayedComparison.OVERALL_TOTAL;

        return new SumRanking(spec, rankTable, criteria, 0, comparison);
    }

    @Override
    public void highlightInsufficientFields() {
        //TODO
    }


}
