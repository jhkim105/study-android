package com.example.jihwan.todoapp.tasklist;

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
    }

    public void addTask(Task task) {
        tasks.add(0, task);
        taskListAdapter.notifyDataSetChanged();
    }
}
