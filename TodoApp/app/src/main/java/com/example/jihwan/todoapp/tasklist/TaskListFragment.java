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
import com.example.jihwan.todoapp.realm.TaskRealmManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by jihwan on 21/12/2016.
 */

public class TaskListFragment extends BaseFragment implements RealmChangeListener<RealmResults<Task>> {

    public static final int REQUEST_CODE_DETAIL = 2001;
    @BindView(R.id.rvList)
    RecyclerView rvList;

    private TaskListAdapter taskListAdapter;

    private RealmResults<Task> tasks;

    private TaskRealmManager realmManager;

    @Override
    protected int getLayoutResId() {
        return R.layout.frament_task_list;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        realmManager = new TaskRealmManager();

        tasks = realmManager.getTaskList();

        tasks.addChangeListener(this);
        taskListAdapter = new TaskListAdapter(tasks);

        rvList.setAdapter(taskListAdapter);


    }

    public void addTask(Task task) {
        realmManager.addTask(task);
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
        if (taskEvent.getTask() == null)
            return;
        if (taskEvent.getAction() == TaskEvent.ACTION_GO_DETAIL) {
            Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
            intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, taskEvent.getTask().getId());
            startActivityForResult(intent, REQUEST_CODE_DETAIL);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if(requestCode == REQUEST_CODE_DETAIL) {
//            if (resultCode == TaskDetailActivity.RESULT_DELETE) {
//                long taskId = data.getLongExtra(TaskDetailActivity.EXTRA_TASK_ID, -1L);
//                getTaskList();
//            } else if (resultCode == TaskDetailActivity.RESULT_OK) {
//                getTaskList();
//            }
//        }
    }



    @Override
    public void onChange(RealmResults<Task> element) {
        taskListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tasks.removeChangeListener(this);
        if (realmManager != null) {
            realmManager.close();
        }
    }
}
