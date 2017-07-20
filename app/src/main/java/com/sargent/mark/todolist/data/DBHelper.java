package com.sargent.mark.todolist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mark on 7/4/17.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "items.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* I have added two new columns which are checked as a 0 or 1 for boolean since there are no
        boolean types for SQLite and category as text for the string of the category
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + Contract.TABLE_TODO.TABLE_NAME + " ("+
                Contract.TABLE_TODO._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE + " DATE, " +
                Contract.TABLE_TODO.COLUMN_NAME_CHECKED + " INTEGER, " +
                Contract.TABLE_TODO.COLUMN_NAME_CATEGORY + " TEXT " + "); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table " + Contract.TABLE_TODO.TABLE_NAME + " if exists;");
    }
}
