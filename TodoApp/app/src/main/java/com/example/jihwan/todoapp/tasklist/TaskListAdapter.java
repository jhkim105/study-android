package com.example.jihwan.todoapp.tasklist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.jihwan.todoapp.R;
import com.example.jihwan.todoapp.Task;
import com.example.jihwan.todoapp.db.ToDoDBManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by jihwan on 21/12/2016.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>{

    private RealmResults<Task> taskList;



    public TaskListAdapter(RealmResults<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.cbTitle.setText(task.getTitle());
        holder.cbCompleted.setChecked(task.isCompleted());

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cbCompleted)
        CheckBox cbCompleted;

        @BindView(R.id.cbTitle)
        TextView cbTitle;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cbCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Task task = taskList.get(getAdapterPosition());

                    if (task.isCompleted() != isChecked) {
                        task.setCompleted(isChecked);
                        ToDoDBManager.getInstance().updateTaskCompleted(task.getId(), task.isCompleted());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Task task = taskList.get(pos);

                    TaskEvent taskEvent = new TaskEvent();
                    taskEvent.setAction(TaskEvent.ACTION_GO_DETAIL);
                    taskEvent.setTask(task);
                    EventBus.getDefault().post(taskEvent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    Task task = taskList.get(pos);
                    if (ToDoDBManager.getInstance().deleteTask(task.getId())) {
                        taskList.remove(pos);
                        notifyItemRemoved(pos);
                        return true;
                    }
                    return false;
                }
            });

        }

    }
}
