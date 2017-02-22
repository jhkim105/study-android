package com.example.jihwan.todoapp.tasklist;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jihwan.todoapp.R;
import com.example.jihwan.todoapp.Task;
import com.example.jihwan.todoapp.base.BaseActivity;
import com.example.jihwan.todoapp.db.ToDoDBManager;
import com.example.jihwan.todoapp.realm.TaskRealmManager;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jihwan on 11/01/2017.
 */

public class TaskDetailActivity extends BaseActivity  implements DatePickerDialog.OnDateSetListener{

    public static final String EXTRA_TASK_ID = "taskId";

    private long taskId;

    private Task task;

    public static final int RESULT_DELETE = 900;

    private TaskRealmManager taskRealmManager;


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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        taskRealmManager = new TaskRealmManager();
        task = taskRealmManager.getTask(taskId);
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
        taskRealmManager.updateTaskDeadLine(task, time);
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
            deleteTask(taskId);
            return true;
        } else if (menuId == R.id.menu_save) {
            updateTask();
            return true;
        } else if (menuId == android.R.id.home) {
            updateTask();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        updateTask();
    }

    private void deleteTask(long id) {
        taskRealmManager.deleteTask(id);
        finish();
    }

    private void updateTask() {
        String title = etTitle.getText().toString().trim();
        if (!TextUtils.isEmpty(title)) {
            taskRealmManager.updateTask(task, etTitle.getText().toString(),
                    etMemo.getText().toString().trim(),
                    cbCompleted.isChecked());

            finish();

        } else {
            Toast.makeText(this, "Plz Input title", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.btnDeadline)
    public void onClickSetDeadline() {

        Calendar calendar = Calendar.getInstance();
        if(task.getDeadLine() > 0) {
            calendar.setTimeInMillis(task.getDeadLine());
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.WEEK_OF_MONTH));
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar =  Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        updateDeadline(calendar.getTimeInMillis());
    }

    @OnClick(R.id.btnDeadlineDelete)
    public void onClickDealineDelete() {
        updateDeadline(-1L);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(EXTRA_TASK_ID, taskId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(taskRealmManager != null) {
            taskRealmManager.close();
        }
    }
}
