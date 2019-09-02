package com.exmple.progress.keeplive.service;

import android.app.Notification;
import android.app.Service;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.wallpaper.WallpaperService;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.exmple.progress.keeplive.IKeeplive;

/**
 * Created by lang.chen on 2019/8/28
 */
public class LocalMountService extends Service {

    private  final  static String TAG="LocalMountService.class";

    private  ServiceConnection serviceConnection;

    @Override
    public IBinder onBind(Intent intent) {
        return new IKeeplive.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }
        };
    }


    @Override
    public void onCreate() {
        super.onCreate();
        serviceConnection=new LocalServiceConn();
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intent1=new Intent(LocalMountService.this, MyJobService.class);
        bindService(intent1,serviceConnection, Context.BIND_AUTO_CREATE);
        return START_STICKY;
    }

    class  LocalServiceConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            Intent intent1=new Intent(LocalMountService.this,MyJobService.class);
            startService(intent1);
            bindService(intent1,serviceConnection, Context.BIND_AUTO_CREATE);


        }
    }

}