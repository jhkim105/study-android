package com.example.jihwan.todoapp.widgent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jihwan.todoapp.R;
import com.example.jihwan.todoapp.Task;
import com.example.jihwan.todoapp.base.BaseActivity;
import com.example.jihwan.todoapp.common.AddTaskDialog;
import com.example.jihwan.todoapp.common.AddTaskEvent;
import com.example.jihwan.todoapp.db.ToDoDBManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class WidgetAddTaskActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_widget_add_task;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AddTaskDialog addTaskDialog  = new AddTaskDialog();
        addTaskDialog.setCancelable(false);
        addTaskDialog.show(getSupportFragmentManager(), "");
    }

    @Subscribe
    public void onEventAddTask(AddTaskEvent event) {
        if (event.task != null) {
            ToDoDBManager.getInstance().insertTask(event.task);
        }
        finish();

    }

    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
