package com.example.taskorzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;

public class individual_habit extends AppCompatActivity {

    TextView habitTitle, habitDescription;
    ArcProgress progressbar;
    Button completedToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_habit);

        habitTitle = findViewById(R.id.titleText);
        habitDescription = findViewById(R.id.descriptionText);
        completedToday = findViewById(R.id.addEntryHabit);
        progressbar = findViewById(R.id.arc_progress);

        Intent intent = getIntent();
        String title = intent.getStringExtra("TitleTextHabit");
        String description = intent.getStringExtra("DescriptionTextHabit");

        habitTitle.setText(title);
        habitDescription.setText(description);


        completedToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progressbarCurrent = progressbar.getProgress();
                int newProgressBarStatus = progressbarCurrent + 1;
                progressbar.setProgress(newProgressBarStatus);
            }
        });

    }
}