package com.immabed.rankmaster;

import android.app.Fragment;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class CreateRankingActivity extends FragmentActivity
        implements CreateValueStatisticFragment.OnFragmentInteractionListener{

    private ArrayList<CreateStatisticFragment> statisticFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ranking);

        statisticFragments = new ArrayList<>();


    }


    public void addValueStatistic(View view) {
        CreateStatisticFragment fragment = new CreateValueStatisticFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment).commit();
        statisticFragments.add(fragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
