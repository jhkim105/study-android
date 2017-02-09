package com.example.jihwan.todoapp.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.jihwan.todoapp.MainActivity;
import com.example.jihwan.todoapp.R;
import com.example.jihwan.todoapp.Task;
import com.example.jihwan.todoapp.db.ToDoDBManager;

import java.util.Calendar;
import java.util.List;

/**
 * Created by jihwan on 02/02/2017.
 */

public class TaskService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notifyCompletedTask();


        stopSelf();
        return START_NOT_STICKY;
    }

    private void notifyCompletedTask() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        List<Task> taskList = ToDoDBManager.getInstance().getIncompleteTasks(calendar.getTimeInMillis());

        if (taskList != null && !taskList.isEmpty()) {
            for (Task task : taskList) {
                Log.d("jhkim", "title:" + task.getTitle());
            }
        } else {
            Log.d("jhkim", "no task");
            stopSelf();
            return;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("할 일 목록");
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE);
        builder.setLights(0xff0000ff, 2000, 2000);
        builder.setAutoCancel(true);
//        builder.setContentText(taskList.get(0).getTitle());

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle(builder);
        inboxStyle.setSummaryText("일일");
        for (Task task : taskList) {
            inboxStyle.addLine(task.getTitle());
        }

        NotificationManagerCompat.from(this).notify(1234, inboxStyle.build());
    }

    private void setAlarm(long date) {
        PendingIntent pendingIntent = PendingIntent.getService(this, 9876, new Intent(this, TaskService.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, date, pendingIntent);

    }
}
