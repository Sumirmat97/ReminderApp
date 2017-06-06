package com.dummies.tasks.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.dummies.tasks.provider.TaskProvider;

import java.util.Calendar;

import com.dummies.tasks.receiver.OnAlarmReceiver;

/**
 * Created by Sumir on 05-06-2017.
 */

public class ReminderManager {
    private ReminderManager(){}

    public static void setReminder(Context context, long taskId, String title, Calendar when)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

        Intent intent = new Intent(context, OnAlarmReceiver.class);
        intent.putExtra(TaskProvider.COLUMN_TASKID,taskId);
        intent.putExtra(TaskProvider.COLUMN_TITLE,title);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_ONE_SHOT);

        long diff =  (when.getTimeInMillis() - calendar.getTimeInMillis());

        if(diff > 0 ){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,when.getTimeInMillis(),pendingIntent);

        }
    }

    public static void removeReminder(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, OnAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_ONE_SHOT);

        alarmManager.cancel(pendingIntent);
    }
}
