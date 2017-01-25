package com.example.jihwan.todoapp.tasklist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jihwan.todoapp.R;
import com.example.jihwan.todoapp.Task;
import com.example.jihwan.todoapp.base.BaseActivity;
import com.example.jihwan.todoapp.db.ToDoDBManager;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by jihwan on 11/01/2017.
 */

public class TaskDetailActivity extends BaseActivity {

    public static final String EXTRA_TASK_ID = "taskId";

    private long taskId;

    private Task task;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.cbCompleted)
    CheckBox cbCompleted;

    @BindView(R.id.etTitle)
    EditText etTitle;

    @BindView(R.id.tvDeadline)
    TextView tvDeadline;

    @BindView(R.id.btnDeadlineDelete)
    Button btnDeadLineDelete;

    @BindView(R.id.etMemo)
    EditText etMemo;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_task_detail;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        Bundle bundle = null;
        if (savedInstanceState != null) {
            bundle = savedInstanceState;
        } else if (getIntent() != null) {
            bundle = getIntent().getExtras();
        }

        if (bundle != null) {
            taskId = bundle.getLong(EXTRA_TASK_ID, -1l);
        }

        if (taskId < 1) {
            finish();
            return;
        }

        task = ToDoDBManager.getInstance().getTask(taskId);
        initTaskDetail(task);
    }


    private void initTaskDetail(Task task) {
        if (task == null)
            return;
        cbCompleted.setChecked(task.isCompleted());
        etTitle.setText(task.getTitle());
        etMemo.setText(task.getMemo());
        updateDeadline(task.getDeadLine());
    }


    private void updateDeadline(long time) {
        task.setDeadLine(time);
        if (time > 0) {
            btnDeadLineDelete.setVisibility(View.VISIBLE);
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String dateString = df.format(new Date(time));
            tvDeadline.setText(dateString);

        } else {
            btnDeadLineDelete.setVisibility(View.GONE);
            tvDeadline.setText(R.string.label_set_enddt);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        if (menuId == R.id.menu_delete) {
            return true;
        } else if (menuId == R.id.menu_save) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
