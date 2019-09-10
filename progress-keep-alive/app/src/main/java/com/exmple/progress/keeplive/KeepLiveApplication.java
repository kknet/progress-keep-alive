package com.exmple.progress.keeplive;


import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.xiaoniu.keeplive.KeepLive;
import com.xiaoniu.keeplive.config.ForegroundNotification;
import com.xiaoniu.keeplive.config.ForegroundNotificationClickListener;
import com.xiaoniu.keeplive.config.KeepLiveService;


/**
 * Start
 * <p/>
 * User:Rocky(email:1247106107@qq.com)
 * Created by Rocky on 2017/09/17  16:49
 * PACKAGE_NAME com.eagle.locker.activity
 * PROJECT_NAME LockerScreen
 * TODO:
 * Description:
 * <p/>
 * Done
 */
public class KeepLiveApplication extends Application {
    static KeepLiveApplication mInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化sdk 正式版的时候设置false，关闭调试
        mInstance = this;

        //定义前台服务的默认样式。即标题、描述和图标
        ForegroundNotification foregroundNotification = new ForegroundNotification("推送标题", "推送内容描述", R.mipmap.ic_launcher,
                //定义前台服务的通知点击事件
                new ForegroundNotificationClickListener() {

                    @Override
                    public void foregroundNotificationClick(Context context, Intent intent) {
                    }
                });
        //启动保活服务
        KeepLive.startWork(this, KeepLive.RunMode.ROGUE, foregroundNotification,
                //你需要保活的服务，如socket连接、定时任务等，建议不用匿名内部类的方式在这里写
                new KeepLiveService() {

                    /**
                     * 运行中
                     * 由于服务可能会多次自动启动，该方法可能重复调用
                     */
                    @Override
                    public void onWorking() {

                    }

                    /**
                     * 服务终止
                     * 由于服务可能会被多次终止，该方法可能重复调用，需同onWorking配套使用，如注册和注销broadcast
                     */
                    @Override
                    public void onStop() {

                    }
                }
        );

    }

    public static KeepLiveApplication getInstance() {
        return mInstance;
    }
}
