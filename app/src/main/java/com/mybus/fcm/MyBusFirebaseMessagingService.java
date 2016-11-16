package com.mybus.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mybus.R;
import com.mybus.activity.MainActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class MyBusFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyBusFCMListener";

    @Override
    public void onMessageReceived(RemoteMessage message) {
        if (message.getNotification() != null || message.getData() == null || message.getData().get("title") == null || message.getData().get("text") == null) {
            return;
        }
        String title = message.getData().get("title");
        String text = message.getData().get("text");

        long id = System.currentTimeMillis();

        this.sendNotification(new NotificationData(id, title, text));
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param notificationData FCM message received.
     */
    private void sendNotification(NotificationData notificationData) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(NotificationData.TEXT, notificationData.getTextMessage());

        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = null;
        try {

            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_notification_icon)
                    .setContentTitle(URLDecoder.decode(notificationData.getTitle() != null ? notificationData.getTitle() : "MyBus", "UTF-8"))
                    .setContentText(URLDecoder.decode(notificationData.getTextMessage(), "UTF-8"))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationData.getTextMessage()));

        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "NotificationBuilder creation failed.", e);
        }

        if (notificationBuilder != null) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationData.getId().intValue(), notificationBuilder.build());
        } else {
            Log.d(TAG, "NotificationBuilder creation failed.");
        }
    }
}
