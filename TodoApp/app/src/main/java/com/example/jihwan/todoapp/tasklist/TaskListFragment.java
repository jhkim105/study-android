package com.example.jihwan.todoapp.tasklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jihwan.todoapp.R;
import com.example.jihwan.todoapp.Task;
import com.example.jihwan.todoapp.base.BaseFragment;
import com.example.jihwan.todoapp.db.ToDoDBManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

/**
 * Created by jihwan on 21/12/2016.
 */

public class TaskListFragment extends BaseFragment {

    @BindView(R.id.rvList)
    RecyclerView rvList;

    private TaskListAdapter taskListAdapter;

    private List<Task> tasks;

    @Override
    protected int getLayoutResId() {
        return R.layout.frament_task_list;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        tasks = new ArrayList<>();

        taskListAdapter = new TaskListAdapter(tasks);

        rvList.setAdapter(taskListAdapter);

        getTaskList();

    }

    public void getTaskList() {
        tasks.clear();
        tasks.addAll(ToDoDBManager.getInstance().getAllTask());
        taskListAdapter.notifyDataSetChanged();
    }

    public void addTask(Task task) {
        tasks.add(0, task);
        task.setId(ToDoDBManager.getInstance().insertTask(task));
        taskListAdapter.notifyItemChanged(0);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEventTask(TaskEvent taskEvent) {
        Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
        intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, taskEvent.getTask().getId());
        startActivity(intent);
    }
}
