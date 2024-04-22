package com.duymanh.btl;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {

    final String CHANNEL_ID = "101";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("myAction")
                && intent.getStringExtra("Title")!=null
                && intent.getStringExtra("Description")!=null ){
            Log.e("Rev","rev");
            NotificationManager manager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel1 = new NotificationChannel(
                        CHANNEL_ID,
                        "Channel 1",
                        NotificationManager.IMPORTANCE_HIGH
                );
                channel1.setDescription("This is Channel 1");
                manager.createNotificationChannel(channel1);
            }
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context,CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_notifications)
                            .setContentTitle(intent.getStringExtra("Title"))
                            .setContentText(intent.getStringExtra("Description"))
                            .setColor(Color.RED)
                            .setCategory(NotificationCompat.CATEGORY_ALARM)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));;


//            Intent i = new Intent(context, MainActivity.class);
//            PendingIntent pendingIntent =
//                    PendingIntent.getActivity(
//                            context,
//                            0,
//                            i,
//                            PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE,null
//                    );
//
//            builder.setContentIntent(pendingIntent);
            manager.notify(12345, builder.build());
        }
    }
}
