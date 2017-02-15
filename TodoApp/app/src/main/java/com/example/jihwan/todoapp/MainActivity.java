package com.example.jihwan.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jihwan.todoapp.base.BaseActivity;
import com.example.jihwan.todoapp.common.AddTaskDialog;
import com.example.jihwan.todoapp.common.AddTaskEvent;
import com.example.jihwan.todoapp.service.TaskService;
import com.example.jihwan.todoapp.setting.SettingActivity;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.menu_setting) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
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
