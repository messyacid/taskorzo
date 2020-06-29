package com.example.taskorzo;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskorzo.data.TaskContract;
import com.example.taskorzo.data.TaskDbHelper;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.labo.kaji.fragmentanimations.SidesAnimation;

import java.util.ArrayList;

public class HabitFragment extends Fragment {
    RecyclerView habitRecylerView;
    ArrayList<String> habitTitle = new ArrayList<>();
    ArrayList<String> habitDescription = new ArrayList<>();
    Cursor cursor;
    TaskDbHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View habitView =  inflater.inflate(R.layout.habit_fragment, container,  false);
        String[] projection = {TaskContract.COLUMN_ID, TaskContract.COLUMN_TITLE, TaskContract.COLUMN_DESC, TaskContract.COLUMN_MAKE_HABIT};
        String selection = TaskContract.COLUMN_MAKE_HABIT + "=?";
        String[] slectionArgs = new String[] {String.valueOf(TaskContract.MAKE_HABIT_TRUE)};

        dbHelper = new TaskDbHelper(getContext());

        cursor = getActivity().getApplicationContext().getContentResolver().query(TaskContract.CONTENT_URI, projection, selection, slectionArgs, null);

        try {
            int idColumnName = cursor.getColumnIndex(TaskContract.COLUMN_TITLE);
            int idColumnDesc = cursor.getColumnIndex(TaskContract.COLUMN_DESC);

            while (cursor.moveToNext()) {
                habitTitle.add(cursor.getString(idColumnName));
                habitDescription.add(cursor.getString(idColumnDesc));
            }

            Log.i("ArrayBhai", String.valueOf(habitTitle));
            Log.i("ArrayBhai", String.valueOf(habitDescription));

        } finally {
            cursor.close();
        }


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
}
