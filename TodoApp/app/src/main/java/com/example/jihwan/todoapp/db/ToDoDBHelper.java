package com.example.jihwan.todoapp.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jihwan on 04/01/2017.
 */

public class ToDoDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "todo.db";
    public static final int DB_VERSION = 1;

    public ToDoDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaskEntry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TaskEntry.DELETE_TABLE);
        onCreate(db);
    }
}
