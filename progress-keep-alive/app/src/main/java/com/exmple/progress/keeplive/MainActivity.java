package com.exmple.progress.keeplive;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.exmple.progress.keeplive.service.LocalMountService;
import com.exmple.progress.keeplive.service.MyJobService;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "LocalMountService.class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 小于5.0用双进程守护
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(this, LocalMountService.class);
            startService(intent);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            //小于8.0 使用JobService
            Intent intent = new Intent(this, MyJobService.class);
            startService(intent);
        }


    }


    @Override
    protected void onStart() {
        super.onStart();

    }


}
