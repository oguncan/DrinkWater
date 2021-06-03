package com.ocktr.drinkwater.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.ocktr.drinkwater.R;

public class RemindersActivity extends AppCompatActivity {
    Toolbar reminderActivityToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        reminderActivityToolbar = findViewById(R.id.reminderActivityToolbar);
        setSupportActionBar(reminderActivityToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.reminder);
        reminderActivityToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        reminderActivityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0);
            }
        });
    }
}