package com.example.jihwan.todoapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TaskServiceReceiver extends BroadcastReceiver {
    public TaskServiceReceiver() {
    }

    @Override
    /**
     * 시간이 오래걸리는 작업을 하면 안된다.
     * 일정시간 넘어가면 에러
     */
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, TaskService.class);
        context.startService(serviceIntent);
    }
}
