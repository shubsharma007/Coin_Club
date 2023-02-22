//package com.example.coinclubapp.Notifications;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.os.IBinder;
//
//import androidx.annotation.Nullable;
//import androidx.core.content.res.ResourcesCompat;
//
//import com.example.coinclubapp.MainActivity;
//import com.example.coinclubapp.R;
//
//public class ExampleService extends Service {
//    public static final String CHANNEL_ID = "exampleservicechannel";
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        Intent iNotify = new Intent(getApplicationContext(), MainActivity.class);
//        iNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            pendingIntent = PendingIntent.getActivity(this, 0, iNotify, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
//
//        }else {
//            pendingIntent = PendingIntent.getActivity(this, 0, iNotify, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
//
//        }
//        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 11, iNotify, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
//
//        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.phonepe_icon, null);
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//        Bitmap largeIcon = bitmapDrawable.getBitmap();
//
//        Notification notification;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            notification = new Notification.Builder(this)
//                    .setLargeIcon(largeIcon)
//                    .setSmallIcon(R.drawable.googlepay_icon)
//                    .setContentTitle("Example Service")
//                    .setContentText("i am notification hello hello")
//                    .setSubText("Hurry Up !!! bidding started")
//                    .setContentIntent(pendingIntent)
//                    .setChannelId(CHANNEL_ID)
//                    .build();
//
//            startForeground(11,notification);
//
//        } else {
//            notification = new Notification.Builder(this)
//                    .setLargeIcon(largeIcon)
//                    .setSmallIcon(R.drawable.googlepay_icon)
//                    .setContentTitle("Example Service")
//                    .setContentText("i am notification hello hello")
//                    .setSubText("Hurry Up !!! bidding started")
//                    .build();
//            startForeground(11,notification);
//
//        }
//        return START_NOT_STICKY;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//}
