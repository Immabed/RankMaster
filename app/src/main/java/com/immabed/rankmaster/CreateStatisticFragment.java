package com.immabed.rankmaster;

import android.net.Uri;
import android.support.v4.app.Fragment;

import com.immabed.rankmaster.rankings.StatisticSpec;

/**
 * Created by immabed on 2016-04-07.
 */
public abstract class CreateStatisticFragment extends Fragment {

    public abstract StatisticSpec createStatisticSpec()
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
    public interface OnStatisticFragmentInteractionListener {
        // TODO: Update argument type and name
        void onStatisticFragmentInteraction(Uri uri);
    }
}
