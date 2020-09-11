package com.example.taskorzo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String SQL_CREATE_TASK_TABLE =  " CREATE TABLE " + TaskContract.TABLE_NAME + "("
               + TaskContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
               + TaskContract.COLUMN_TITLE + " TEXT NOT NULL, "
               + TaskContract.COLUMN_DESC + " TEXT ); ";



       db.execSQL(SQL_CREATE_TASK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
