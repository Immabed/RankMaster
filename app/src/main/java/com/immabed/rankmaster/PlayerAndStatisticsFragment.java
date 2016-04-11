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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.immabed.rankmaster.rankings.Match;
import com.immabed.rankmaster.rankings.Player;
import com.immabed.rankmaster.rankings.RankTable;
import com.immabed.rankmaster.rankings.Statistic;
import com.immabed.rankmaster.rankings.StatisticSpec;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerAndStatisticsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerAndStatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerAndStatisticsFragment extends Fragment {

    public static final String TABLE_ARG = "com.immabed.rankmaster.PlayerAndStatisticsFragment.table_arg";


    private OnFragmentInteractionListener mListener;

    private RankTable rankTable;
    private ArrayList<StatisticFragment> fragments = new ArrayList<>();
    private Spinner playerSpinner;
    private Player newPlayer;
    private TextView newPlayerField;
    private LinearLayout newPlayerLayout;

    public PlayerAndStatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayerAndStatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerAndStatisticsFragment newInstance(RankTable rankTable) {
        PlayerAndStatisticsFragment fragment = new PlayerAndStatisticsFragment();
        Bundle args = new Bundle();
        args.putSerializable(TABLE_ARG, rankTable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rankTable = (RankTable)getArguments().getSerializable(TABLE_ARG);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_and_statistics, container, false);

        newPlayerField = (TextView)view.findViewById(R.id.new_player_field);
        newPlayerLayout = (LinearLayout)view.findViewById(R.id.new_player_layout);
        playerSpinner = (Spinner)view.findViewById(R.id.player_name);
        populatePlayerSpinner();

        playerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (((Player) playerSpinner.getSelectedItem()).getId().equals(newPlayer.getId())) {
                    newPlayerLayout.setVisibility(View.VISIBLE);
                    newPlayerLayout.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                } else {
                    newPlayerLayout.setVisibility(View.INVISIBLE);
                    newPlayerLayout.getLayoutParams().height = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        for (StatisticSpec spec : rankTable.getStatisticSpecs()) {
            if (spec != null) {
                try {
                    StatisticFragment fragment = StatisticFragment.newInstance(spec);
                    getChildFragmentManager().beginTransaction().add(R.id.statistic_fragment_container,
                            fragment).commit();
                    fragments.add(fragment);
                } catch (UnknownParameterObjectException e) {
                    System.out.println(e.getMessage());
                }
            }
        }


        return view;
    }

    private void populatePlayerSpinner() {
        Context context = getContext();

        List<Player> playersList = new ArrayList<>(Arrays.asList(rankTable.getPlayers()));
        newPlayer = new Player("New Player");
        playersList.add(newPlayer);

        ArrayAdapter<Player> adapterSpecs = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, playersList);
        adapterSpecs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerSpinner.setAdapter(adapterSpecs);
    }

    public Player getPlayer() throws InsufficientFieldEntriesException {
        Player player = (Player)playerSpinner.getSelectedItem();
        if (player.getId().equals(newPlayer.getId())) {
            if (newPlayerField.getText().toString().equals("")) {
                throw new InsufficientFieldEntriesException("Must enter a name or select an existing player.");
            }
            else {
                player.changeName(newPlayerField.getText().toString());
            }
        }
        return player;
    }

    public Statistic[] getStatistics() throws InsufficientFieldEntriesException {
        Statistic[] statistics = new Statistic[fragments.size()];
        for (int i = 0; i < fragments.size(); i++) {
            statistics[i] = fragments.get(i).getStatistic();
        }
        return statistics;
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
