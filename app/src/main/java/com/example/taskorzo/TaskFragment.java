package com.example.taskorzo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskorzo.data.TaskContract;
import com.example.taskorzo.data.TaskDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TaskFragment extends Fragment implements AddTaskDialogFragment.OnTaskSelected {
Cursor cursor;
RecyclerView recylerAllTasks;
ArrayList<String> recycleTitle, recycleDescription;
TaskDbHelper dbHelper;
recycleAdapter recycleAdapter;
FloatingActionButton floatingActionButton;
String taskTitle, taskDescription;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View taskView =  inflater.inflate(R.layout.task_fragment,container,  false);
        String[] projection = {TaskContract.COLUMN_ID, TaskContract.COLUMN_TITLE, TaskContract.COLUMN_DESC};

        floatingActionButton = taskView.findViewById(R.id.addTaskButton);
        recylerAllTasks = (RecyclerView) taskView.findViewById(R.id.recyclerAllTasks);
        recycleTitle = new ArrayList<String>();
        recycleDescription = new ArrayList<String>();

        //database associated code here
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

        recycleAdapter = new recycleAdapter(getContext(), recycleTitle, recycleDescription);

        //Adding recylerView and Array populating code here
        recylerAllTasks.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleTitle = new ArrayList<>();
        recycleDescription = new ArrayList<>();

        //Populating RecyclerView with Database TODO 1-2
        recylerAllTasks.setAdapter(recycleAdapter);



        //FAB Button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialogFragment addTaskDialogFragment = new AddTaskDialogFragment();
                addTaskDialogFragment.setTargetFragment(TaskFragment.this, 1);
                addTaskDialogFragment.show(getFragmentManager(), "New Task Dialog");
            }
        });


     return taskView;
    }


    @Override
    public void sendTask(String[] newTask) {
        taskTitle = newTask[0];
        taskDescription = newTask[1];

        Log.i("DIDIT", taskTitle + " " + taskDescription);
    }
}
