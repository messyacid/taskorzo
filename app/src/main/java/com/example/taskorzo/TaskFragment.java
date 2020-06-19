package com.example.taskorzo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taskorzo.data.TaskContract;
import com.example.taskorzo.data.TaskDbHelper;

public class TaskFragment extends Fragment {
TextView initialResult;
Cursor cursor;
TaskDbHelper dbHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View taskView =  inflater.inflate(R.layout.task_fragment,container,  false);
        String[] projection = {TaskContract.COLUMN_ID, TaskContract.COLUMN_TITLE, TaskContract.COLUMN_DESC};

        dbHelper = new TaskDbHelper(getContext());
        cursor = getActivity().getApplicationContext().getContentResolver().query(TaskContract.CONTENT_URI, projection, null, null, null);
        initialResult = taskView.findViewById(R.id.intialResult);

        try {

            initialResult.setText("Task table contains " + cursor.getCount() + " tasks \n\n");
            initialResult.append(TaskContract.COLUMN_TITLE + " - - - " + TaskContract.COLUMN_DESC + "\n");

            int idColumnName = cursor.getColumnIndex(TaskContract.COLUMN_TITLE);
            int idColumnDesc = cursor.getColumnIndex(TaskContract.COLUMN_DESC);

            while (cursor.moveToNext()) {
                String currentName = cursor.getString(idColumnName);
                String currentDescription = cursor.getString(idColumnDesc);

                initialResult.append("\n" + currentName + "---" + currentDescription);
            }

        } finally {
            cursor.close();
        }

        return taskView;
    }


}
