package com.example.jihwan.todoapp.realm;

import com.example.jihwan.todoapp.Task;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by jihwan on 22/02/2017.
 */

public class TaskRealmManager {

    private Realm realm;

    public TaskRealmManager() {
        realm = Realm.getDefaultInstance();
    }


    public RealmResults<Task> getTaskList() {
        RealmResults<Task> results = realm.where(Task.class).findAll().sort("id", Sort.DESCENDING);
        return results;
    }

    public void addTask(Task task) {
        realm.beginTransaction();
        task.setId(nextId());
        realm.insertOrUpdate(task);
        realm.commitTransaction();
    }

    private long nextId() {
        Number max = realm.where(Task.class).max("id");
        return max == null ? 1l : max.longValue() + 1;
    }


    public Task getTask(long taskId) {
        return realm.where(Task.class).equalTo("id", taskId).findFirst();
    }

    public void close() {
        if (realm != null && !realm.isClosed()){
            realm.close();
        }
    }

    public void updateTaskDeadLine(final Task task, final long date) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.setDeadLine(date);
            }
        });
    }

    public void  updateTask(final Task task, final String title, final String memo, final boolean completed) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.setTitle(title);
                task.setMemo(memo);
                task.setCompleted(completed);
            }
        });
    }


    public void deleteTask(long id) {
        realm.beginTransaction();
        realm.where(Task.class).equalTo("id", id).findFirst().deleteFromRealm();
        realm.commitTransaction();
    }

    public Realm getRealm() {
        return realm;
    }
}
