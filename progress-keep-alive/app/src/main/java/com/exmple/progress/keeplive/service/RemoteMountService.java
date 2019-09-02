package com.exmple.progress.keeplive.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.exmple.progress.keeplive.IKeeplive;

/**
 * Created by lang.chen on 2019/8/28
 */
public class RemoteMountService extends Service {

    private  final  static String TAG="LocalMountService.class";
    private  Context mContext;
    private  MyBinder myBinder;
    @Override
    public IBinder onBind(Intent intent) {
      return myBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        myBinder=new MyBinder();
        mContext=this;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //        // 绑定建立链接
        startService(new Intent(mContext,LocalMountService.class));
        bindService(new Intent(mContext, LocalMountService.class), mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            startService(new Intent(mContext, LocalMountService.class));
            // 重新绑定
            bindService(new Intent(mContext, LocalMountService.class), mServiceConnection, Context.BIND_IMPORTANT);

            //创建通知栏
            Notification.Builder builder = new Notification.Builder(mContext);
            //显示通知栏，服务进程提权为前台服务。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startForeground(250, builder.build());//使用id：250标记该通知栏
            }
        }
    };

    private class MyBinder extends IKeeplive.Stub{

        @Override
        public String getServiceName() throws RemoteException {
            return RemoteMountService.class.getName();
        }
    }
}