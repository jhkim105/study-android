package com.example.jihwan.todoapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jihwan.todoapp.Task;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Created by jihwan on 04/01/2017.
 */

public class ToDoDBManager {

    private static ToDoDBManager ourInstance = new ToDoDBManager();

    private ToDoDBHelper helper;

    private SQLiteDatabase db;

    public static ToDoDBManager getInstance() {
        return ourInstance;
    }

    private ToDoDBManager() {

    }

    public void initDB(Context context) {
        helper = new ToDoDBHelper(context);
    }

    public long insertTask(Task task) {
        db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskEntry.COLUMN_TITLE, task.getTitle());
        contentValues.put(TaskEntry.COLUMN_DEADLINE, task.getDeadLine());
        contentValues.put(TaskEntry.COLUMN_MEMO, task.getMemo());
        contentValues.put(TaskEntry.COLUMN_COMPLETED, task.isCompleted());

        long result = db.insert(TaskEntry.TABLE_NAME, null, contentValues);
        db.close();

        return result;

    }

    public Task getTask(long taskId) {
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(TaskEntry.TABLE_NAME, null, TaskEntry._ID + "=" + taskId
                , null, null, null, null);

        Task task = null;
        if (cursor.moveToFirst()) {
            task = new Task(cursor);
        }
        cursor.close();
        db.close();

        return task;
    }

    public List<Task> getAllTask() {
        List<Task> taskList = new ArrayList<>();

        db = helper.getReadableDatabase();
        Cursor cursor = db.query(TaskEntry.TABLE_NAME, null, null, null, null, null, TaskEntry._ID + " desc", null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(cursor);
                taskList.add(task);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskList;
    }

    public long updateTaskCompleted(long id, boolean completed) {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskEntry.COLUMN_COMPLETED, completed);
        long result = db.update(TaskEntry.TABLE_NAME,
                contentValues, TaskEntry._ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    public boolean deleteTask(long id) {
        db = helper.getWritableDatabase();

        int result = db.delete(TaskEntry.TABLE_NAME, TaskEntry._ID + "=" + id, null);
        return result > 0;
    }

}
