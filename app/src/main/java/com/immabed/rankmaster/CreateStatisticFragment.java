package com.immabed.rankmaster;

import android.support.v4.app.Fragment;

import com.immabed.rankmaster.rankings.StatisticSpec;

/**
 * Created by immabed on 2016-04-07.
 */
public abstract class CreateStatisticFragment extends Fragment {

    public abstract StatisticSpec createStatisticSpec()
        throws InsufficientFieldEntriesException;
}
