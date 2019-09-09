package com.exmple.progress.keeplive.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

/**
 * Created by lang.chen on 2019/9/5
 */
public class LForegroundService extends Service {


    private  int  notifyId=0x4223;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //创建通知栏
        Notification.Builder builder = new Notification.Builder(this);

        //显示通知栏，服务进程提权为前台服务。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            makeForegroundService();
        }

        return super.onStartCommand(intent, flags, startId);

    }


    private void makeForegroundService() {
        Intent notificationIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel;
        String channelId;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channelId = "channel";
            channel = new NotificationChannel(channelId, "MainActivity", NotificationManager.IMPORTANCE_NONE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        } else {
            channelId = null;
        }

        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, channelId).setContentTitle("Title").setContentText("ContentMessage").setContentIntent(pendingIntent).build();
        }
        startForeground(notifyId, notification);
    }

}
