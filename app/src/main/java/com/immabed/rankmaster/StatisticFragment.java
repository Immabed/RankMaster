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

    public static final String ARG_SPEC = "com.immabed.rankmaster.StatisticFragment.spec_arg";


    public abstract Statistic getStatistic() throws InsufficientFieldEntriesException;


    public static StatisticFragment newInstance(StatisticSpec spec)
            throws UnknownParameterObjectException {
        StatisticFragment fragment;
        if (spec instanceof ValueStatisticSpec) {
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
