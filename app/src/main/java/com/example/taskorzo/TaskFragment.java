package com.example.taskorzo;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskorzo.data.TaskContract;
import com.example.taskorzo.data.TaskDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import java.io.Console;
import java.security.Provider;
import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;


public class TaskFragment extends Fragment implements AddTaskDialogFragment.OnTaskSelected{
Cursor cursor;
RecyclerView recylerAllTasks;
ArrayList<String> recycleTitle, recycleDescription;
TaskDbHelper dbHelper;
recycleAdapter recycleAdapter;
FloatingActionButton floatingActionButton;
String taskTitle, taskDescription;
ContentValues values = new ContentValues();
FancyButton markcomplete;
Uri newuri;
int isHabit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View taskView =  inflater.inflate(R.layout.task_fragment,container,  false);
        String[] projection = {TaskContract.COLUMN_ID, TaskContract.COLUMN_TITLE, TaskContract.COLUMN_DESC};
        floatingActionButton = taskView.findViewById(R.id.addTaskButton);
        recylerAllTasks = (RecyclerView) taskView.findViewById(R.id.recyclerAllTasks);
        markcomplete = taskView.findViewById(R.id.mark_complete);

        recycleTitle = new ArrayList<>();
        recycleDescription = new ArrayList<>();
        recycleAdapter = new recycleAdapter(getContext(), recycleTitle, recycleDescription);

        recylerAllTasks.setLayoutManager(new LinearLayoutManager(getActivity()));
        recylerAllTasks.setAdapter(recycleAdapter);
        dbHelper = new TaskDbHelper(getContext());

        cursor = getActivity().getApplicationContext().getContentResolver().query(TaskContract.CONTENT_URI, projection, null, null, null);

        try {
            int idColumnName = cursor.getColumnIndex(TaskContract.COLUMN_TITLE);
            int idColumnDesc = cursor.getColumnIndex(TaskContract.COLUMN_DESC);

            while (cursor.moveToNext()) {
                recycleTitle.add(cursor.getString(idColumnName));
                recycleDescription.add(cursor.getString(idColumnDesc));
            }

        } finally {
            cursor.close();
        }

        //FAB Button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialogFragment addTaskDialogFragment = new AddTaskDialogFragment();
                addTaskDialogFragment.setTargetFragment(TaskFragment.this, 1);
                addTaskDialogFragment.show(getFragmentManager(), "New Task Dialog");
            }
        });

        recycleAdapter.setOnItemClickListner(new recycleAdapter.onItemClickListner() {
            @Override
            public void onClick(String buttonClicked) {
                if (!buttonClicked.isEmpty()) {
                    String selection = TaskContract.COLUMN_TITLE + "=?";
                    String selectionArgs = buttonClicked;
                    getActivity().getContentResolver().delete(TaskContract.CONTENT_URI, selection, new String[]{selectionArgs});
                    int index = recycleTitle.indexOf(buttonClicked);
                    recycleTitle.remove(index);
                    recycleDescription.remove(index);
                    recylerAllTasks.removeViewAt(index);
                    recycleAdapter.notifyItemRemoved(index);
                    recycleAdapter.notifyItemRangeChanged(index, recycleTitle.size());

                }
            }
        });


     return taskView;
    }

    @Override
    public void sendTask(String[] newTask) {
        taskTitle = newTask[0];
        taskDescription = newTask[1];

        if(!taskTitle.isEmpty()) {
            recycleTitle.add(taskTitle);
            recycleDescription.add(taskDescription);
            recycleAdapter.notifyDataSetChanged();

            values.put(TaskContract.COLUMN_TITLE, taskTitle);
            values.put(TaskContract.COLUMN_DESC, taskDescription);

            newuri = getActivity().getContentResolver().insert(TaskContract.CONTENT_URI, values);

        }
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
