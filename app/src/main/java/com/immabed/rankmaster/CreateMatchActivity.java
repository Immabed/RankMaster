package com.immabed.rankmaster;

import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.immabed.rankmaster.rankings.Match;
import com.immabed.rankmaster.rankings.Player;
import com.immabed.rankmaster.rankings.PlayerAlreadyExistsException;
import com.immabed.rankmaster.rankings.RankTable;

import java.util.ArrayList;

public class CreateMatchActivity extends AppCompatActivity implements
        PlayerAndStatisticsFragment.OnFragmentInteractionListener, StatisticFragment.OnStatisticFragmentInteractionListener {

    public static final String TABLE_ARG = "com.immabed.rankmaster.CreateMatchActivity.table_arg";
    private ArrayList<PlayerAndStatisticsFragment> playerAndStatisticsFragments = new ArrayList<>();
    private ArrayList<Player> players;

    private RankTable rankTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);

        Intent intent = getIntent();
        rankTable = (RankTable)intent.getSerializableExtra(TABLE_ARG);
    }

    public void createPlayerAndStatistic (View view) {
        PlayerAndStatisticsFragment fragment = PlayerAndStatisticsFragment.newInstance(rankTable);
        getSupportFragmentManager().beginTransaction().add(R.id.player_statistic_fragment_container, fragment).commit();
        playerAndStatisticsFragments.add(fragment);
    }

    public void createMatch(View view) {
        players = new ArrayList<>();
        if (playerAndStatisticsFragments.size() == 0) {
            Context context = getApplicationContext();
            Toast.makeText(context, "A Match Requires At Least One Participant.", Toast.LENGTH_SHORT).show();
            return;
        }
        Match match = new Match();
        for (PlayerAndStatisticsFragment fragment : playerAndStatisticsFragments) {
            try {
                match.addPlayer(fragment.getPlayer(), fragment.getStatistics());
            }
            catch (InsufficientFieldEntriesException e) {
                Context context = getApplicationContext();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            catch (PlayerAlreadyExistsException e) {
                Context context = getApplicationContext();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        }
        rankTable.addMatch(match);

        RankTableIO.saveTable(this, rankTable);

        Intent intent = new Intent(this, RankTableActivity.class);
        intent.putExtra(RankTableActivity.PASS_RANK, rankTable);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
