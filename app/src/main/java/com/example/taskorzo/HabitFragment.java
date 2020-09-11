package com.example.taskorzo;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskorzo.data.HabitContract;
import com.example.taskorzo.data.HabitDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.labo.kaji.fragmentanimations.MoveAnimation;


import java.util.ArrayList;

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


        newHabitTitle = newHabit[0];
        newHabitDescription = newHabit[1];

        if(!newHabitTitle.isEmpty()) {
            habitTitle.add(newHabitTitle);
            habitDescription.add(newHabitDescription);
            recycleAdapter.notifyDataSetChanged();

            values.put(HabitContract.COLUMN_TITLE, newHabitTitle);
            values.put(HabitContract.COLUMN_DESC, newHabitDescription);

            getActivity().getContentResolver().insert(HabitContract.CONTENT_URI, values);

        }
    }
}
