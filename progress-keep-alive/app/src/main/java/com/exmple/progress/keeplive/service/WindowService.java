package com.exmple.progress.keeplive.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.exmple.progress.keeplive.utils.ScreenManager;
import com.exmple.progress.keeplive.utils.ScreenReceiverUtil;

/**
 * Created by lang.chen on 2019/9/5
 */
public class WindowService extends Service {
    public static final int NOTICE_ID = 100;
    private static final String TAG ="WindowActivity.class";
    private DownloadBinder mDownloadBinder;
    private NotificationCompat.Builder mBuilderProgress;
    private NotificationManager mNotificationManager;

    private ScreenReceiverUtil mScreenListener;
    private ScreenManager mScreenManager;

    private ScreenReceiverUtil.SreenStateListener mScreenListenerer = new ScreenReceiverUtil.SreenStateListener() {
        @Override
        public void onSreenOn() {
            mScreenManager.finishActivity();
            Log.d(TAG, "关闭了1像素Activity");
        }

        @Override
        public void onSreenOff() {
            mScreenManager.startActivity();
            Log.d(TAG, "打开了1像素Activity");
        }

        @Override
        public void onUserPresent() {
        }
    };
    private OnTimeChangeListener mOnTimeChangeListener;

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("0","channelTest",NotificationManager.IMPORTANCE_DEFAULT);
            startForeground(1, new Notification());
        }

//        注册锁屏广播监听器
        mScreenListener = new ScreenReceiverUtil(this);
        mScreenManager = ScreenManager.getInstance(this);
        mScreenListener.setScreenReceiverListener(mScreenListenerer);

        mDownloadBinder = new DownloadBinder();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {

        return mDownloadBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (mManager == null) {
            return;
        }
        mManager.cancel(NOTICE_ID);

//        mScreenListener.stopScreenReceiverListener();
    }



    public interface OnTimeChangeListener {
        void showTime(String time);
    }

    public class DownloadBinder extends Binder {
        public void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener) {
            mOnTimeChangeListener = onTimeChangeListener;
        }
    }
}
