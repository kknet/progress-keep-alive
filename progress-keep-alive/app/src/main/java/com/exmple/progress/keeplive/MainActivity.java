package com.exmple.progress.keeplive;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.exmple.progress.keeplive.service.WindowService;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "LocalMountService.class";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        /**
//         * 小于5.0用双进程守护
//         */
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            Intent intent = new Intent(this, LocalMountService.class);
//            startService(intent);
//        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//            //小于8.0 使用JobService
//            Intent intent = new Intent(this, MyJobService.class);
//            startService(intent);
//        } else {
//            Intent intent = new Intent(this, LForegroundService.class);
//            startForegroundService(intent);
//
//
//        }

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Intent intent = new Intent(this, LForegroundService.class);
            startForegroundService(intent);
        }else {
            Intent intent = new Intent(this, LForegroundService.class);
            startService(intent);
        }
*/


//        Intent intent=new Intent(this,WindowActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intentAlive = new Intent(this, WindowService.class);
            startForegroundService(intentAlive);
        }else {
            Intent intentAlive = new Intent(this, WindowService.class);
            startService(intentAlive);
        }

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"show ",Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 进入白名单列表上一层级
     **/
    private void enterSetting() {
        Intent intent = new Intent();
        intent.setAction("com.android.settings.action.SETTINGS");
        intent.addCategory("com.android.settings.category");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName("com.android.settings"
                , "com.android.settings.Settings.PowerUsageSummaryActivity");

        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


}
