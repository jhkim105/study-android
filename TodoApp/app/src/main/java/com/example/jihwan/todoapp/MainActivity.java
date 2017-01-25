package com.example.jihwan.todoapp;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.jihwan.todoapp.base.BaseActivity;
import com.example.jihwan.todoapp.common.AddTaskDialog;
import com.example.jihwan.todoapp.common.AddTaskEvent;
import com.example.jihwan.todoapp.tasklist.TaskListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private TaskListFragment taskListFragment;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        taskListFragment = new TaskListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flContent, taskListFragment, TaskListFragment.class.getName())
                .commit();

    }

    @Override
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

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.floatingActionButton)
    public void onClickAdd() {
//        Task task = new Task();
//        task.setTitle("Test Task");
//        count++;
//        taskListFragment.addTask(task);
        AddTaskDialog addTaskDialog  = new AddTaskDialog();
        addTaskDialog.show(getSupportFragmentManager(), "");
    }

    @Subscribe
    public void onEventAddTask(AddTaskEvent event) {
        if(event.task != null) {
            taskListFragment.addTask(event.task);
        }
    }
}
