package com.sumir.reminder.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.sumir.reminder.activity.R;
import com.sumir.reminder.activity.TaskEditActivity;
import com.sumir.reminder.provider.TaskProvider;
import com.sumir.reminder.util.ReminderManager;

/**
 * Created by Sumir on 05-06-2017.
 */

public class OnAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Uri ringtoneUri;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String defaultToneKey = context.getString(R.string.tone_default_key);

        String ringtonePreference = sharedPreferences.getString(defaultToneKey,"content://settings/system/notification_sound");
        ringtoneUri = Uri.parse(ringtonePreference);

        Intent taskEditIntent = new Intent(context, TaskEditActivity.class);

        long taskId = intent.getLongExtra(TaskProvider.COLUMN_TASKID,-1);
        String title = intent.getStringExtra(TaskProvider.COLUMN_TITLE);
        taskEditIntent.putExtra(TaskEditActivity.EXTRA_TASKID,taskId);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,(int)taskId,taskEditIntent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder note = new NotificationCompat.Builder(context)
                                                            .setContentTitle(context.getString(R.string.notify_new_task_title))
                                                            .setContentText(title)
                                                            .setSmallIcon(android.R.drawable.ic_menu_agenda)
                                                            .setContentIntent(pendingIntent)
                                                            .setAutoCancel(true)
                                                            .setSound(ringtoneUri);

        note.setVibrate(new long[]{500,1000});
        notificationManager.notify((int) taskId,note.build());

    }
}
