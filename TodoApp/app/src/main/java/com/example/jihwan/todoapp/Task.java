package com.example.jihwan.todoapp;

import android.database.Cursor;

import com.example.jihwan.todoapp.db.TaskEntry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by jihwan on 21/12/2016.
 */

@Getter
@Setter
@ToString
public class Task {

    public static final long NO_DEADLINE = -1l;

    private long id;

    private String title;

    private String memo;

    private long deadLine;

    private boolean completed;

    public Task() {
        id = -1l;
        title = "";
        memo = "";
        deadLine = NO_DEADLINE;
        completed = false;
    }

    public Task(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(TaskEntry._ID));
        title = cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TITLE));
        memo = cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_MEMO));
        deadLine = cursor.getLong(cursor.getColumnIndex(TaskEntry.COLUMN_DEADLINE));
        completed = "1".equals(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_COMPLETED)));

    }

}
