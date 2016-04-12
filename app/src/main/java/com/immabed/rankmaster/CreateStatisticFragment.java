package com.immabed.rankmaster;

import android.net.Uri;
import android.support.v4.app.Fragment;

import com.immabed.rankmaster.rankings.StatisticSpec;

/**
 * Abstract parent of fragments that create StatisticSpec objects.
 * @author Brady Coles
 */
public abstract class CreateStatisticFragment extends Fragment {

    /**
     * Returns the StatisticSpec object that has been created, or throws an exception describing
     * invalid or insufficient user entries.
     * @return The StatisticSpec object this fragment creates.
     * @throws InsufficientFieldEntriesException If the user has entered insufficient or invalid
     * data such that the Ranking object cannot be creates, exception is thrown.
     */
    public abstract StatisticSpec createStatisticSpec()
        throws InsufficientFieldEntriesException;

    /**
     * Highlights fields that are not entered properly to alert user.
     */
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
    public interface OnStatisticFragmentInteractionListener {
        // TODO: Update argument type and name
        void onStatisticFragmentInteraction(Uri uri);
    }
}
