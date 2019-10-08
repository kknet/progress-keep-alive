package com.exmple.progress.keeplive;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.exmple.progress.keeplive.service.WindowService;
import com.exmple.progress.keeplive.utils.ScreenManager;
import com.exmple.progress.keeplive.utils.ScreenReceiverUtil;

/**
 * Created by lang.chen on 2019/9/5
 * <p>
 * 开启1像素的window 来做应用保活
 */
public class WindowActivity extends AppCompatActivity {


    private  String TAG =WindowActivity.class.getName()+".class";
    private long mTime;

    private ScreenReceiverUtil mScreenListener;
    private ScreenManager mScreenManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window mWindow = getWindow();
        mWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams attrParams = mWindow.getAttributes();
        attrParams.x = 0;
        attrParams.y = 0;
        attrParams.height = 10;
        attrParams.width = 10;
        mWindow.setAttributes(attrParams);
        ScreenManager.getInstance(this).setSingleActivity(this);
        mScreenListener = new ScreenReceiverUtil(this);
        mScreenManager = ScreenManager.getInstance(this);
        mScreenListener.setScreenReceiverListener(mScreenListenerer);
    }


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


    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    if(System.currentTimeMillis()-mTime>5000){
//                        Log.i(TAG,"running");
                        mTime=System.currentTimeMillis();
                    }

                }
            }
        }).start();
        //handler.sendEmptyMessage(1);
    }

    /**
     *
     * 15:29
     *
     * Vivo Y66 Android 6.0
     * 保持在后台20分钟会被应用杀死,无法保活，导致锁屏监听无效
     * 保持在黑屏状态下12分钟被杀死,无法拉活,之后无法收到屏幕广播
     *
     *
     * OPPO PBAM00  Android 8.0   1个小时以内
     *
     *
     *14:56:28
     * Vivo x7Plus 5.0
     * 5-10分钟会进入被杀死，无法接受到Umeng推送,20分钟后彻底杀死进程
     *
     *
     *
     *
     * 09:46
     * HuaWei CHM-CL00 Android 4.4
     *
     *
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {//11:43 07   15:25
            Log.i(TAG, "running");

            handler.sendEmptyMessageDelayed(1, 2000);
        }
    };

    @Override
    protected void onDestroy() {
        mScreenListener.stopScreenReceiverListener();
        Intent intentAlive = new Intent(this, WindowService.class);
        startService(intentAlive);
        super.onDestroy();
    }

}
