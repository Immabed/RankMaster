package com.immabed.rankmaster;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.immabed.rankmaster.rankings.RankTable;
import com.immabed.rankmaster.rankings.StatisticSpec;
import com.immabed.rankmaster.rankings.compare.Ranking;
import com.immabed.rankmaster.rankings.compare.RankingDoesNotShareRankTableException;

import java.util.ArrayList;

public class CreateRankingActivity extends FragmentActivity
        implements CreateStatisticFragment.OnStatisticFragmentInteractionListener,
            CreateRankingFragment.OnRankingFragmentInteractionListener {

    private ArrayList<CreateStatisticFragment> statisticFragments;
    private ArrayList<CreateRankingFragment> rankingFragments;
    RankTable rankTable;

    private ScrollView statisticView;
    private ScrollView rankingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ranking);

        statisticFragments = new ArrayList<>();
        rankingFragments = new ArrayList<>();

        statisticView = (ScrollView) findViewById(R.id.statistic_scroll_view);
        rankingView = (ScrollView) findViewById(R.id.ranking_scroll_view);

        rankingView.setVisibility(View.INVISIBLE);
        findViewById(R.id.create_rank_table).setVisibility(View.INVISIBLE);
    }


    public void addValueStatistic(View view) {
        CreateStatisticFragment fragment = CreateValueStatisticFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment).commit();
        statisticFragments.add(fragment);
    }

    public void addRanking(View view) {
        CreateRankingFragment fragment = CreateSumRankingFragment.newInstance(rankTable);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ranking_fragment_container, fragment).commit();
        rankingFragments.add(fragment);
    }


    public void createRanking(View view) {
        ArrayList<Ranking> rankings = new ArrayList<>();
        if (rankingFragments.size() == 0) {
            Context context = getApplicationContext();
            Toast.makeText(context, "At least one ranking rule is required.", Toast.LENGTH_SHORT).show();
        }
        for (CreateRankingFragment fragment: rankingFragments) {
            try {
                rankings.add(fragment.getRanking());
            }
            catch (InsufficientFieldEntriesException e) {
                Context context = getApplicationContext();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                for (CreateRankingFragment f : rankingFragments)
                    f.highlightInsufficientFields();
                return;
            }
        }

        for (Ranking ranking : rankings) {
            try {
                rankTable.addRanking(ranking);
            } catch (RankingDoesNotShareRankTableException e) {
                System.out.println(e.getMessage());
            }
        }

        RankTableIO.saveTable(this, rankTable);

        Intent intent = new Intent(this, RankTableActivity.class);
        intent.putExtra(RankTableActivity.PASS_RANK, rankTable);
        startActivity(intent);
    }

    public void addRankingRules(View view) {
        ArrayList<StatisticSpec> statisticSpecs = new ArrayList<>();
        for (CreateStatisticFragment fragment : statisticFragments) {
            try {
                statisticSpecs.add(fragment.createStatisticSpec());
            }
            catch (InsufficientFieldEntriesException e) {
                Context context = getApplicationContext();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                for (CreateStatisticFragment f : statisticFragments)
                    f.highlightInsufficientFields();
                return;
            }
        }
        TextView rankName = (TextView)findViewById(R.id.rank_table_name);
        if (rankName.getText().toString().equals("")) {
            Context context = getApplicationContext();
            Toast.makeText(context, "Please name the rank table.", Toast.LENGTH_SHORT).show();
            return;
        }

        rankTable = new RankTable(rankName.getText().toString(), statisticSpecs.toArray(new StatisticSpec[statisticSpecs.size()]));

        statisticView.setVisibility(View.INVISIBLE);
        rankingView.setVisibility(View.VISIBLE);
        findViewById(R.id.add_ranking_rules).setVisibility(View.INVISIBLE);
        findViewById(R.id.create_rank_table).setVisibility(View.VISIBLE);
    }


    @Override
    public void onStatisticFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRankingFragmentInteraction(Uri uri) {

    }
}
