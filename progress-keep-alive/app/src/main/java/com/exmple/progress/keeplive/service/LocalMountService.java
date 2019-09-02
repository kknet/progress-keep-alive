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
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
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

    private  Context mContext;
    private  ServiceConnection serviceConnection;
    private  MyBinder myBinder;

    private  MyHandler myHandler;
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        myBinder=new MyBinder();
        myHandler=new MyHandler();
        serviceConnection=new LocalServiceConn();
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intent1=new Intent(mContext, RemoteMountService.class);
        startService(intent1);
        bindService(intent1,serviceConnection, Context.BIND_IMPORTANT);


        //创建通知栏
        Notification.Builder builder = new Notification.Builder(this);

        //显示通知栏，服务进程提权为前台服务。
        startForeground(250, builder.build());//使用id：250标记该通知栏


        myHandler.sendEmptyMessage(1);
        return START_STICKY;
    }


    private  class  MyHandler extends  Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("test","running");
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myHandler.sendEmptyMessage(1);
                }
            },1000);
        }
    }
    class  LocalServiceConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            //创建通知栏
            Notification.Builder builder = new Notification.Builder(mContext);

            //显示通知栏，服务进程提权为前台服务。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startForeground(250, builder.build());//使用id：250标记该通知栏
            }

            Intent intent1=new Intent(mContext,RemoteMountService.class);
            startService(intent1);
            bindService(intent1,serviceConnection, Context.BIND_IMPORTANT);


        }
    }

    private class MyBinder extends IKeeplive.Stub{

        @Override
        public String getServiceName() throws RemoteException {
            return LocalMountService.class.getName();
        }
    }

}