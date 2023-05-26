package com.example.porgamring.helpers;

import android.annotation.SuppressLint;
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

import androidx.core.app.NotificationCompat;

import com.example.porgamring.MainActivity;
import com.example.porgamring.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Tasks {
    Context getApplicationContext;
    NotificationManager notificationManager;

    /**
     * @param getApplicationContext to get this:
     *                              getApplicationContext()
     * @param notificationManager   to get this:
     *                              (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
     */
    public Tasks(Context getApplicationContext, NotificationManager notificationManager) {
        this.getApplicationContext = getApplicationContext;
        this.notificationManager = notificationManager;
    }

    @SuppressLint("ObsoleteSdkInt")
    public void sendNotification(String message, String title) {
        Intent intent = new Intent(getApplicationContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext, 0, intent,
                PendingIntent.FLAG_IMMUTABLE);

        String channelId = "some_channel_id";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = this.notificationManager;

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }


    public void hourlyTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> sendNotification("Je moet draaien!", "Het is weer tijd!"), 0, 1, TimeUnit.HOURS);
    }

    public void monthlyTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


        scheduler.scheduleAtFixedRate(() -> {
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("MM");
            Date date = new Date();
            Log.e("Month", dateFormat.format(date));
            String mon = "";
            String dat = dateFormat.format(date);
            switch (dat) {
                case "12":
                    mon = "DEC";
                    break;
                case "01":
                    mon = "JAN";
                    break;
                case "02":
                    mon = "FEB";
                    break;
                case "03":
                    mon = "MAR";
                    break;
                case "04":
                    mon = "APR";
                    break;
                case "05":
                    mon = "MAY";
                    break;
                case "06":
                    mon = "JUN";
                    break;
                case "07":
                    mon = "JUL";
                    break;
                case "08":
                    mon = "AUG";
                    break;
                case "09":
                    mon = "SEP";
                    break;
                case "10":
                    mon = "OCT";
                    break;
                case "11":
                    mon = "NOV";
                    break;
            }
            String url = "https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/draai/delete/" + mon;
            try {
                SentAPI.delete(url);
                Toast.makeText(getApplicationContext, "delete last month", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
            }

            try {
                SentAPI.delete(url);
            } catch (IOException e) {
                Log.e("IOException", e.getMessage());
            }

        }, 0, 30, TimeUnit.DAYS);

    }
}
