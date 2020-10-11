package com.example.taskorzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.taskorzo.data.HabitContract;
import com.example.taskorzo.data.HabitDbHelper;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.mateware.snacky.Snacky;

import static java.lang.Boolean.FALSE;


public class individual_habit extends AppCompatActivity {

    TextView habitTitle, habitDescription;
    ArcProgress progressbar;
    Button completedToday;
    HabitDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_habit);

        habitTitle = findViewById(R.id.titleText);
        habitDescription = findViewById(R.id.descriptionText);
        completedToday = findViewById(R.id.addEntryHabit);
        progressbar = findViewById(R.id.arc_progress);

        final Intent intent = getIntent();
        final String title = intent.getStringExtra("TitleTextHabit");
        final String description = intent.getStringExtra("DescriptionTextHabit");

        habitTitle.setText(title);
        habitDescription.setText(description);
        dbHelper = new HabitDbHelper(getApplicationContext());

        progressbar.setProgress(getKnownProgress());

        if (getLastDateClicked().equals(getCurrentDate())) {
            if (getCurrentDate().equals(getStartDate())) {
                completedToday.setText("Starts Tomorrow");
                completedToday.setTextColor(Color.BLACK);
                completedToday.setBackgroundColor(Color.GRAY);
                completedToday.setEnabled(FALSE);
            } else {
                completedToday.setText("Done Today");
                completedToday.setTextColor(Color.BLACK);
                completedToday.setBackgroundColor(Color.GRAY);
                completedToday.setEnabled(FALSE);
            }

        }


        completedToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDate = getCurrentDate();
                String lastDateClicked = getLastDateClicked();

                if (lastDateClicked.equals(currentDate)) {

                    if (currentDate.equals(getStartDate())) {

                        completedToday.setText("Starts Tomorrow");
                        completedToday.setTextColor(Color.BLACK);
                        completedToday.setBackgroundColor(Color.GRAY);
                        completedToday.setEnabled(FALSE);
                    } else {

                        completedToday.setText("Done Today");
                        completedToday.setTextColor(Color.BLACK);
                        completedToday.setBackgroundColor(Color.GRAY);
                        completedToday.setEnabled(FALSE);
                    }
                } else if (progressbar.getProgress() == 21) {
                    Snacky.builder()
                            .setActivity(individual_habit.this)
                            .setText("You created a new Habit you will be able to add another Habit Now")
                            .setDuration(Snacky.LENGTH_LONG)
                            .success()
                            .show();
                    String selection = HabitContract.COLUMN_TITLE + "=?";
                    String selectionArgs = title;
                    getContentResolver().delete(HabitContract.CONTENT_URI, selection, new String[]{selectionArgs});
                    Intent intent = getIntent().putExtra("streakBrokenTitle",title);
                    setResult(111,intent);

                } else if (getStreakBreak().equals(getLastDateClicked())) {

                    Snacky.builder()
                            .setView(v)
                            .setText("You broke the 21 day streak this habit will be deleted shortly")
                            .setDuration(Snacky.LENGTH_INDEFINITE)
                            .setActionText(android.R.string.ok)
                            .error()
                            .show();

                    String selection = HabitContract.COLUMN_TITLE + "=?";
                    String selectionArgs = title;

                    getContentResolver().delete(HabitContract.CONTENT_URI, selection, new String[]{selectionArgs});

                    Intent intent = getIntent().putExtra("streakBrokenTitle",title);
                    setResult(111,intent);


                } else {
                    setknownProgress(getKnownProgress()+1);
                    setLastDateClicked();
                    progressbar.setProgress(getKnownProgress());
                    completedToday.setText("Done already");
                    completedToday.setTextColor(Color.BLACK);
                    completedToday.setBackgroundColor(Color.GRAY);
                    completedToday.setEnabled(FALSE);
                    Log.i("blyat", "else" + lastDateClicked + " - " + currentDate);
                }


            }
        });

    }

    public String getLastDateClicked() {
        SharedPreferences streakPref = getSharedPreferences("StreakFunction", MODE_PRIVATE);
        return streakPref.getString("lastDateClicked", "Blyat");
    }

    public String getStartDate() {
        SharedPreferences streakPref = getSharedPreferences("StreakFunction", MODE_PRIVATE);
        return streakPref.getString("startDate", "Blyat");
    }

    public int getKnownProgress() {
        SharedPreferences streakPref = getSharedPreferences("StreakFunction", MODE_PRIVATE);
        return  streakPref.getInt("knownProgress", 0);
    }

    public String getLastDate() {
        SharedPreferences streakPref = getSharedPreferences("StreakFunction", MODE_PRIVATE);
        return streakPref.getString("lastDate", "Blyat");
    }

    public String getCurrentDate() {
        Date rightNow = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        return df.format(rightNow);
    }

    public String getStreakBreak() {
        Date rightNow = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(rightNow);
        calendar.add(Calendar.DATE, -2);
        return df.format(calendar.getTime());
    }

    public void setknownProgress(int newProgress) {
        SharedPreferences streakPref = getSharedPreferences("StreakFunction", MODE_PRIVATE);
        SharedPreferences.Editor editor = streakPref.edit();
        editor.putInt("knownProgress", newProgress);
        editor.commit();
    }

    public void setLastDateClicked() {
        SharedPreferences streakPref = getSharedPreferences("StreakFunction", MODE_PRIVATE);
        SharedPreferences.Editor editor = streakPref.edit();
        editor.putString("lastDateClicked", getCurrentDate());
        editor.commit();
    }


}
