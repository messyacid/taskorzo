package com.example.taskorzo.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



public class TaskProvider extends ContentProvider {

    public static final String LOG_TAG = TaskProvider.class.getSimpleName();
    private TaskDbHelper taskDbHelper;
    private static final int TASKS = 100;
    private static final int TASK_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + TaskContract.CONTENT_AUTHORITY);

    static {
        sUriMatcher.addURI(TaskContract.CONTENT_AUTHORITY, TaskContract.PATH_TASKS, TASKS);
        sUriMatcher.addURI(TaskContract.CONTENT_AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_ID);
    }

    @Override
    public boolean onCreate() {
        taskDbHelper = new TaskDbHelper(getContext());
        return true;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = taskDbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = database.query(TaskContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        int match = sUriMatcher.match(uri);

        switch(match) {
            case TASKS:
                cursor = database.query(TaskContract.TABLE_NAME, projection, selection, selectionArgs ,null, null, sortOrder);
                break;
            case TASK_ID:
                selection = TaskContract.COLUMN_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(TaskContract.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknow URI" + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TASKS:
                return insertTask(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported on " + uri);
        }
    }

    private Uri insertTask(Uri uri, ContentValues values) {
        SQLiteDatabase database = taskDbHelper.getWritableDatabase();

        String name = values.getAsString(TaskContract.COLUMN_TITLE);

        if (name == null) {
            throw new IllegalArgumentException("task requires a name ");
        }

        long id = database.insert(TaskContract.TABLE_NAME, null, values);
        if (id == -1) {
            Log.i("Insert Task Provider", "Failed to insert row");
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
        
    }
}
