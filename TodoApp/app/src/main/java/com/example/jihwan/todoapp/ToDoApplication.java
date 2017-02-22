package com.example.jihwan.todoapp;

import android.app.Application;

import com.example.jihwan.todoapp.db.ToDoDBManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jihwan on 04/01/2017.
 */

public class ToDoApplication extends Application {

    public static final String TASK_REALM_NAME = "task.realm";
    public static final int TASK_REALM_VERSION = 1;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(TASK_REALM_NAME)
                .schemaVersion(TASK_REALM_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        ToDoDBManager.getInstance().initDB(this);

    }
}
