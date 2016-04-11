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

public class RankTableActivity extends AppCompatActivity {

    public static final String PASS_RANK = "com.immabed.rankmaster.RankTableActivity.PassRank";

    RankTable rankTable;
    TableLayout rankTableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_table);

        rankTableView = (TableLayout)findViewById(R.id.rank_table);

        Intent intent = getIntent();
        rankTable = (RankTable)intent.getSerializableExtra(PASS_RANK);

        populateRows();
    }


    public void createMatch(View view) {
        Intent intent = new Intent(this, CreateMatchActivity.class);
        intent.putExtra(CreateMatchActivity.TABLE_ARG, rankTable);
        finish();
        startActivity(intent);
    }

    private void populateRows() {
        StatisticSpec[] specs = rankTable.getStatisticSpecs();

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);


        TableRow row = (TableRow)inflater.inflate(R.layout.rank_row_template, null);

        TextView tv = (TextView)inflater.inflate(R.layout.rank_name_cell_template, null);
        tv.setText(rankTable.toString());
        row.addView(tv);

        tv = (TextView)inflater.inflate(R.layout.rank_name_cell_template, null);
        tv.setText("GP");
        row.addView(tv);

        for (StatisticSpec spec : specs) {
            tv = (TextView)inflater.inflate(R.layout.rank_name_cell_template, null);
            tv.setText(spec.toString());
            row.addView(tv);
        }
        rankTableView.addView(row);


        for (Player player : rankTable.getPlayers()) {
            row = (TableRow)inflater.inflate(R.layout.rank_row_template, null);
            tv = (TextView)inflater.inflate(R.layout.rank_name_cell_template, null);
            tv.setText(player.toString());
            row.addView(tv);

            tv = (TextView)inflater.inflate(R.layout.rank_basic_cell_template, null);
            tv.setText(Integer.toString(rankTable.getGamesPlayed(player)));
            row.addView(tv);

            for (StatisticSpec spec : specs) {
                tv = (TextView)inflater.inflate(R.layout.rank_basic_cell_template, null);
                tv.setText(rankTable.getStatisticStringByPlayerBySpec(player, spec));
                row.addView(tv);
            }
            rankTableView.addView(row);
        }
    }
}
