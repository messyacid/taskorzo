package com.example.taskorzo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HabitDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "habits.db";
    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TASK_TABLE =  " CREATE TABLE " + HabitContract.TABLE_NAME + "("
                + HabitContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitContract.COLUMN_TITLE + " TEXT NOT NULL, "
                + HabitContract.COLUMN_DESC + " TEXT ); ";



        db.execSQL(SQL_CREATE_TASK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }}
