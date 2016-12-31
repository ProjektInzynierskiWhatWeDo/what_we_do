package com.nextbest.skalkowski.whatwedo.model;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.nextbest.skalkowski.whatwedo.R;
import com.nextbest.skalkowski.whatwedo.local_database.UserSettings;

public class Notification {
    private void createNotification(Context context, String message , PendingIntent pendingIntent , String action , int id) {
        UserSettings userSettings = UserSettings.getUserSettings();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setTicker(context.getString(R.string.app_name))
                .setContentTitle(context.getString(R.string.app_name))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setContentText(message)
                .setLights(Color.CYAN, 1000, 2000)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setVibrate(getVibrate(userSettings.isVibrate()));


        builder.setSmallIcon(R.mipmap.ic_launcher);

        if (userSettings.isSound()) {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(alarmSound);
        }
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);


        android.app.Notification notification = builder.build();
        notification.flags = android.app.Notification.FLAG_ONLY_ALERT_ONCE;
//        if (actionNotification.equals(ACTION_ACHIEVEMENT) || actionNotification.equals(ACTION_DELETE_GROUP) || actionNotification.equals(ACTION_DELETE_EVENT)) {
//            notification.flags = Notification.FLAG_AUTO_CANCEL;
//        }

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);

    }

    private long[] getVibrate(boolean vibrate) {
        if (vibrate) {
            return new long[]{1000, 1000, 1000, 1000, 1000};
        } else {
            return new long[]{0, 0, 0, 0, 0};
        }
    }
}
