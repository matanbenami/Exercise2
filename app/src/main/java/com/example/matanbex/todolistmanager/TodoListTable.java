package com.example.matanbex.todolistmanager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TodoListTable {

    public static final String TABLE_TODO = "todo_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_DATE = "date";

    private static final String DATABASE_CREATE = "create table " +
            TABLE_TODO + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_MESSAGE + " text not null, " +
            COLUMN_DATE + " text not null" + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(TodoListTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(database);
    }

}
