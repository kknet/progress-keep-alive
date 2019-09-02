package com.exmple.progress.keeplive.service;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by lang.chen on 2019/8/29
 *
 * @author lang.chen
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {


    private final static String TAG = "MyJobService.class";
    private static final int JOB_ID = 1001;
    private static final long REFRESH_INTERVAL = 5 * 1000; // 5 seconds

    private int id = 0x12;
    private JobScheduler mJobScheduler;


    @Override
    public void onCreate() {
        super.onCreate();
        mJobScheduler = (JobScheduler)
                getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ComponentName componentName = new ComponentName(this, MyJobService.class);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //7.0以及7.0以上
            JobInfo job = new JobInfo.Builder(id, componentName)
                    .setMinimumLatency(5 * 1000)
                    .setOverrideDeadline(5 * 1000)
                    .setRequiresDeviceIdle(true)
                    .setRequiresCharging(true)
                    .setPersisted(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)//任意网络
                    .build();
            //调用schedule
            mJobScheduler.schedule(job);
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            //5.0到6.0
            JobInfo job = new JobInfo.Builder(id, componentName)
                    .setPeriodic(1000)
                    .setPersisted(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)//任意网络
                    .build();
            //调用schedule
            mJobScheduler.schedule(job);
        }

        return START_STICKY;
    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            Log.i("test", "onServiceDisconnected");
            startService(new Intent(MyJobService.this, LocalMountService.class));
            // 重新绑定
            bindService(new Intent(MyJobService.this, LocalMountService.class), serviceConnection, Context.BIND_IMPORTANT);

        }
    };

    @Override
    public boolean onStartJob(JobParameters params) {
        Intent intent1 = new Intent(MyJobService.this, LocalMountService.class);

        startService(intent1);
        bindService(intent1, serviceConnection, Context.BIND_AUTO_CREATE);

        jobFinished(params, true);
        return false;
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i("test", "onServiceDisconnected");
        startService(new Intent(MyJobService.this, LocalMountService.class));
        // 重新绑定
        bindService(new Intent(MyJobService.this, LocalMountService.class), serviceConnection, Context.BIND_IMPORTANT);

        return false;
    }

}
