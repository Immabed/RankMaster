package com.immabed.rankmaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.immabed.rankmaster.rankings.Player;
import com.immabed.rankmaster.rankings.RankTable;
import com.immabed.rankmaster.rankings.StatisticSpec;

public class RankTableActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_table);

        TableLayout rankTableView = (TableLayout)findViewById(R.id.rank_table);

        RankTable rankTable = new RankTable("Table");
        StatisticSpec[] specs = rankTable.getStatisticSpecs();
        TextView tv = new TextView(this);
        tv.setText("");
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        row.addView(tv);

        tv = new TextView(this);
        tv.setText("GP");
        row.addView(tv);

        for (StatisticSpec spec : specs) {
            tv = new TextView(this);
            tv.setText(spec.toString());
            row.addView(tv);
        }
        rankTableView.addView(row);


        for (Player player : rankTable.getPlayers()) {
            row = new TableRow(this);
            lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            tv = new TextView(this);
            tv.setText(player.toString());
            row.addView(tv);

            tv = new TextView(this);
            tv.setText(Integer.toString(rankTable.getGamesPlayed(player)));
            row.addView(tv);

            for (StatisticSpec spec : specs) {
                tv = new TextView(this);
                tv.setText(rankTable.getStatisticStringByPlayerBySpec(player, spec));
                row.addView(tv);
            }
            rankTableView.addView(row);
        }
    }
}
