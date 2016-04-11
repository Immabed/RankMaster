package com.immabed.rankmaster;

import android.net.Uri;

import com.immabed.rankmaster.rankings.compare.Ranking;

/**
 * Created by Immabed on 2016-04-09.
 */
public abstract class CreateRankingFragment extends android.support.v4.app.Fragment {
    public abstract Ranking getRanking()
            throws InsufficientFieldEntriesException;

    public abstract void highlightInsufficientFields();
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
    public interface OnRankingFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRankingFragmentInteraction(Uri uri);
    }
}
