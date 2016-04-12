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

/**
 * Activity to create a RankTable.
 *
 * Modified from template.
 * @author Brady Coles
 */
public class CreateRankingActivity extends FragmentActivity
        implements CreateStatisticFragment.OnStatisticFragmentInteractionListener,
            CreateRankingFragment.OnRankingFragmentInteractionListener {

    /**
     * List of CreateStatisticFragments used to create StatisticSpec objects
     */
    private ArrayList<CreateStatisticFragment> statisticFragments;
    /**
     * List of CreateRankingFragments used to create Ranking objects
     */
    private ArrayList<CreateRankingFragment> rankingFragments;
    /**
     * RankTable object that is created.
     */
    private RankTable rankTable;

    /**
     * View containing CreateValueStatisticFragments (and associated views)
     */
    private ScrollView statisticView;
    /**
     * View containing CreateRankingFragments (and associated views)
     */
    private ScrollView rankingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ranking);

        // Initialize ArrayLists
        statisticFragments = new ArrayList<>();
        rankingFragments = new ArrayList<>();

        //Populate members
        statisticView = (ScrollView) findViewById(R.id.statistic_scroll_view);
        rankingView = (ScrollView) findViewById(R.id.ranking_scroll_view);

        // Set rankingView invisible because it is not needed yet.
        rankingView.setVisibility(View.INVISIBLE);
        findViewById(R.id.create_rank_table).setVisibility(View.INVISIBLE);
    }


    /**
     * Add a new CreateValueStatisticFragment.
     * @param view Calling view.
     */
    public void addValueStatistic(View view) {
        CreateStatisticFragment fragment = CreateValueStatisticFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment).commit();
        statisticFragments.add(fragment);
    }

    /**
     * Add a new CreateRankingFragment.
     * @param view Calling view.
     */
    public void addRanking(View view) {
        CreateRankingFragment fragment = CreateSumRankingFragment.newInstance(rankTable);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ranking_fragment_container, fragment).commit();
        rankingFragments.add(fragment);
    }


    public void createRanking(View view) {
        if (rankingFragments.size() == 0) {
            // Requires at least one ranking rule
            Context context = getApplicationContext();
            Toast.makeText(context, "At least one ranking rule is required.", Toast.LENGTH_SHORT).show();
        }

        for (CreateRankingFragment fragment: rankingFragments) {
            // create and add ranking objects
            try {
                rankTable.addRanking(fragment.getRanking());
            }
            catch (InsufficientFieldEntriesException e) {
                Context context = getApplicationContext();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                for (CreateRankingFragment f : rankingFragments)
                    f.highlightInsufficientFields();
                return;
            }
            catch (RankingDoesNotShareRankTableException e) {
                System.out.println(e.getMessage());
            }

        }

        RankTableIO.saveTable(this, rankTable);

        Intent intent = new Intent(this, RankTableActivity.class);
        intent.putExtra(RankTableActivity.PASS_RANK, rankTable);
        startActivity(intent);
    }

    /**
     * Create a RankTable using the name and CreateStatisticFragments, and move to displaying the
     * rankingView.
     * @param view Calling view.
     */
    public void addRankingRules(View view) {
        if (statisticFragments.size() == 0) {
            // Requires at least one statistic.
            Context context = getApplicationContext();
            Toast.makeText(context, "At least one statistic definition is required.", Toast.LENGTH_SHORT).show();
        }

        ArrayList<StatisticSpec> statisticSpecs = new ArrayList<>();
        for (CreateStatisticFragment fragment : statisticFragments) {
            // create StatisticSpec objects
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

        // Create RankTable
        rankTable = new RankTable(rankName.getText().toString(), statisticSpecs.toArray(new StatisticSpec[statisticSpecs.size()]));

        // Set visibility to allow users to enter Ranking Rules
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
