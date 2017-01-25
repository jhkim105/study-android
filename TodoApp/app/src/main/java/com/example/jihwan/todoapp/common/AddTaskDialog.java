package com.example.jihwan.todoapp.common;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jihwan.todoapp.R;
import com.example.jihwan.todoapp.Task;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jihwan on 28/12/2016.
 */

public class AddTaskDialog  extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.etTitle)
    EditText etTitle;

    @BindView(R.id.tvDeadline)
    TextView tvDeadline;

    @BindView(R.id.btnDeadlineDelete)
    Button btnDeadLineDelete;

    private Task task;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task = new Task();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_task, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onClickAdd();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.btnDeadline)
    public void onClickSetDeadline() {

        Calendar calendar = Calendar.getInstance();
        if(task.getDeadLine() > 0) {
            calendar.setTimeInMillis(task.getDeadLine());
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.WEEK_OF_MONTH));
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar =  Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        updateDeadline(calendar.getTimeInMillis());
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

    @OnClick(R.id.btnDeadlineDelete)
    public void onClickDealineDelete() {
        updateDeadline(-1L);
    }

    @OnClick(R.id.btnCancel)
    public void onClickCancel() {
        dismiss();
    }

    @OnClick(R.id.btnAdd)
    public void onClickAdd() {
        String title = etTitle.getText().toString().trim();
        if (title.isEmpty()) return;
        task.setTitle(title);
        AddTaskEvent event = new AddTaskEvent();
        event.task = task;
        EventBus.getDefault().post(event);
        dismiss();
    }
}
