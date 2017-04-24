package com.example.matanbex.todolistmanager;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "todotable.db";
    private static final int DATABASE_VERSION = 1;

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TodoListTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        TodoListTable.onUpgrade(db, oldVersion, newVersion);
    }
}
