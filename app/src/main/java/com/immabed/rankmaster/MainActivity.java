package com.immabed.rankmaster;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.immabed.rankmaster.rankings.RankTable;

import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.immabed.rankmaster.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //RankTableIO.clearTables(this);
        reloadTables();
    }

    @Override
    protected void onStart() {
        super.onStart();
        reloadTables();
    }

    public void reloadTables() {
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout)findViewById(R.id.table_list_layout);

        layout.removeAllViews();

        RankTable[] tables = RankTableIO.getTables(this);
        for (final RankTable table : tables) {
            LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.rank_table_selection_template, null);
            TextView tv = (TextView)ll.findViewById(R.id.goto_table);
            TextView dl = (TextView)ll.findViewById(R.id.delete);
            tv.setText(table.toString());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), RankTableActivity.class);
                    intent.putExtra(RankTableActivity.PASS_RANK, table);
                    v.getContext().startActivity(intent);
                }
            });
            dl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RankTableIO.removeTable(v.getContext(), table);
                    ((MainActivity)v.getContext()).reloadTables();
                }
            });
            layout.addView(ll);
        }
    }


    public void createNewTable(View view) {
        startActivity(new Intent(this, CreateRankingActivity.class));
    }




}
