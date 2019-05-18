package com.gsered.gse;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class pushnotification extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    private void showNotification(String title, String body) {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("gseredapp","gseredapp", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("GSE Red channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableLights(true);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "gseredapp")
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pi)
                .setContentText(body);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());

//        String notification_channel_id = "com.gsered.gse";
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel notificationChannel = new NotificationChannel(notification_channel_id,"Notification",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//
//            notificationChannel.setDescription("GSE Red channel");
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.BLUE);
//            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
//            notificationChannel.enableLights(true);
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//
//        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(this,notification_channel_id);
//        notificationbuilder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.drawable.heart)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setContentInfo("Info");
//
//        notificationManager.notify(new Random().nextInt(), notificationbuilder.build());
    }

    @Override
    public void onNewToken(String token) {
        Log.e("token : ", token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token);
    }
}
