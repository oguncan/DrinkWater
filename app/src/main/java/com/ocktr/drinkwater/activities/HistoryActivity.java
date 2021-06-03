package com.ocktr.drinkwater.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.ocktr.drinkwater.R;

public class HistoryActivity extends AppCompatActivity {

    Toolbar historyActivityToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initViews();
    }

    private void initViews(){
        historyActivityToolbar = findViewById(R.id.historyActivityToolbar);
        setSupportActionBar(historyActivityToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.history);
        historyActivityToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        historyActivityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0);
            }
        });

    }
}