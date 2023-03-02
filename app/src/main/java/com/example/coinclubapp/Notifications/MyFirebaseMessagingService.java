package com.example.coinclubapp.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.coinclubapp.MainActivity;
import com.example.coinclubapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "FCM SERVICE";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Toast.makeText(this, "notification aya he", Toast.LENGTH_SHORT).show();
        }

            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            String channelId = getString(R.string.default_notification_channel_id);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle(title)
                            .setContentText(body)
                            .setAutoCancel(true)
                            .setChannelId(channelId)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("descr");
                notificationManager.createNotificationChannel(channel);
                notificationBuilder.setChannelId(channelId);
            }
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }

}
