package com.example.jihwan.todoapp.db;

import android.provider.BaseColumns;

/**
 * Created by jihwan on 04/01/2017.
 */

public class TaskEntry implements BaseColumns {

    public static final String TABLE_NAME = "task";

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DEADLINE = "deadline";
    public static final String COLUMN_MEMO = "memo";
    public static final String COLUMN_COMPLETED = "completed";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT NOT NULL," +
                    COLUMN_DEADLINE + " INTEGER," +
                    COLUMN_MEMO + " TEXT, " +
                    COLUMN_COMPLETED + " TEXT NOT NULL);";

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


}
