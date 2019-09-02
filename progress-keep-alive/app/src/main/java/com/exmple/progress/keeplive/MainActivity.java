package com.exmple.progress.keeplive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.exmple.progress.keeplive.service.MyJobService;

public class MainActivity extends AppCompatActivity {
    private  final  static String TAG="LocalMountService.class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=new Intent(this, MyJobService.class);
        startService(intent);


    }


    @Override
    protected void onStart() {
        super.onStart();

    }



}
