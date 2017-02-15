package com.example.jihwan.todoapp.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.jihwan.todoapp.service.TaskService;

import java.util.Calendar;

/**
 * Created by jihwan on 15/02/2017.
 */

public class IntentUtils {

    public static void setServiceAlarm(Context context, boolean flag) {
        Log.d("jhkim", "SetAlarm!!!");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long date = calendar.getTimeInMillis();
        long debugDate = System.currentTimeMillis() + 10000;
        PendingIntent pendingIntent = PendingIntent.getService(context, 9876, new Intent(context, TaskService.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        if (flag) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, date, pendingIntent);
        }
    }
}
