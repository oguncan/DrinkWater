package com.ocktr.drinkwater.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.ocktr.drinkwater.R;

public class StatsActivity extends AppCompatActivity {
    Toolbar statsActivityToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        statsActivityToolbar = findViewById(R.id.statsActivityToolbar);
        setSupportActionBar(statsActivityToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.stats);
        statsActivityToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        statsActivityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0);
            }
        });
    }
}