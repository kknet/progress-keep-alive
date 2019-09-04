package com.exmple.progress.keeplive.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.exmple.progress.keeplive.service.LocalMountService;
import com.exmple.progress.keeplive.service.MyJobService;

/**
 * Created by lang.chen on 2019/9/2
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
            System.out.println("—— SCREEN_ON ——");
            Intent intentServcie = new Intent(context, LocalMountService.class);
            context.startService(intentServcie);

        } else if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
            System.out.println("—— SCREEN_OFF ——");
        } else if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //开启广播
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Intent intentA = new Intent(context, LocalMountService.class);
                context.startService(intentA);
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                //小于8.0 使用JobService
                Intent intentB = new Intent(context, MyJobService.class);
                context.startService(intentB);
            }
        }
    }
}
