package com.example.porgamring;

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

import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
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
     *                              NotificationManager notificationManager
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
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


    public void hourlyTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sendNotification("Je moet draaien!", "Het is weer tijd!");
            }
        }, 0, 1, TimeUnit.SECONDS); // TimeUnit.HOURS als je de het per uur wilt doen
    }

    public void monthlyTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Calculate the delay until the next month
        LocalDateTime nextMonth = now.plusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        Duration duration = Duration.between(now, nextMonth);
        long initialDelay = duration.getSeconds();

        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("MM");
                Date date = new Date();
                Log.d("Month", dateFormat.format(date));
                String mon = "";
                if (date.toString().equals("12")) {
                    mon = "DEC";
                } else if (date.toString().equals("01")) {
                    mon = "JAN";
                } else if (date.toString().equals("02")) {
                    mon = "FEB";
                } else if (date.toString().equals("03")) {
                    mon = "MAR";
                } else if (date.toString().equals("04")) {
                    mon = "APR";
                } else if (date.toString().equals("05")) {
                    mon = "MAY";
                } else if (date.toString().equals("06")) {
                    mon = "JUN";
                } else if (date.toString().equals("07")) {
                    mon = "JUL";
                } else if (date.toString().equals("08")) {
                    mon = "AUG";
                } else if (date.toString().equals("09")) {
                    mon = "SEP";
                } else if (date.toString().equals("10")) {
                    mon = "OCT";
                } else if (date.toString().equals("11")) {
                    mon = "NOV";
                }
                String url = "https://gdfdbb33abf047a-jmaaadprog.adb.eu-amsterdam-1.oraclecloudapps.com/ords/maxh/draai/delete/" + mon;

                try {
                    SentAPI.delete(url);
                } catch (IOException e) {
                    Log.e("IOException", e.getMessage());
                }

            }
        }, initialDelay, 30, TimeUnit.DAYS);
    }
}