package com.example.taskorzo.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HabitProvider extends ContentProvider {

    public static final String LOG_TAG = HabitProvider.class.getSimpleName();
    private HabitDbHelper habitDbHelper;
    private static final int HABITS = 100;
    private static final int HABIT_ID = 101;
    public static final String CONTENT_LIST_TYPE  = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + HabitContract.CONTENT_AUTHORITY + "/" + HabitContract.PATH_HABITS;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + HabitContract.CONTENT_AUTHORITY + "/" + HabitContract.PATH_HABITS;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + HabitContract.CONTENT_AUTHORITY);

    static {
        sUriMatcher.addURI(HabitContract.CONTENT_AUTHORITY, HabitContract.PATH_HABITS, HABITS);
        sUriMatcher.addURI(HabitContract.CONTENT_AUTHORITY, HabitContract.PATH_HABITS + "/#", HABIT_ID );
    }

    @Override
    public boolean onCreate() {
        habitDbHelper = new HabitDbHelper(getContext());
        return true;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = habitDbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = database.query(HabitContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        int match = sUriMatcher.match(uri);

        switch(match) {
            case HABITS:
                cursor = database.query(HabitContract.TABLE_NAME, projection, selection, selectionArgs ,null, null, sortOrder);
                break;
            case HABIT_ID:
                selection = HabitContract.COLUMN_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(HabitContract.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknow URI" + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HABITS:
                return CONTENT_LIST_TYPE;
            case HABIT_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri +" with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HABITS:
                return insertHabit(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported on " + uri);
        }
    }

    private Uri insertHabit(Uri uri, ContentValues values) {
        SQLiteDatabase database = habitDbHelper.getWritableDatabase();

        String name = values.getAsString(HabitContract.COLUMN_TITLE);

        if (name == null) {
            throw new IllegalArgumentException("Habit requires a name ");
        }

        long id = database.insert(HabitContract.TABLE_NAME, null, values);
        if (id == -1) {
            Log.i("Insert Habit Provider", "Failed to insert row");
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = habitDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HABITS:
                return database.delete(HabitContract.TABLE_NAME, selection, selectionArgs);

            case HABIT_ID:
                selection = HabitContract.COLUMN_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                return database.delete(HabitContract.TABLE_NAME, selection, selectionArgs);
            default:
                throw  new IllegalArgumentException("Deleting is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HABITS:
                return updateHabit(uri, values, selection, selectionArgs);
            case HABIT_ID:
                selection = HabitContract.COLUMN_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateHabit(uri, values, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }

    }

    private int updateHabit (Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(HabitContract.COLUMN_TITLE)) {
            String name = values.getAsString(HabitContract.COLUMN_TITLE);
            if (name == null) {
                throw new IllegalArgumentException("Habit requires a title");
            }
            if (values.size() == 0) {
                return 0;
            }
        }
        SQLiteDatabase database = habitDbHelper.getWritableDatabase();
        return database.update(HabitContract.TABLE_NAME, values, selection, selectionArgs);
    }

}
