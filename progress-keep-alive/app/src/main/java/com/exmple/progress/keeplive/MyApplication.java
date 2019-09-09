package com.exmple.progress.keeplive;

import android.app.Application;
import android.util.Log;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * Created by lang.chen on 2019/9/7
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this, "5d734b864ca35716d900041f", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "5833165ea1cc6a86b81cabdb8d273f8b");
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i("MyApplication.class", "注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e("MyApplication.class", "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
    }
}
