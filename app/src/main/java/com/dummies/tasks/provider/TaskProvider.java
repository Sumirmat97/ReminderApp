package com.dummies.tasks.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Sumir on 01-06-2017.
 */

public class TaskProvider extends ContentProvider {

    public static final String COLUMN_TASKID = "_id";
    public static final String COLUMN_DATE_TIME = "task_date_time";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_TITLE = "title";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "tasks";

    public static final String AUTHORITY = "com.dummies.tasks.provider.TaskProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/task");

    private static final String TASKS_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.dummies.tasks.tasks";
    private static final String TASK_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vmd.com.dummies.tasks.task";

    private static final int LIST_TASK = 0;
    private static final int ITEM_TASK = 1;
    private static final UriMatcher URI_MATCHER = buildUriMatcher();



    SQLiteDatabase database;

    @Override
    public boolean onCreate() {

        database = new DatabaseHelper(getContext()).getWritableDatabase();
        return true;
    }

    protected static class DatabaseHelper extends SQLiteOpenHelper {

        static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + " (" +
                    COLUMN_TASKID + " integer primary key autoincrement, " +
                    COLUMN_TITLE + " text not null, " +
                    COLUMN_NOTES + " text not null, " +
                    COLUMN_DATE_TIME + " integer not null);";

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            throw new UnsupportedOperationException();
        }
    }

    private static UriMatcher buildUriMatcher(){
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "task", LIST_TASK);
        matcher.addURI(AUTHORITY, "task/#", ITEM_TASK);
        return matcher;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String[] projection = new String[] {COLUMN_TASKID,
                                            COLUMN_TITLE,
                                            COLUMN_NOTES,
                                            COLUMN_DATE_TIME};
        Cursor cursor;
        switch (URI_MATCHER.match(uri))
        {
            case LIST_TASK:

                cursor = database.query(DATABASE_TABLE,projection,selection
                                        ,selectionArgs,null,null,sortOrder);
                break;
            case ITEM_TASK:
                cursor = database.query(DATABASE_TABLE,
                                        projection,
                                        COLUMN_TASKID + "=?",
                                        new String[]{Long.toString(ContentUris.parseId(uri))},
                                        null,null,null,null);
                if(cursor.getCount() > 0)
                    cursor.moveToFirst();

                break;

            default: throw new IllegalArgumentException("Unknown Uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri))
        {
            case LIST_TASK: return TASKS_MIME_TYPE;
            case ITEM_TASK: return TASK_MIME_TYPE;
            default: throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if(contentValues.containsKey(COLUMN_TASKID))
            throw new UnsupportedOperationException();

        long id = database.insertOrThrow(DATABASE_TABLE,null,contentValues);
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int count = database.delete(
                    DATABASE_TABLE,
                    COLUMN_TASKID + "=?",
                    new String[]{Long.toString(ContentUris.parseId(uri))});
        if(count > 0)
            getContext().getContentResolver().notifyChange(uri,null);

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        if(contentValues.containsKey(COLUMN_TASKID))
            throw new UnsupportedOperationException();
        int count = database.update(
                DATABASE_TABLE,
                contentValues,
                COLUMN_TASKID + "=?",
                new String[]{Long.toString(ContentUris.parseId(uri))}
        );

        if(count > 0)
            getContext().getContentResolver().notifyChange(uri,null);

        return count;
    }
}
