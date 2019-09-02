package com.exmple.progress.keeplive.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.exmple.progress.keeplive.service.LocalMountService;

/**
 * Created by lang.chen on 2019/9/2
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
            System.out.println("—— SCREEN_ON ——");
            Intent intentServcie=new Intent(context, LocalMountService.class);
            context.startService(intentServcie);

        } else if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
            System.out.println("—— SCREEN_OFF ——");
        }
    }
}
