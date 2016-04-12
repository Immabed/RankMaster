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

/**
 * Main activity and launcher activity. Holds a list of saved RankTables as well as a button to
 * create a new RankTable.
 * Modified from prebuilt template.
 * @author Brady Coles
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    protected void onStart() {
        super.onStart();
        /*
        Force a reload of the list of tables anytime the activity is resumed so that the list represents
        actual tables.
         */
        reloadTables();
    }

    /**
     * Clears the list of RankTable text buttons and reloads based on what is retrieved from storage.
     * Use whenever the list of tables needs to be loaded or updated, such as on the creation of the
     * activity or when a table has been added/removed/changed in storage.
     */
    public void reloadTables() {
        // Initialize the inflater and get the layout that holds the list of tables.
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout)findViewById(R.id.table_list_layout);
        //Ensure prompt text is invisible to start
        findViewById(R.id.no_table_text).setVisibility(View.VISIBLE);
        //Remove any residual RankTable buttons
        layout.removeAllViews();

        // Create and populate and add layouts for every RankTable loaded from storage.
        RankTable[] tables = RankTableIO.getTables(this);
        for (final RankTable table : tables) {
            LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.rank_table_selection_template, null);
            TextView tv = (TextView)ll.findViewById(R.id.goto_table);
            TextView dl = (TextView)ll.findViewById(R.id.delete);
            tv.setText(table.toString());
            // Set listeners for handling click events
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
        if (tables.length == 0) {
            // Display prompt text because no Tables exist
            findViewById(R.id.no_table_text).setVisibility(View.VISIBLE);
        }
    }


    /**
     * Starts a new CreateRankingActivity.
     * @param view The calling view object, used to satisfy the listener.
     */
    public void createNewTable(View view) {
        startActivity(new Intent(this, CreateRankingActivity.class));
    }




}
