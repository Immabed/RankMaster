package com.immabed.rankmaster;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.immabed.rankmaster.rankings.Statistic;
import com.immabed.rankmaster.rankings.StatisticSpec;
import com.immabed.rankmaster.rankings.ValueStatisticSpec;

/**
 * Created by Immabed on 2016-04-09.
 */
public abstract class StatisticFragment extends Fragment {

    /**
     * Identifier for passing/accessing the RankTable argument in the OnCreate/newInstance Bundle
     */
    public static final String ARG_SPEC = "com.immabed.rankmaster.StatisticFragment.spec_arg";


    /**
     * Returns the Statistic object that the fragment is creating.
     * @return A statistic object created from the user entered data.
     * @throws InsufficientFieldEntriesException If fields are empty or incorrect that need to be
     * changed before a Statistic object can be created, exception is thrown.
     */
    public abstract Statistic getStatistic() throws InsufficientFieldEntriesException;


    /**
     * Used to create a StatisticFragment child depending on the class of the spec argument.
     * @param spec The StatisticSpec object that defines the Statistic to be created.
     * @return A fragment that is a child of StatisticFragment based on the Class of spec.
     * @throws UnknownParameterObjectException If spec is not recognized, exception thrown.
     */
    public static StatisticFragment newInstance(StatisticSpec spec)
            throws UnknownParameterObjectException {
        StatisticFragment fragment;
        // Check what type of Statistic needs to be created. Needs to be updated when new Statistic
        // types are added.
        if (spec instanceof ValueStatisticSpec) {
            // Statistic will be a ValueStatistic
            fragment = new ValueStatisticFragment();
        }
        else {
            throw new UnknownParameterObjectException(
                    String.format("Unknown StatisticSpec: %s", spec.getClass().toString()));
        }

        Bundle args = new Bundle();
        args.putSerializable(ARG_SPEC, spec);
        fragment.setArguments(args);
        return fragment;
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
    public interface OnStatisticFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
