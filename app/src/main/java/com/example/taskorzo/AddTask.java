package com.example.taskorzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.taskorzo.data.TaskContract;
import com.example.taskorzo.data.TaskDbHelper;

public class AddTask extends AppCompatActivity {
    EditText editDescription, editTaskTitle;
    TaskDbHelper taskDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Here for app to go fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_task);
        //Initialize all elements
        taskDbHelper = new TaskDbHelper(this);
        editTaskTitle = findViewById(R.id.editTaskTitle);
        editDescription = findViewById(R.id.editTaskDesc);





        Button btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertTask();
                finish();
            }
        });

        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void insertTask() {
        String taskTitle = editTaskTitle.getText().toString().trim();
        String taskDescription = editDescription.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(TaskContract.COLUMN_TITLE, taskTitle);
        values.put(TaskContract.COLUMN_DESC, taskDescription);

        Uri newUri = getContentResolver().insert(TaskContract.CONTENT_URI, values);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}