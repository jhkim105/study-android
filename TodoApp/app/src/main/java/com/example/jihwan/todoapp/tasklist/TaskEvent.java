package com.example.jihwan.todoapp.tasklist;

import com.example.jihwan.todoapp.Task;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jihwan on 11/01/2017.
 */

@Getter
@Setter
public class TaskEvent {

    public static final int ACTION_GO_DETAIL = 10;

    private int action;

    private Task task;
}
