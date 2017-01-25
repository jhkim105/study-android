package com.example.jihwan.todoapp;

import android.app.Application;

import com.example.jihwan.todoapp.db.ToDoDBManager;

/**
 * Created by jihwan on 04/01/2017.
 */

public class ToDoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToDoDBManager.getInstance().initDB(this);
    }
}
