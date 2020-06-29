package com.example.taskorzo.data;

import android.net.Uri;

public final class TaskContract {

    public static final String TABLE_NAME = "tasks";
    public static final String COLUMN_TITLE = "titles";
    public static final String COLUMN_DESC = "descriptions";
    public static final String COLUMN_MAKE_HABIT = "ishabit";
    public static final String COLUMN_ID = "_id";


    //Possible values for isHABIT

    public static final int MAKE_HABIT_TRUE = 1;
    public static final int MAKE_HABIT_FALSE = 0;

    public static final String CONTENT_AUTHORITY = "com.example.taskorzo";
    public static final String BASE_CONTENT_URI = "content://" + CONTENT_AUTHORITY;


    public static final String PATH_TASKS = "tasks";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(Uri.parse(BASE_CONTENT_URI), PATH_TASKS);
}

