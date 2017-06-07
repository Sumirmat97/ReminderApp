package com.dummies.tasks.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String defaultToneKey = context.getString(R.string.tone_default_key);

        String ringtonePreference = sharedPreferences.getString(defaultToneKey,"DEFAULT_RINGTONE_URI");
        Uri ringtoneUri = Uri.parse(ringtonePreference);

        Intent taskEditIntent = new Intent(context, TaskEditActivity.class);

        long taskId = intent.getLongExtra(TaskProvider.COLUMN_TASKID,-1);
        String title = intent.getStringExtra(TaskProvider.COLUMN_TITLE);
        taskEditIntent.putExtra(TaskEditActivity.EXTRA_TASKID,taskId);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,taskEditIntent,PendingIntent.FLAG_ONE_SHOT);

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
