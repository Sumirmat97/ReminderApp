package com.dummies.tasks.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dummies.tasks.activity.R;
import com.dummies.tasks.activity.TaskEditActivity;
import com.dummies.tasks.provider.TaskProvider;

/**
 * Created by Sumir on 05-06-2017.
 */

public class OnAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        Intent taskEditIntent = new Intent(context, TaskEditActivity.class);

        long taskId = intent.getLongExtra(TaskProvider.COLUMN_TASKID,-1);

        String title = intent.getStringExtra(TaskProvider.COLUMN_TITLE);
        taskEditIntent.putExtra(TaskEditActivity.EXTRA_TASKID,taskId);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,taskEditIntent,PendingIntent.FLAG_ONE_SHOT);
        Notification note = new Notification.Builder(context).setContentTitle(context.getString(R.string.notify_new_task_title))
                                                            .setContentText(title)
                                                            .setSmallIcon(android.R.drawable.ic_menu_my_calendar)
                                                            .setContentIntent(pendingIntent)
                                                            .setAutoCancel(true)
                                                            .build();

        note.defaults |= Notification.DEFAULT_ALL;
        notificationManager.notify((int) taskId,note);

    }
}
