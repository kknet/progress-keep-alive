package com.exmple.progress.keeplive.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.exmple.progress.keeplive.IKeeplive;

/**
 * Created by lang.chen on 2019/8/28
 */
public class RemoteMountService extends Service {

    private  final  static String TAG="LocalMountService.class";

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
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //        // 绑定建立链接
        bindService(new Intent(this, LocalMountService.class), mServiceConnection, Context.BIND_IMPORTANT);
        return super.onStartCommand(intent, flags, startId);
    }

    ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG,"onSeveiceStop-local");
//            Intent intent = new Intent(RemoteMountService.this, HolderActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
            Intent intentAction=new Intent();
            intentAction.setAction("com.exmple.progress.keeplive.re");
            intentAction.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            getApplicationContext().sendBroadcast(intentAction);
            startService(new Intent(RemoteMountService.this, LocalMountService.class));
            // 重新绑定
            bindService(new Intent(RemoteMountService.this, LocalMountService.class), mServiceConnection, Context.BIND_IMPORTANT);

        }
    };
}