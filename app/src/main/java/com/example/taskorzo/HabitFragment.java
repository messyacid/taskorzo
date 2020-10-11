package com.example.taskorzo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskorzo.data.HabitContract;
import com.example.taskorzo.data.HabitDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.labo.kaji.fragmentanimations.MoveAnimation;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.mateware.snacky.Snacky;

import static android.content.Context.MODE_PRIVATE;

public class HabitFragment extends Fragment implements AddHabitDialogFragment.OnHabitSelected {
    RecyclerView habitRecylerView;
    ArrayList<String> habitTitle = new ArrayList<>();
    ArrayList<String> habitDescription = new ArrayList<>();
    String newHabitTitle, newHabitDescription;
    Cursor cursor;
    HabitDbHelper dbHelper;
    habitRecylerAdapter recycleAdapter;
    FloatingActionButton addHabitButton;
    ContentValues values = new ContentValues();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View habitView =  inflater.inflate(R.layout.habit_fragment, container,  false);
        habitRecylerView = habitView.findViewById(R.id.habitRecylerView);
        addHabitButton = habitView.findViewById(R.id.addHabitButton);

        dbHelper = new HabitDbHelper(getContext());

        String[] projection = {HabitContract.COLUMN_ID, HabitContract.COLUMN_TITLE, HabitContract.COLUMN_DESC};
        cursor = getActivity().getApplicationContext().getContentResolver().query(HabitContract.CONTENT_URI, projection, null, null, null);

        recycleAdapter = new habitRecylerAdapter(getContext(), habitTitle, habitDescription);
        habitRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        habitRecylerView.setAdapter(recycleAdapter);

        try {
            int idColumnName = cursor.getColumnIndex(HabitContract.COLUMN_TITLE);
            int idColumnDesc = cursor.getColumnIndex(HabitContract.COLUMN_DESC);

            while (cursor.moveToNext()) {
                habitTitle.add(cursor.getString(idColumnName));
                habitDescription.add(cursor.getString(idColumnDesc));
            }

        } finally {
            cursor.close();
        }


        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHabitDialogFragment addHabitDialogFragment = new AddHabitDialogFragment();
                addHabitDialogFragment.setTargetFragment(HabitFragment.this, 1);
                addHabitDialogFragment.show(getFragmentManager(), "New Task Dialog");
            }
        });


        recycleAdapter.setOnItemClickListner(new habitRecylerAdapter.onItemClickListner() {
            @Override
            public void onClick(String itemClicked) {
                if(!itemClicked.isEmpty()) {
                    int position = habitTitle.indexOf(itemClicked);
                    Intent intent = new Intent(getContext(), individual_habit.class);
                    intent.putExtra("TitleTextHabit", habitTitle.get(0));
                    intent.putExtra("DescriptionTextHabit", habitDescription.get(0));
                    startActivityForResult(intent, 111);

                }
            }
        });

        return habitView;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return MoveAnimation.create(MoveAnimation.UP, enter, 500);
        } else {
            return MoveAnimation.create(MoveAnimation.DOWN, enter, 0);
        }
    }

    @Override
    public void sendHabit(String[] newHabit) {

        if (habitTitle.size() < 1) {


            newHabitTitle = newHabit[0];
            newHabitDescription = newHabit[1];

            if (!newHabitTitle.isEmpty()) {
                habitTitle.add(newHabitTitle);
                habitDescription.add(newHabitDescription);
                recycleAdapter.notifyDataSetChanged();

                values.put(HabitContract.COLUMN_TITLE, newHabitTitle);
                values.put(HabitContract.COLUMN_DESC, newHabitDescription);

                getActivity().getContentResolver().insert(HabitContract.CONTENT_URI, values);

                SharedPreferences streakPref = getActivity().getSharedPreferences("StreakFunction", MODE_PRIVATE);
                SharedPreferences.Editor editor = streakPref.edit();

                Date currentDate = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
                String startDate = df.format(currentDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DATE, 21);
                String lastDate = df.format(calendar.getTime());

                calendar.setTime(currentDate);
                String lastDateClicked = df.format(calendar.getTime());


                Log.i("DATE", startDate + "  - -  " + lastDate + " - " + lastDateClicked);

                editor.putString("startDate", startDate);
                editor.putString("lastDate", lastDate);
                editor.putInt("knownProgress", 0);
                editor.putString("lastDateClicked", lastDateClicked);
                editor.commit();
                Log.i("DATE", "Commited I guess");

            }
        } else {
            Toast.makeText(getContext(), "You can make only one habit at once at your level", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        Log.i("Works", requestCode + " " + resultCode);
        recycleAdapter.notifyDataSetChanged();

    }

}



