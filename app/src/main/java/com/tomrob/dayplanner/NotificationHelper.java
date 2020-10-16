package com.tomrob.dayplanner;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import com.tomrob.dayplanner.R;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String channel1ID = "channel1ID";
    public static final String channel1Name = "Channel 1";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        // for apps with over 26 API
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            creatChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void creatChannels(){
        NotificationChannel channel1 = new NotificationChannel(channel1ID,channel1Name, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager() {
        if(mManager == null){
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(){

        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
                .setContentTitle("Time slot has finished!")
                .setContentText("Check whats next now!")
                .setSmallIcon(R.drawable.ic_baseline_update_24)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

    }
}
