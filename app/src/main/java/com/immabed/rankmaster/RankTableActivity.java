package com.immabed.rankmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.immabed.rankmaster.rankings.Player;
import com.immabed.rankmaster.rankings.RankTable;
import com.immabed.rankmaster.rankings.StatisticSpec;

/**
 * Activity for displaying a RankTable. Has table and button to create a new match.
 *
 * Modified from template.
 * @author Brady Coles
 */
public class RankTableActivity extends AppCompatActivity {

    /**
     * Identifier for passing/accessing the RankTable argument in the OnCreate/Intent Bundle
     */
    public static final String PASS_RANK = "com.immabed.rankmaster.RankTableActivity.PassRank";

    /**
     * The RankTable whose data is being displayed.
     */
    private RankTable rankTable;
    /**
     * The view that holds the rankTable data
     */
    private TableLayout rankTableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_table);

        // Populate members
        rankTableView = (TableLayout)findViewById(R.id.rank_table);
        Intent intent = getIntent();
        rankTable = (RankTable)intent.getSerializableExtra(PASS_RANK);

        // Fill rankTableView with data for display
        populateRows();
    }


    /**
     * Starts a CreateMatchActivity for the current RankTable. Used by click listener.
     * @param view The calling view object.
     */
    public void createMatch(View view) {
        Intent intent = new Intent(this, CreateMatchActivity.class);
        intent.putExtra(CreateMatchActivity.TABLE_ARG, rankTable);
        finish();
        startActivity(intent);
    }

    /**
     * Fills the rankTableView with rows of entries. Gets the Statistics and Players and displays them.
     */
    private void populateRows() {
        StatisticSpec[] specs = rankTable.getStatisticSpecs();

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        // First row with Table and Statistic Names //
        TableRow row = (TableRow)inflater.inflate(R.layout.rank_row_template, null);

        TextView tv = (TextView)inflater.inflate(R.layout.rank_name_cell_template, null);
        tv.setText(rankTable.toString());
        row.addView(tv);

        // Games Played column
        tv = (TextView)inflater.inflate(R.layout.rank_name_cell_template, null);
        tv.setText("GP");
        row.addView(tv);


        for (StatisticSpec spec : specs) {
            // User Statistic Column
            tv = (TextView)inflater.inflate(R.layout.rank_name_cell_template, null);
            tv.setText(spec.toString());
            row.addView(tv);
        }
        rankTableView.addView(row);

        // Rows for players. //
        for (Player player : rankTable.getPlayers()) {
            // Player Name
            row = (TableRow)inflater.inflate(R.layout.rank_row_template, null);
            tv = (TextView)inflater.inflate(R.layout.rank_name_cell_template, null);
            tv.setText(player.toString());
            row.addView(tv);

            // Games Played
            tv = (TextView)inflater.inflate(R.layout.rank_basic_cell_template, null);
            tv.setText(Integer.toString(rankTable.getGamesPlayed(player)));
            row.addView(tv);

            for (StatisticSpec spec : specs) {
                // User entered Statistics
                tv = (TextView)inflater.inflate(R.layout.rank_basic_cell_template, null);
                tv.setText(rankTable.getStatisticStringByPlayerBySpec(player, spec));
                row.addView(tv);
            }

            rankTableView.addView(row);
        }
    }
}
